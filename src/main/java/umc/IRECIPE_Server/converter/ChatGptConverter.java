package umc.IRECIPE_Server.converter;

import umc.IRECIPE_Server.dto.UserChatGptResponseDTO;

import java.util.List;

public class ChatGptConverter {

    public static UserChatGptResponseDTO.UserGptResponseDTO toUserGptResponseDTO(String gptResponse) {
        return UserChatGptResponseDTO.UserGptResponseDTO.builder()
                .gptResponse(gptResponse)
                .build();
    }
}
