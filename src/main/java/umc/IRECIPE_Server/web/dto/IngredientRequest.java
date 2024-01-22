package umc.IRECIPE_Server.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.IRECIPE_Server.domain.enums.Type;

import java.time.LocalDate;


public class IngredientRequest {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class addDTO{
        String name;
        String image;
        String category;
        String memo;
        Type type;
        LocalDate expiryDate;
    }


}
