package umc.IRECIPE_Server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ReviewResponseDTO {

    // 리뷰 등록
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class addReviewResponseDTO {
        private Float score;
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
