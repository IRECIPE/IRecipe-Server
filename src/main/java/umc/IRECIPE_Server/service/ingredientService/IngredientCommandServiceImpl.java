package umc.IRECIPE_Server.service.ingredientService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.IRECIPE_Server.converter.IngredientConverter;
import umc.IRECIPE_Server.domain.mapping.Ingredient;
import umc.IRECIPE_Server.repository.IngredientRepository;
import umc.IRECIPE_Server.web.dto.IngredientRequest;

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
}
