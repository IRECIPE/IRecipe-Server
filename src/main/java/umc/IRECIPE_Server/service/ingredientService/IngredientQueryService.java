package umc.IRECIPE_Server.service.ingredientService;

import umc.IRECIPE_Server.domain.mapping.Ingredient;
import umc.IRECIPE_Server.web.dto.IngredientRequest;

import java.util.Optional;

public interface IngredientQueryService {
    Ingredient findOne(Long ingredientId);

    void delete(Long ingredientId);


}
