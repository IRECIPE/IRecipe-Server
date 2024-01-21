package umc.IRECIPE_Server.converter;

import umc.IRECIPE_Server.domain.mapping.Ingredient;
import umc.IRECIPE_Server.domain.mapping.IngredientCategory;
import umc.IRECIPE_Server.web.dto.IngredientRequest;
import umc.IRECIPE_Server.web.dto.IngredientResponse;

import java.time.LocalDateTime;

public class IngredientConverter {

    public static IngredientResponse.addResultDTO toaddResultDTO(Ingredient ingredient) {
        return IngredientResponse.addResultDTO.builder()
                .ingredientId(ingredient.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Ingredient toIngredient(IngredientRequest.addDTO request) {

        IngredientCategory category = IngredientCategory.builder()
                .name(request.getCategory())
                .type(request.getType())
                .expiryDate(request.getExpiryDate())
                .build();


        return Ingredient.builder()
                .name(request.getName())
                .image(request.getImage())
                .memo(request.getMemo())
                .ingredientCategory(category)
                .build();
    }
}
