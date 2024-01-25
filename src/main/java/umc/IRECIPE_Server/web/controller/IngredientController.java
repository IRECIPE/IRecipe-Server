package umc.IRECIPE_Server.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.IRECIPE_Server.apiPayLoad.ApiResponse;
import umc.IRECIPE_Server.converter.IngredientConverter;
import umc.IRECIPE_Server.domain.mapping.Ingredient;
import umc.IRECIPE_Server.service.ingredientService.IngredientCommandService;
import umc.IRECIPE_Server.service.ingredientService.IngredientQueryService;
import umc.IRECIPE_Server.web.dto.IngredientRequest;
import umc.IRECIPE_Server.web.dto.IngredientResponse;

import java.util.Optional;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/ingredient")
public class IngredientController {

    private final IngredientCommandService ingredientCommandService;
    private final IngredientQueryService ingredientQueryService;

    @PostMapping("/")
    @Operation(summary = "냉장고에 재료 저장 API",description = "냉장고에서 재료를 저장하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<IngredientResponse.addResultDTO> add(@RequestBody @Valid IngredientRequest.addDTO request) {
        Ingredient ingredient = ingredientCommandService.addIngredient(request);
        return ApiResponse.onSuccess(IngredientConverter.toaddResultDTO(ingredient));
    }

    @GetMapping("/{ingredientId}")
    @Operation(summary = "특정 재료 1개 조회 API",description = "냉장고에서 재료를 클릭했을 때 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "ingredientId", description = "재료의 아이디, path variable 입니다")
    })
    public ApiResponse<IngredientResponse.findOneResultDTO> findOne(@PathVariable(name = "ingredientId") Long ingredientId) {
        Ingredient ingredient = ingredientQueryService.findOne(ingredientId);
       return ApiResponse.onSuccess(IngredientConverter.toFindOneResultDTO(ingredient));
    }

    @DeleteMapping("/{ingredientId}")
    @Operation(summary = "재료 삭제 API",description = "냉장고에서 재료를 삭제하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "ingredientId", description = "재료의 아이디, path variable 입니다")
    })
    public ApiResponse<IngredientResponse.deleteResultDTO> delete(@PathVariable(name = "ingredientId") Long ingredientId) {
        ingredientQueryService.delete(ingredientId);
        return ApiResponse.onSuccess(IngredientConverter.toDeleteResultDTO());
    }
}
