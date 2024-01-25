package umc.IRECIPE_Server.service;

import jakarta.validation.Valid;
import umc.IRECIPE_Server.dto.request.ChatGptRecipeSaveRequestDTO;
import umc.IRECIPE_Server.dto.response.ChatGptResponseDTO;

public interface ChatGptService {

    // 랜덤 레시피 & 일반 채팅
    ChatGptResponseDTO askQuestion(String question);

    // 사용자 재료 기반 레시피
    ChatGptResponseDTO askRefriQuestion(String memberId);

    // 사용자 재료 유통기한 기반 레시피
    ChatGptResponseDTO askExpiryIngredientsQuestion(String memberId);

    // 레시피 저장
    void saveRecipe(String memberId, ChatGptRecipeSaveRequestDTO.@Valid RecipeSaveRequestDTO recipe);
}
