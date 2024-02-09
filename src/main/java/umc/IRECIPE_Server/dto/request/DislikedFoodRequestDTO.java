package umc.IRECIPE_Server.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

public class DislikedFoodRequestDTO {

    @Getter
    public static class saveDislikedFoodRequest {
        @Schema(description = "사용자가 기피하는 음식", example = "브로콜리")
        String dislikedFood;
    }
}
