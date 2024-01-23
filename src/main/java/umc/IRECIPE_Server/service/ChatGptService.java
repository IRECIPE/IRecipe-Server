package umc.IRECIPE_Server.service;

import umc.IRECIPE_Server.dto.ChatGptResponseDTO;
import umc.IRECIPE_Server.dto.UserChatGptRequestDTO;

public interface ChatGptService {
    ChatGptResponseDTO askQuestion(String question);
}
