package umc.IRECIPE_Server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class UserChatGptResponseDTO {
    // 백엔드에서 프론트로 ChatGpt 응답 반환하는 Dto

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserGptResponseDTO{
        String gptResponse;
    }
}
