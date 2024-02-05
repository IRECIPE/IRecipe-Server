package umc.IRECIPE_Server.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import umc.IRECIPE_Server.apiPayLoad.ApiResponse;
import umc.IRECIPE_Server.converter.ChatGptConverter;
import umc.IRECIPE_Server.dto.request.ChatGptRecipeSaveRequestDTO;
import umc.IRECIPE_Server.dto.request.DislikedFoodRequestDTO;
import umc.IRECIPE_Server.dto.request.UserChatGptRequestDTO;
import umc.IRECIPE_Server.dto.response.ChatGptButtonResponseDTO;
import umc.IRECIPE_Server.dto.response.UserChatGptResponseDTO;
import umc.IRECIPE_Server.service.ChatGptService;

@Slf4j
@RestController
@RequestMapping("/ai-chat")
@RequiredArgsConstructor
public class ChatGptController {
    private final ChatGptService chatGPTService;

    // 사용자가 ChatGPT 에 처음 접속했을 때 반환할 버튼 메세지
    @GetMapping()
    public ApiResponse<ChatGptButtonResponseDTO.buttonResponseDTO> buttonMessages() {
        return ApiResponse.onSuccess(ChatGptConverter.tobuttonResponseDTO());
    }

    // 사용자가 ChatGPT 에 랜덤 레시피 요청 or 직접 입력한 메세지
    @PostMapping("/random")
    public ApiResponse<UserChatGptResponseDTO.UserGptResponseDTO> getRandomRecipeResponse(@RequestBody @Valid UserChatGptRequestDTO.UserGptRequestDTO request) {
        // 1. 유저 질문 : request
        // 2. chatgptrequest 에 전달
        // 3. chatgptresponse 반환
        // 4. 유저에 response 반환

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = authentication.getName();
        String response = chatGPTService.askQuestion(memberId, request.getQuestion()).getChoices().get(0).getMessage().getContent();
        return ApiResponse.onSuccess(ChatGptConverter.toUserGptResponseDTO(response));
    }

    // 사용자 냉장고 재료 기반 레시피 요청
    @GetMapping("/refri")
    public ApiResponse<UserChatGptResponseDTO.UserGptResponseDTO> getRefriRecipeResponse() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = authentication.getName();
        String response = chatGPTService.askRefriQuestion(memberId).getChoices().get(0).getMessage().getContent();
        return ApiResponse.onSuccess(ChatGptConverter.toUserGptResponseDTO(response));
    }

    // 사용자 냉장고 재료 유통기한 기반 레시피 요청
    @GetMapping("/expiry")
    public ApiResponse<UserChatGptResponseDTO.UserGptResponseDTO> getBeforeExpiredRecipeResponse() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = authentication.getName();
        String response = chatGPTService.askExpiryIngredientsQuestion(memberId).getChoices().get(0).getMessage().getContent();
        return ApiResponse.onSuccess(ChatGptConverter.toUserGptResponseDTO(response));
    }

    // 싫어하는 음식 입력받기
    @PostMapping("/dislike")
    public ApiResponse<String> saveDislikedFood(@RequestBody @Valid DislikedFoodRequestDTO.saveDislikedFoodRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = authentication.getName();
        chatGPTService.saveDislikedFood(memberId, request);
        return ApiResponse.onSuccess("Disliked Food saved successfully");
    }

    // 레시피 저장
    @PostMapping("/save")
    public ApiResponse<String> saveRecipe(@RequestBody @Valid ChatGptRecipeSaveRequestDTO.RecipeSaveRequestDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = authentication.getName();
        chatGPTService.saveRecipe(memberId, request);
        return ApiResponse.onSuccess("Recipe saved successfully");
    }
}
