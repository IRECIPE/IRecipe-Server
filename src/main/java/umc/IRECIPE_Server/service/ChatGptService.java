package umc.IRECIPE_Server.service;

import jakarta.validation.Valid;
import umc.IRECIPE_Server.dto.ChatGptRecipeSaveRequestDTO;
import umc.IRECIPE_Server.dto.ChatGptResponseDTO;

public interface ChatGptService {
    ChatGptResponseDTO askQuestion(String question);
    void saveRecipe(Long memberId, ChatGptRecipeSaveRequestDTO.@Valid RecipeSaveRequestDTO recipe);
}
