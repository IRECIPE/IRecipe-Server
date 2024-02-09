package umc.IRECIPE_Server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "AI Recipe Chatbot", description = "레시피 추천 챗봇 관련 API")
public class ChatGptController {
    private final ChatGptService chatGPTService;

    // 사용자가 ChatGPT 에 처음 접속했을 때 반환할 버튼 메세지
    @GetMapping()
    @Operation(summary = "챗봇 버튼 질문 조회 API", description = "챗봇 버튼의 목록을 조회하는 API 입니다")
    public ApiResponse<ChatGptButtonResponseDTO.buttonResponseDTO> buttonMessages() {
        return ApiResponse.onSuccess(ChatGptConverter.tobuttonResponseDTO());
    }

    // 사용자가 ChatGPT 에 랜덤 레시피 요청
    @GetMapping("/random")
    @Operation(summary = "랜덤 레시피 조회 API", description = "싫어하는 음식을 제외하고, 랜덤으로 레시피를 추천해주는 API 입니다")
    public ApiResponse<UserChatGptResponseDTO.UserGptResponseDTO> getRandomRecipeResponse() {
        // 1. 유저 질문 : request
        // 2. chatgptrequest 에 전달
        // 3. chatgptresponse 반환
        // 4. 유저에 response 반환

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = authentication.getName();
        String question = "랜덤으로 음식 레시피 추천해줘";
        String response = chatGPTService.askQuestion(memberId, question).getChoices().get(0).getMessage().getContent();
        return ApiResponse.onSuccess(ChatGptConverter.toUserGptResponseDTO(response));
    }

    // 사용자 냉장고 재료 기반 레시피 요청
    @GetMapping("/refri")
    @Operation(summary = "사용자 냉장고 재료 기반 레시피 조회 API", description = "싫어하는 음식을 제외하고, 사용자의 냉장고 속 재료를 기반으로 레시피를 추천해주는 API 입니다")
    public ApiResponse<UserChatGptResponseDTO.UserGptResponseDTO> getRefriRecipeResponse() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = authentication.getName();
        String response = chatGPTService.askRefriQuestion(memberId).getChoices().get(0).getMessage().getContent();
        return ApiResponse.onSuccess(ChatGptConverter.toUserGptResponseDTO(response));
    }

    // 사용자 냉장고 재료 유통기한 기반 레시피 요청
    @GetMapping("/expiry")
    @Operation(summary = "사용자 냉장고 재료 유통기한 기반 레시피 조회 API", description = "싫어하는 음식을 제외하고, 사용자의 냉장고에서 유통기한이 짧은 재료를 우선적으로 사용하여 레시피를 추천해주는 API 입니다")
    public ApiResponse<UserChatGptResponseDTO.UserGptResponseDTO> getBeforeExpiredRecipeResponse() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = authentication.getName();
        String response = chatGPTService.askExpiryIngredientsQuestion(memberId).getChoices().get(0).getMessage().getContent();
        return ApiResponse.onSuccess(ChatGptConverter.toUserGptResponseDTO(response));
    }

    // 싫어하는 음식 입력받기
    @PostMapping("/dislike")
    @Operation(summary = "싫어하는 음식 입력 API", description = "사용자가 기피하는 음식을 입력하는 API 입니다. 챗봇이 레시피를 추천할 때 반영됩니다")
    public ApiResponse<String> saveDislikedFood(@RequestBody @Valid DislikedFoodRequestDTO.saveDislikedFoodRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = authentication.getName();
        chatGPTService.saveDislikedFood(memberId, request);
        return ApiResponse.onSuccess("Disliked Food saved successfully");
    }

    // 레시피 저장
    @PostMapping("/save")
    @Operation(summary = "추천받은 레시피 저장 API", description = "챗봇에게 추천받은 레시피를 저장하는 API 입니다")
    public ApiResponse<String> saveRecipe(@RequestBody @Valid ChatGptRecipeSaveRequestDTO.RecipeSaveRequestDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = authentication.getName();
        chatGPTService.saveRecipe(memberId, request);
        return ApiResponse.onSuccess("Recipe saved successfully");
    }
}
