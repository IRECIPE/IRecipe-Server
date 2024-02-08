package umc.IRECIPE_Server.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ReviewResponseDTO {

    @Schema(description = "리뷰 등록 응답 DTO")
    // 리뷰 등록
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class addReviewResponseDTO {

        @Schema(description = "리뷰 별점", defaultValue = "4.3")
        private Float score;

        @Schema(description = "리뷰 내용")
        private String context;


        private String imageUrl;
    }

    // 리뷰 조회
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getReviewResponseDTO {
        private Long reviewId;
        private Long memberId;
        private String memberNickname;
        private String memberImage;
        private String context;
        private Float score;
        private String imageUrl;
        private LocalDateTime createdAt;
    }

    // 리뷰 수정
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class updateReviewResponseDTO {
        private Long reviewId;
        private String message;
    }

    // 리뷰 삭제
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class deleteReviewResponseDTO {
        private String message;
    }
}
