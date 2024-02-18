package umc.IRECIPE_Server.service.ingredientService;


import umc.IRECIPE_Server.dto.request.IngredientRequestDTO;
import umc.IRECIPE_Server.entity.Ingredient;


public interface IngredientCommandService {
    Ingredient addIngredient(String memberId, IngredientRequestDTO.addDTO request);
    Ingredient updateById(IngredientRequestDTO.updateDTO request, Long ingredientId);
}
