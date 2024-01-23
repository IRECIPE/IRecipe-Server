package umc.IRECIPE_Server.dto;

import lombok.Getter;

import java.io.Serializable;

public class UserChatGptRequestDTO implements Serializable {
    // 프론트에서 백엔드에 요청하는 질문 Dto

    @Getter
    public static class UserGptRequestDTO {
        String question;
    }

}
