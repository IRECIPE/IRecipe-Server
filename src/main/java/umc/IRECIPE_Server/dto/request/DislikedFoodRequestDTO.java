package umc.IRECIPE_Server.dto.request;

import lombok.Getter;

public class DislikedFoodRequestDTO {

    @Getter
    public static class saveDislikedFoodRequest {
        String dislikedFood;
    }
}
