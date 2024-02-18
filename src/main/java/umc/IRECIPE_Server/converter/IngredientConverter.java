package umc.IRECIPE_Server.converter;


import org.springframework.data.domain.Page;
import umc.IRECIPE_Server.dto.request.IngredientRequestDTO;
import umc.IRECIPE_Server.dto.response.IngredientResponseDTO;
import umc.IRECIPE_Server.entity.Ingredient;
import umc.IRECIPE_Server.entity.Member;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class IngredientConverter {

    public static IngredientResponseDTO.addResultDTO toaddResultDTO(Ingredient ingredient) {
        return IngredientResponseDTO.addResultDTO.builder()
                .ingredientId(ingredient.getIngredientId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Ingredient toIngredient(Member member, IngredientRequestDTO.addDTO request) {
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

    public static IngredientResponseDTO.findOneResultDTO toFindOneResultDTO(Ingredient ingredient) {

        return IngredientResponseDTO.findOneResultDTO.builder()
                .ingredientId(ingredient.getIngredientId())
                .name(ingredient.getName())
                .memo(ingredient.getMemo())
                .category(ingredient.getCategory())
                .type(ingredient.getType())
                .expiryDate(ingredient.getExpiry_date())
                .remainingDays(Ingredient.calculateRemainingDay(ingredient.getExpiry_date()))
                .build();

    }

    public static IngredientResponseDTO.findAllResultListDTO toFindAllResultListDTO(Page<Ingredient> ingredientList) {
        List<IngredientResponseDTO.findIngredientResultDTO> ingredientResultDTOList = ingredientList.getContent().stream()
                .map(IngredientConverter::toFindIngredientResultDTO)
                .collect(Collectors.toList());

        return IngredientResponseDTO.findAllResultListDTO.builder()
                .isLast(ingredientList.isLast())
                .isFirst(ingredientList.isFirst())
                .totalPage(ingredientList.getTotalPages())
                .totalElements(ingredientList.getTotalElements())
                .listSize(ingredientResultDTOList.size())
                .ingredientList(ingredientResultDTOList)
                .build();
    }

    private static IngredientResponseDTO.findIngredientResultDTO toFindIngredientResultDTO(Ingredient ingredient) {

        return IngredientResponseDTO.findIngredientResultDTO.builder()
                .ingredientId(ingredient.getIngredientId())
                .name(ingredient.getName())
                .memo(ingredient.getMemo())
                .category(ingredient.getCategory())
                .type(ingredient.getType())
                .expiryDate(ingredient.getExpiry_date())
                .remainingDays(Ingredient.calculateRemainingDay(ingredient.getExpiry_date()))
                .build();
    }

    public static IngredientResponseDTO.deleteResultDTO toDeleteResultDTO() {
        return IngredientResponseDTO.deleteResultDTO.builder()
                .message("재료가 삭제되었습니다.")
                .build();

    }
    public static IngredientResponseDTO.updateResultDTO toUpdateResultDTO(Ingredient ingredient) {
        return IngredientResponseDTO.updateResultDTO.builder()
                .ingredientId(ingredient.getIngredientId())
                .updatedAt(LocalDateTime.now())
                .build();
    }


}
