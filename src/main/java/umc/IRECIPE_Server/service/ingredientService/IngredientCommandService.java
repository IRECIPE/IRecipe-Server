package umc.IRECIPE_Server.service.ingredientService;


import umc.IRECIPE_Server.dto.IngredientRequest;
import umc.IRECIPE_Server.entity.Ingredient;


public interface IngredientCommandService {
    Ingredient addIngredient(IngredientRequest.addDTO request);
    Ingredient updateById(IngredientRequest.updateDTO request, Long ingredientId);
}
