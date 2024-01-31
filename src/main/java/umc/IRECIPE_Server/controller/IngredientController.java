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

    //재료추가
    @PostMapping("/")
    public ApiResponse<IngredientResponse.addResultDTO> addIngredient(@RequestBody @Valid IngredientRequest.addDTO request) {
        Ingredient ingredient = ingredientCommandService.addIngredient(request);
        return ApiResponse.onSuccess(IngredientConverter.toaddResultDTO(ingredient));
    }

    //재료 상세정보 조회
    @GetMapping("/{ingredientId}")
    public ApiResponse<IngredientResponse.findOneResultDTO> findOne(@PathVariable(name = "ingredientId") Long ingredientId) {
        Ingredient ingredient = ingredientQueryService.findOne(ingredientId);
       return ApiResponse.onSuccess(IngredientConverter.toFindOneResultDTO(ingredient));
    }

    //재료 삭제
    @DeleteMapping("/{ingredientId}")
    public ApiResponse<IngredientResponse.deleteResultDTO> deleteIngredient(@PathVariable(name = "ingredientId") Long ingredientId) {
        ingredientQueryService.delete(ingredientId);
        return ApiResponse.onSuccess(IngredientConverter.toDeleteResultDTO());
    }

    //재료 수정
    @PatchMapping("/{ingredientId}")
    public ApiResponse<IngredientResponse.updateResultDTO> updateIngredient(
            @RequestBody @Valid IngredientRequest.updateDTO request,
            @PathVariable(name = "ingredientId") Long ingredientId) {
        Ingredient ingredient  = ingredientCommandService.updateById(request, ingredientId);
        return ApiResponse.onSuccess(IngredientConverter.toUpdateResultDTO(ingredient));
    }


    //재료 전체 조회
    @GetMapping("/")
    public ApiResponse<IngredientResponse.findAllResultListDTO> findAll(@RequestParam(name = "memberId") Long memberId, @RequestParam(name = "page") Integer page) {
        Page<Ingredient> ingredientList = ingredientQueryService.getIngredientList(memberId, page);
        return ApiResponse.onSuccess(IngredientConverter.toFindAllResultListDTO(ingredientList));
    }

    //냉동 보관 재료 리스트 조회
    @GetMapping("/refrigerated")
    public ApiResponse<IngredientResponse.findAllResultListDTO> findRefrigeratedList(@RequestParam(name = "memberId") Long memberId, @RequestParam(name = "page") Integer page) {
        Page<Ingredient> ingredientList = ingredientQueryService.getRefrigeratedIngredientList(memberId, page);
        return ApiResponse.onSuccess(IngredientConverter.toFindAllResultListDTO(ingredientList));
    }
    @GetMapping("/ambient")
    public ApiResponse<IngredientResponse.findAllResultListDTO> findAmbientList(@RequestParam(name = "memberId") Long memberId, @RequestParam(name = "page") Integer page) {
        Page<Ingredient> ingredientList = ingredientQueryService.getAmbientIngredientList(memberId, page);
        return ApiResponse.onSuccess(IngredientConverter.toFindAllResultListDTO(ingredientList));
    }
    @GetMapping("/frozen")
    public ApiResponse<IngredientResponse.findAllResultListDTO> findFrozenList(@RequestParam(name = "memberId") Long memberId, @RequestParam(name = "page") Integer page) {
        Page<Ingredient> ingredientList = ingredientQueryService.getFrozenIngredientList(memberId, page);
        return ApiResponse.onSuccess(IngredientConverter.toFindAllResultListDTO(ingredientList));
    }
}
