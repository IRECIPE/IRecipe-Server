package umc.IRECIPE_Server.service.ingredientService;

import umc.IRECIPE_Server.domain.mapping.Ingredient;
import umc.IRECIPE_Server.web.dto.IngredientRequest;

public interface IngredientCommandService {
    public Ingredient addIngredient(IngredientRequest.addDTO request);
}