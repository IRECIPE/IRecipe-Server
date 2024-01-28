package umc.IRECIPE_Server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.IRECIPE_Server.apiPayLoad.ApiResponse;
import umc.IRECIPE_Server.converter.IngredientConverter;
import umc.IRECIPE_Server.dto.IngredientRequest;
import umc.IRECIPE_Server.entity.Ingredient;
import umc.IRECIPE_Server.service.ingredientService.IngredientCommandService;
import umc.IRECIPE_Server.service.ingredientService.IngredientQueryService;
import umc.IRECIPE_Server.web.dto.IngredientResponse;


@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/ingredient")
public class IngredientController {

    private final IngredientCommandService ingredientCommandService;
    private final IngredientQueryService ingredientQueryService;

    @PostMapping("/")
    public ApiResponse<IngredientResponse.addResultDTO> addIngredient(@RequestBody @Valid IngredientRequest.addDTO request) {
        Ingredient ingredient = ingredientCommandService.addIngredient(request);
        return ApiResponse.onSuccess(IngredientConverter.toaddResultDTO(ingredient));
    }

    @GetMapping("/{ingredientId}")
    public ApiResponse<IngredientResponse.findOneResultDTO> findOne(@PathVariable(name = "ingredientId") Long ingredientId) {
        Ingredient ingredient = ingredientQueryService.findOne(ingredientId);
       return ApiResponse.onSuccess(IngredientConverter.toFindOneResultDTO(ingredient));
    }

    @DeleteMapping("/{ingredientId}")
    public ApiResponse<IngredientResponse.deleteResultDTO> deleteIngredient(@PathVariable(name = "ingredientId") Long ingredientId) {
        ingredientQueryService.delete(ingredientId);
        return ApiResponse.onSuccess(IngredientConverter.toDeleteResultDTO());
    }

    @PatchMapping("/{ingredientId}")
    public ApiResponse<IngredientResponse.updateResultDTO> updateIngredient(
            @RequestBody @Valid IngredientRequest.updateDTO request,
            @PathVariable(name = "ingredientId") Long ingredientId) {
        Ingredient ingredient  = ingredientCommandService.updateById(request, ingredientId);
        return ApiResponse.onSuccess(IngredientConverter.toUpdateResultDTO(ingredient));
    }

    @GetMapping("")
    public ApiResponse<IngredientResponse.findAllResultListDTO> findAll(@RequestParam(name = "memberId") Long memberId, @RequestParam(name = "page") Integer page) {
        Page<Ingredient> ingredientList = ingredientQueryService.getIngredientList(memberId, page);
        ingredientList.forEach(ingredient ->
                System.out.println("Controller Ingredient name: " + ingredient.getName()));
        return ApiResponse.onSuccess(IngredientConverter.toFindAllResultListDTO(ingredientList));
    }
}
