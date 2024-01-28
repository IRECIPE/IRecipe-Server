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
        String button1;
        String button2;
        String button3;
    }
}
