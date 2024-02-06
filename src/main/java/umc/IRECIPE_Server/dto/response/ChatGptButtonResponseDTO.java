package umc.IRECIPE_Server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ChatGptButtonResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class buttonResponseDTO{
        private String button1;
        private String button2;
        private String button3;
        private String button4;
    }
}
