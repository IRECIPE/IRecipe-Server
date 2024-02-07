package umc.IRECIPE_Server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.IRECIPE_Server.apiPayLoad.ApiResponse;
import umc.IRECIPE_Server.apiPayLoad.code.status.ErrorStatus;
import umc.IRECIPE_Server.apiPayLoad.exception.GeneralException;
import umc.IRECIPE_Server.common.S3.S3Service;
import umc.IRECIPE_Server.common.enums.Type;
import umc.IRECIPE_Server.converter.IngredientConverter;
import umc.IRECIPE_Server.dto.IngredientRequest;
import umc.IRECIPE_Server.dto.IngredientResponse;
import umc.IRECIPE_Server.entity.Ingredient;
import umc.IRECIPE_Server.service.ingredientService.IngredientCommandService;
import umc.IRECIPE_Server.service.ingredientService.IngredientQueryService;

import java.io.IOException;


@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/ingredients")
public class IngredientController {

    private final S3Service s3Service;
    private final IngredientCommandService ingredientCommandService;
    private final IngredientQueryService ingredientQueryService;

    //재료추가
    @PostMapping(value = "/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse<IngredientResponse.addResultDTO> addIngredient(@RequestPart("request") IngredientRequest.addDTO request)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        Ingredient ingredient = ingredientCommandService.addIngredient(userId, request);
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
    public ApiResponse<IngredientResponse.findAllResultListDTO> findAll(@RequestParam(name = "page") Integer page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        Page<Ingredient> ingredientList = ingredientQueryService.getIngredientList(userId, page);
        return ApiResponse.onSuccess(IngredientConverter.toFindAllResultListDTO(ingredientList));
    }

    //보관 방법별 재료 리스트 조회
    @GetMapping("/types")
    public ApiResponse<IngredientResponse.findAllResultListDTO> findRefrigeratedList(@RequestParam(name = "page") Integer page,
                                                                                     @RequestParam(name = "type") Type type
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        Page<Ingredient> ingredientList = ingredientQueryService.getIngredientListByType(userId, type, page);
        return ApiResponse.onSuccess(IngredientConverter.toFindAllResultListDTO(ingredientList));
    }

    //재료 이름 검색
    @GetMapping("/search")
    public ApiResponse<IngredientResponse.findAllResultListDTO> searchIngredient(@RequestParam(name = "name") String name,
                                                                                 @RequestParam(name = "page") Integer page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        Page<Ingredient> ingredientList = ingredientQueryService.searchIngredientByName(userId, name, page);
        return ApiResponse.onSuccess(IngredientConverter.toFindAllResultListDTO(ingredientList));
    }

    @GetMapping("/expire")
    public ApiResponse<IngredientResponse.findAllResultListDTO> findNearingExpirationIngredient(@RequestParam(name = "page") Integer page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        Page<Ingredient> ingredientList = ingredientQueryService.getNearingExpirationIngredientList(userId, page);
        return ApiResponse.onSuccess(IngredientConverter.toFindAllResultListDTO(ingredientList));
    }
}
