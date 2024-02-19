package umc.IRECIPE_Server.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.IRECIPE_Server.common.enums.IngredientCategory;
import umc.IRECIPE_Server.common.enums.Type;


import java.time.LocalDate;


public class IngredientRequestDTO {

    @Schema(description = "냉장고 재료 추가 DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class addDTO{
        @Schema(description = "재료 이름", defaultValue = "소고기")
        String name;
        @Schema(description = "재료 카테고리", defaultValue = "MEAT")
        IngredientCategory category;
        @Schema(description = "재료 메모", defaultValue = "빨리 먹어야 함")
        String memo;
        @Schema(description = "재료 보관방법", defaultValue = "FROZEN")
        Type type;
        @Schema(description = "재료 유통기한", defaultValue = "2025-02-09")
        LocalDate expiryDate;
    }

    @Schema(description = "냉장고 재료 수정 DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class updateDTO{
        @Schema(description = "재료 이름", defaultValue = "소고기")
        String name;
        @Schema(description = "재료 카테고리", defaultValue = "MEAT")
        IngredientCategory category;
        @Schema(description = "재료 메모", defaultValue = "빨리 먹어야 함")
        String memo;
        @Schema(description = "재료 보관방법", defaultValue = "FROZEN")
        Type type;
        @Schema(description = "재료 유통기한", defaultValue = "2025-02-09")
        LocalDate expiryDate;
    }


}
