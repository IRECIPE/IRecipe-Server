package umc.IRECIPE_Server.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.IRECIPE_Server.apiPayLoad.ApiResponse;
import umc.IRECIPE_Server.converter.IngredientConverter;
import umc.IRECIPE_Server.domain.mapping.Ingredient;
import umc.IRECIPE_Server.service.ingredientService.IngredientCommandService;
import umc.IRECIPE_Server.web.dto.IngredientRequest;
import umc.IRECIPE_Server.web.dto.IngredientResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ingredient")
public class IngredientController {

    private final IngredientCommandService ingredientCommandService;

    @PostMapping("/")
    public ApiResponse<IngredientResponse.addResultDTO> add(@RequestBody @Valid IngredientRequest.addDTO request) {
        Ingredient ingredient = ingredientCommandService.addIngredient(request);
        return ApiResponse.onSuccess(IngredientConverter.toaddResultDTO(ingredient));
    }
}
