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
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import umc.IRECIPE_Server.apiPayLoad.code.status.ErrorStatus;
import umc.IRECIPE_Server.apiPayLoad.exception.GeneralException;
import umc.IRECIPE_Server.apiPayLoad.exception.handler.IngredientHandler;
import umc.IRECIPE_Server.apiPayLoad.exception.handler.MemberHandler;
import umc.IRECIPE_Server.config.ChatGptConfig;
import umc.IRECIPE_Server.dto.request.ChatGptMessageDto;
import umc.IRECIPE_Server.dto.request.ChatGptRecipeSaveRequestDTO;
import umc.IRECIPE_Server.dto.request.ChatGptRequestDTO;
import umc.IRECIPE_Server.dto.response.ChatGptResponseDTO;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.StoredRecipe;
import umc.IRECIPE_Server.repository.IngredientRepository;
import umc.IRECIPE_Server.repository.MemberRepository;
import umc.IRECIPE_Server.repository.StoredRecipeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChatGptServiceImpl implements ChatGptService {

    private final MemberRepository memberRepository;
    private final IngredientRepository ingredientRepository;
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

    // ChatGPT API 요청하기 - 랜덤 레시피 / 일반 채팅
    @Override
    public ChatGptResponseDTO askQuestion(String question) {
        List<ChatGptMessageDto> messages = new ArrayList<>();
        messages.add(ChatGptMessageDto.builder()
                .role("user")
                .content(question)
                .build());

        return this.getResponse(
                this.buildHttpEntity(
                        new ChatGptRequestDTO(messages, ChatGptConfig.MODEL)
                )
        );
    }

    // ChatGPT API 요청하기 - 냉장고 재료 기반
    @Override
    public ChatGptResponseDTO askRefriQuestion(String memberId) {
        List<String> myIngredientList = ingredientRepository.findNamesByMemberId(memberId);
        String question = myIngredientList + "주어진 재료를 활용하여 만들 수 있는 레시피(조리 방법)를 알려줘";
        return askQuestion(question);
    }

    // ChatGPT API 요청하기 - 냉장고 재료 유통기한 기반
    @Override
    public ChatGptResponseDTO askExpiryIngredientsQuestion(String memberId) {
        List<String> myExpiryIngredientList = ingredientRepository.findIngredientsNameOrderByExpiryDate(memberId);
        String question = myExpiryIngredientList + "이건 유통기한이 짧은 순서인데, 가능하다면 앞 순서부터 주어진 재료를 활용하여 만들 수 있는 레시피(조리 방법)를 알려줘";
        return askQuestion(question);
    }

    // ChatGPT 에게 추천받은 레시피 저장하기
    @Override
    public void saveRecipe(String memberId, ChatGptRecipeSaveRequestDTO.@Valid RecipeSaveRequestDTO recipe) {

        Member member = memberRepository.findByPersonalId(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        StoredRecipe storedRecipe = StoredRecipe.builder()
                .member(member)
                .body(recipe.getBody())
                .build();
        storedRecipeRepository.save(storedRecipe);
    }
}
