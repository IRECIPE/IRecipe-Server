package umc.IRECIPE_Server.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import umc.IRECIPE_Server.apiPayLoad.code.status.ErrorStatus;
import umc.IRECIPE_Server.apiPayLoad.exception.GeneralException;
import umc.IRECIPE_Server.config.ChatGptConfig;
import umc.IRECIPE_Server.converter.ChatGptConverter;
import umc.IRECIPE_Server.dto.request.ChatGptMessageDto;
import umc.IRECIPE_Server.dto.request.ChatGptRecipeSaveRequestDTO;
import umc.IRECIPE_Server.dto.request.ChatGptRequestDTO;
import umc.IRECIPE_Server.dto.request.DislikedFoodRequestDTO;
import umc.IRECIPE_Server.dto.response.ChatGptResponseDTO;
import umc.IRECIPE_Server.entity.DislikedFood;
import umc.IRECIPE_Server.entity.Ingredient;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.repository.DislikedFoodRepository;
import umc.IRECIPE_Server.repository.IngredientRepository;
import umc.IRECIPE_Server.repository.MemberRepository;
import umc.IRECIPE_Server.repository.StoredRecipeRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChatGptServiceImpl implements ChatGptService {

    private final MemberRepository memberRepository;
    private final IngredientRepository ingredientRepository;
    private final DislikedFoodRepository dislikedFoodRepository;
    private final StoredRecipeRepository storedRecipeRepository;
    private static RestTemplate restTemplate = new RestTemplate();

    @Value("${chatgpt.api-key}")
    private String apiKey;

    // ChatGPT 에 요청할 때 포함해야 하는 값 세팅하기
    @Transactional
    public HttpEntity<ChatGptRequestDTO> buildHttpEntity(ChatGptRequestDTO requestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ChatGptConfig.MEDIA_TYPE));
        headers.add(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + apiKey);
        return new HttpEntity<>(requestDTO, headers);
    }

    // ChatGPT 요청할 때 레시피 요구조건 세팅하기
    public String setRecipeRequirements(String memberId) {

        String recipeRequirement1 = " 대신, 350ml = 종이컵 1컵 / 1T = 큰 숟가락 반 / 파우더 1g = 큰 숟가락 1/4 = 1티스푼 식으로 계량 측정 방법을 통일하고, ";
        String recipeRequirement2 = "필수 재료와 선택 재료를 구분하고 ";
        String recipeRequirements;

        // 현재 사용자 조회
        Optional<Member> member = memberRepository.findByPersonalId(memberId);
        if(member.isEmpty()){
            throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
        }

        // 사용자가 기피하는 음식 조회 후 질문에 포함하기
        List<DislikedFood> dislikedFoodList = dislikedFoodRepository.findAllByMember_Id(member.get().getId());
        String excludedFoods = dislikedFoodList.stream()
                .map(DislikedFood::getName)
                .collect(Collectors.joining(", "));
        if (dislikedFoodList.isEmpty()) {
            recipeRequirements = recipeRequirement1 + recipeRequirement2 + "추천해줘";
        } else {
            recipeRequirements = recipeRequirement1 + recipeRequirement2 + excludedFoods + "제외해줘";
        }

        return recipeRequirements;
    }

    // ChatGPT API 응답 반환 받기
    @Transactional
    public ChatGptResponseDTO getResponse(HttpEntity<ChatGptRequestDTO> chatGptRequestDTOHttpEntity) {
        // 답변 길어질 시 Timeout Error : 1분 설정 60sec * 10000ms
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(60000);
        requestFactory.setReadTimeout(60 * 1000);
        restTemplate.setRequestFactory(requestFactory);

        ResponseEntity<ChatGptResponseDTO> responseEntity = restTemplate.postForEntity(
                ChatGptConfig.URL,
                chatGptRequestDTOHttpEntity,
                ChatGptResponseDTO.class
        );
        return responseEntity.getBody();
    }

    // ChatGPT API 요청하기 - 랜덤 레시피
    @Override
    public ChatGptResponseDTO askQuestion(String memberId, String question) {

        // 레시피 요구사항 불러오기
        String recipeRequirements = setRecipeRequirements(memberId);

        // ChatGPT API 사용 시 지켜야 할 DTO 맞추기
        List<ChatGptMessageDto> messages = new ArrayList<>();
        messages.add(ChatGptMessageDto.builder()
                .role("user")
                .content(question + recipeRequirements)
                .build());

        // 응답 반환
        return this.getResponse(
                this.buildHttpEntity(
                        new ChatGptRequestDTO(messages, ChatGptConfig.MODEL)
                )
        );
    }

    // ChatGPT API 요청하기 - 냉장고 재료 기반
    @Override
    public ChatGptResponseDTO askRefriQuestion(String memberId) {

        // 사용자 재료 조회
        List<Ingredient> myIngredientList = ingredientRepository.findNamesByMember_PersonalId(memberId);
        String myIngredient = myIngredientList.stream()
                .map(Ingredient::getName)
                .collect(Collectors.joining(", "));

        // 질문 넘겨주기
        String question = myIngredient + "주어진 재료를 활용하여 만들 수 있는 레시피(조리 방법)를 알려줘";
        return askQuestion(memberId, question);
    }

    // ChatGPT API 요청하기 - 냉장고 재료 유통기한 기반
    @Override
    public ChatGptResponseDTO askExpiryIngredientsQuestion(String memberId) {

        // 유통기한으로 정렬된 사용자 재료 조회
        List<Ingredient> myExpiryIngredientList = ingredientRepository.findNamesByMember_PersonalId(memberId);
        String myExpiryIngredient = myExpiryIngredientList.stream()
                .sorted(Comparator.comparing(Ingredient::getExpiry_date))
                .map(Ingredient::getName)
                .collect(Collectors.joining(", "));

        // 질문 넘겨주기
        String question = myExpiryIngredient + "이건 유통기한이 짧은 순서인데, 가능하다면 앞 순서부터 주어진 재료를 활용하여 만들 수 있는 레시피(조리 방법)를 알려줘";
        return askQuestion(memberId, question);
    }

    // 싫어하는 음식 저장하기
    @Override
    public void saveDislikedFood(String memberId, DislikedFoodRequestDTO.@Valid saveDislikedFoodRequest dislikedFood) {

        Optional<Member> member = memberRepository.findByPersonalId(memberId);
        if(member.isEmpty()){
            throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
        }

        dislikedFoodRepository.save(ChatGptConverter.saveDislikedFood(member.get(), dislikedFood.getDislikedFood()));
    }

    // ChatGPT 에게 추천받은 레시피 저장하기
    @Override
    public void saveRecipe(String memberId, ChatGptRecipeSaveRequestDTO.@Valid RecipeSaveRequestDTO recipe) {

        Optional<Member> member = memberRepository.findByPersonalId(memberId);
        if(member.isEmpty()){
            throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
        }

        storedRecipeRepository.save(ChatGptConverter.saveStoredRecipe(member.get(), recipe.getBody()));
    }
}
