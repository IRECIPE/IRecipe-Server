package umc.IRECIPE_Server.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

public class ChatGptRecipeSaveRequestDTO {

    @Getter
    public static class RecipeSaveRequestDTO {
        @Schema(description = "추천받은 레시피 내용")
        private String body;
    }
}
