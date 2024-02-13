package umc.IRECIPE_Server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.IRECIPE_Server.common.enums.IngredientCategory;
import umc.IRECIPE_Server.common.enums.Type;
import umc.IRECIPE_Server.dto.response.PostResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IngredientResponse {

    @Schema(description = "냉장고 재료 추가 DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class addResultDTO{
        @Schema(description = "재료 아이디")
        Long ingredientId;
        @Schema(description = "재료 생성일시")
        LocalDateTime createdAt;
    }
    @Schema(description = "재료 상세 정보 조회 DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class findOneResultDTO{
        @Schema(description = "재료 아이디")
        Long ingredientId;
        @Schema(description = "재료 이름")
        String name;
        @Schema(description = "재료 카테고리", defaultValue = "MEAT")
        IngredientCategory category;
        @Schema(description = "재료 메모")
        String memo;
        @Schema(description = "재료 보관방법", defaultValue = "FROZEN")
        Type type;
        @Schema(description = "재료 유통기한")
        LocalDate expiryDate;
        @Schema(description = "유통기한까지 남은 일 수")
        Integer remainingDays;
    }

    @Schema(description = "재료 정보 수정 DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class updateResultDTO{
        @Schema(description = "재료 아이디")
        Long ingredientId;
        @Schema(description = "수정 일시")
        LocalDateTime updatedAt;
    }

    @Schema(description = "냉장고 재료 삭제 DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class deleteResultDTO{
        @Schema(description = "완료 메세지", defaultValue = "삭제 완료")
        String message;
    }

    @Schema(description = "냉장고 재료 조회 DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class findIngredientResultDTO{
        @Schema(description = "재료 아이디")
        Long ingredientId;
        @Schema(description = "재료 이름")
        String name;
        @Schema(description = "재료 카테고리", defaultValue = "MEAT")
        IngredientCategory category;
        @Schema(description = "재료 메모")
        String memo;
        @Schema(description = "재료 보관방법", defaultValue = "FROZEN")
        Type type;
        @Schema(description = "재료 유통기한")
        LocalDate expiryDate;
        @Schema(description = "유통기한까지 남은 일 수")
        Integer remainingDays;
    }

    @Schema(description = "조회 리스트 정보DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class findAllResultListDTO{
        @Schema(description = "게시글 리스트")
        List<IngredientResponse.findIngredientResultDTO> ingredientList;
        @Schema(description = "리스트 사이즈")
        Integer listSize;
        @Schema(description = "전체 페이지 갯수")
        Integer totalPage;
        @Schema(description = "전체 데이터 갯수")
        Long totalElements;
        @Schema(description = "첫 페이지면 true")
        Boolean isFirst;
        @Schema(description = " 마지막 페이지면 true")
        Boolean isLast;
    }


}
