package umc.IRECIPE_Server.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class IngredientResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class addResultDTO{
        Long ingredientId;
        LocalDateTime createdAt;
    }
}
