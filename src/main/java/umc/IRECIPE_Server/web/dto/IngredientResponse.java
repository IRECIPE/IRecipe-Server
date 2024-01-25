package umc.IRECIPE_Server.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.IRECIPE_Server.domain.enums.Type;

import java.time.LocalDate;
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
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class findOneResultDTO{
        String name;
        String image;
        String category;
        String memo;
        Type type;
        LocalDate expiryDate;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class deleteResultDTO{
        String message;
    }

}
