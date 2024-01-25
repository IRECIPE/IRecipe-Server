package umc.IRECIPE_Server.service.ingredientService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.IRECIPE_Server.apiPayLoad.code.status.ErrorStatus;
import umc.IRECIPE_Server.apiPayLoad.exception.handler.IngredientHandler;
import umc.IRECIPE_Server.domain.mapping.Ingredient;
import umc.IRECIPE_Server.repository.IngredientRepository;
import umc.IRECIPE_Server.web.dto.IngredientRequest;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IngredientQueryServiceImpl implements IngredientQueryService {
    private final IngredientRepository ingredientRepository;
    @Override
    public Ingredient findOne(Long ingredientId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new IngredientHandler(ErrorStatus.INGREDIENT_NOT_FOUND));
        return ingredient;
    }

    @Override
    @Transactional
    public void delete(Long ingredientId) {
        Ingredient ingredient = findOne(ingredientId);
        ingredientRepository.delete(ingredient);
    }


}
