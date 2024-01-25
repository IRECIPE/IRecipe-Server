package umc.IRECIPE_Server.converter;

import org.springframework.data.domain.Page;
import umc.IRECIPE_Server.domain.mapping.Ingredient;
import umc.IRECIPE_Server.domain.mapping.IngredientCategory;
import umc.IRECIPE_Server.web.dto.IngredientRequest;
import umc.IRECIPE_Server.web.dto.IngredientResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class IngredientConverter {

    public static IngredientResponse.addResultDTO toaddResultDTO(Ingredient ingredient) {
        return IngredientResponse.addResultDTO.builder()
                .ingredientId(ingredient.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Ingredient toIngredient(IngredientRequest.addDTO request) {

        IngredientCategory category = IngredientCategory.builder()
                .name(request.getCategory())
                .type(request.getType())
                .expiryDate(request.getExpiryDate())
                .build();


        return Ingredient.builder()
                .name(request.getName())
                .image(request.getImage())
                .memo(request.getMemo())
                .ingredientCategory(category)
                .build();
    }

    public static IngredientResponse.findOneResultDTO toFindOneResultDTO(Ingredient ingredient) {

        return IngredientResponse.findOneResultDTO.builder()
                .name(ingredient.getName())
                .image(ingredient.getImage())
                .memo(ingredient.getMemo())
                .category(ingredient.getIngredientCategory().getName())
                .type(ingredient.getIngredientCategory().getType())
                .expiryDate(ingredient.getIngredientCategory().getExpiryDate())
                .build();

    }

    public static IngredientResponse.findAllResultListDTO toFindAllResultListDTO(Page<Ingredient> ingredientList) {
        List<IngredientResponse.findIngredientResultDTO> ingredientResultDTOList = ingredientList.getContent().stream()
                .map(IngredientConverter::toFindIngredientResultDTO)
                .collect(Collectors.toList());

        ingredientResultDTOList.forEach(resultDTO ->
                System.out.println("Ingredient name: " + resultDTO.getName()));

        return IngredientResponse.findAllResultListDTO.builder()
                .isLast(ingredientList.isLast())
                .isFirst(ingredientList.isFirst())
                .totalPage(ingredientList.getTotalPages())
                .totalElements(ingredientList.getTotalElements())
                .listSize(ingredientResultDTOList.size())
                .ingredientList(ingredientResultDTOList)
                .build();
    }

    private static IngredientResponse.findIngredientResultDTO toFindIngredientResultDTO(Ingredient ingredient) {

        return IngredientResponse.findIngredientResultDTO.builder()
                .name(ingredient.getName())
                .image(ingredient.getImage())
                .memo(ingredient.getMemo())
                .category(ingredient.getIngredientCategory().getName())
                .type(ingredient.getIngredientCategory().getType())
                .expiryDate(ingredient.getIngredientCategory().getExpiryDate())
                .build();
    }

    public static IngredientResponse.deleteResultDTO toDeleteResultDTO() {
        return IngredientResponse.deleteResultDTO.builder()
                .message("재료가 삭제되었습니다.")
                .build();

    }
    public static IngredientResponse.updateResultDTO toUpdateResultDTO(Ingredient ingredient) {
        return IngredientResponse.updateResultDTO.builder()
                .ingredientId(ingredient.getId())
                .updatedAt(LocalDateTime.now())
                .build();
    }


}
