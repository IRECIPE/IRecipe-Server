package umc.IRECIPE_Server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.IRECIPE_Server.common.enums.IngredientCategory;
import umc.IRECIPE_Server.common.enums.Type;


import java.time.LocalDate;


public class IngredientRequest {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class addDTO{
        String name;
        IngredientCategory category;
        String memo;
        Type type;
        LocalDate expiryDate;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class updateDTO{
        String name;
        IngredientCategory category;
        String memo;
        Type type;
        LocalDate expiryDate;
    }


}
