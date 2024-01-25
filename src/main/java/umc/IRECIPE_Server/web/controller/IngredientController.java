package umc.IRECIPE_Server.web.controller;

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
import umc.IRECIPE_Server.domain.mapping.Ingredient;
import umc.IRECIPE_Server.service.ingredientService.IngredientCommandService;
import umc.IRECIPE_Server.service.ingredientService.IngredientQueryService;
import umc.IRECIPE_Server.web.dto.IngredientRequest;
import umc.IRECIPE_Server.web.dto.IngredientResponse;

import java.util.List;
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
    public ApiResponse<IngredientResponse.addResultDTO> addIngredient(@RequestBody @Valid IngredientRequest.addDTO request) {
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
    public ApiResponse<IngredientResponse.deleteResultDTO> deleteIngredient(@PathVariable(name = "ingredientId") Long ingredientId) {
        ingredientQueryService.delete(ingredientId);
        return ApiResponse.onSuccess(IngredientConverter.toDeleteResultDTO());
    }

    @PatchMapping("/{ingredientId}")
    @Operation(summary = "재료 정보 수정 API",description = "냉장고에서 재료의 정보를를 수정하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "ingredientId", description = "재료의 아이디, path variable 입니다")
    })
    public ApiResponse<IngredientResponse.updateResultDTO> updateIngredient(
            @RequestBody @Valid IngredientRequest.updateDTO request,
            @PathVariable(name = "ingredientId") Long ingredientId) {
        Ingredient ingredient  = ingredientCommandService.updateById(request, ingredientId);
        return ApiResponse.onSuccess(IngredientConverter.toUpdateResultDTO(ingredient));
    }

    @GetMapping("")
    @Operation(summary = "재료 전체 조회 API", description = "냉장고에 존재하는 재료들을 전부 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "memberId", description = "회원의 아이디, request param 입니다!"),
            @Parameter(name = "page", description = "페이지 번호, 0번이 1 페이지 입니다."),
    })
    public ApiResponse<IngredientResponse.findAllResultListDTO> findAll(@RequestParam(name = "memberId") Long memberId, @RequestParam(name = "page") Integer page) {
        Page<Ingredient> ingredientList = ingredientQueryService.getIngredientList(memberId, page);
        ingredientList.forEach(ingredient ->
                System.out.println("Controller Ingredient name: " + ingredient.getName()));
        return ApiResponse.onSuccess(IngredientConverter.toFindAllResultListDTO(ingredientList));
    }
}
