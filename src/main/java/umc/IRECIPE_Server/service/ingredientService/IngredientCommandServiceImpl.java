package umc.IRECIPE_Server.service.ingredientService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.IRECIPE_Server.apiPayLoad.code.status.ErrorStatus;
import umc.IRECIPE_Server.apiPayLoad.exception.handler.IngredientHandler;
import umc.IRECIPE_Server.converter.IngredientConverter;

import umc.IRECIPE_Server.dto.IngredientRequest;
import umc.IRECIPE_Server.entity.Ingredient;
import umc.IRECIPE_Server.repository.IngredientRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IngredientCommandServiceImpl implements IngredientCommandService {

    private final IngredientRepository ingredientRepository;
    @Override
    @Transactional
    public Ingredient addIngredient(IngredientRequest.addDTO request) {
        Ingredient newIngredient = IngredientConverter.toIngredient(request);
        return ingredientRepository.save(newIngredient);
    }

    @Override
    @Transactional
    public Ingredient updateById(IngredientRequest.updateDTO request, Long ingredientId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new IngredientHandler(ErrorStatus.INGREDIENT_NOT_FOUND));

        ingredient.updateIngredient(request.getName(), request.getCategory(), request.getType(), request.getExpiryDate(), request.getMemo());
        return ingredientRepository.save(ingredient);
    }
}