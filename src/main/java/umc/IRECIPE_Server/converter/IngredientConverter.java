package umc.IRECIPE_Server.converter;


import org.springframework.data.domain.Page;
import umc.IRECIPE_Server.dto.IngredientRequest;
import umc.IRECIPE_Server.dto.IngredientResponse;
import umc.IRECIPE_Server.entity.Ingredient;
import umc.IRECIPE_Server.entity.Member;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class IngredientConverter {

    public static IngredientResponse.addResultDTO toaddResultDTO(Ingredient ingredient) {
        return IngredientResponse.addResultDTO.builder()
                .ingredientId(ingredient.getIngredientId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Ingredient toIngredient(Member member, IngredientRequest.addDTO request) {
        String url = Ingredient.setDefaultImage(request.getCategory());

        return Ingredient.builder()
                .name(request.getName())
                .memo(request.getMemo())
                .category(request.getCategory())
                .type(request.getType())
                .expiry_date(request.getExpiryDate())
                .member(member)
                .imageUrl(url)
                .remainingDays(Ingredient.calculateRemainingDay(request.getExpiryDate()))
                .build();
    }

    public static IngredientResponse.findOneResultDTO toFindOneResultDTO(Ingredient ingredient) {

        return IngredientResponse.findOneResultDTO.builder()
                .ingredientId(ingredient.getIngredientId())
                .name(ingredient.getName())
                .memo(ingredient.getMemo())
                .category(ingredient.getCategory())
                .type(ingredient.getType())
                .expiryDate(ingredient.getExpiry_date())
                .remainingDays(Ingredient.calculateRemainingDay(ingredient.getExpiry_date()))
                .build();

    }

    public static IngredientResponse.findAllResultListDTO toFindAllResultListDTO(Page<Ingredient> ingredientList) {
        List<IngredientResponse.findIngredientResultDTO> ingredientResultDTOList = ingredientList.getContent().stream()
                .map(IngredientConverter::toFindIngredientResultDTO)
                .collect(Collectors.toList());

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
                .ingredientId(ingredient.getIngredientId())
                .name(ingredient.getName())
                .memo(ingredient.getMemo())
                .category(ingredient.getCategory())
                .type(ingredient.getType())
                .expiryDate(ingredient.getExpiry_date())
                .remainingDays(Ingredient.calculateRemainingDay(ingredient.getExpiry_date()))
                .build();
    }

    public static IngredientResponse.deleteResultDTO toDeleteResultDTO() {
        return IngredientResponse.deleteResultDTO.builder()
                .message("재료가 삭제되었습니다.")
                .build();

    }
    public static IngredientResponse.updateResultDTO toUpdateResultDTO(Ingredient ingredient) {
        return IngredientResponse.updateResultDTO.builder()
                .ingredientId(ingredient.getIngredientId())
                .updatedAt(LocalDateTime.now())
                .build();
    }


}
