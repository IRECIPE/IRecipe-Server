package umc.IRECIPE_Server.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ReviewResponseDTO {

    @Schema(description = "리뷰 신규 등록 응답 DTO")
    // 리뷰 등록
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class addReviewResponseDTO {

        @Schema(description = "리뷰 별점", defaultValue = "4")
        private int score;

        @Schema(description = "리뷰 내용", defaultValue = "노력대비 최고의 맛!...")
        private String context;

        @Schema(description = "리뷰 사진 url")
        private String imageUrl;
    }

    // 리뷰 조회
    @Schema(description = "리뷰 단일 조회 응답 DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getReviewResponseDTO {

        @Schema(description = "리뷰 Id", defaultValue = "1")
        private Long reviewId;

        @Schema(description = "유저 Id", defaultValue = "1")
        private Long memberId;

        @Schema(description = "유저 닉네임", defaultValue = "옆집 할머니")
        private String memberNickname;

        @Schema(description = "유저 프로필 사진")
        private String memberImage;

        @Schema(description = "리뷰 내용")
        private String context;

        @Schema(description = "리뷰 별점")
        private int score;

        @Schema(description = "리뷰 사진")
        private String imageUrl;

        @Schema(description = "리뷰 생성일자")
        private LocalDateTime createdAt;
    }

    // 리뷰 수정
    @Schema(description = "리뷰 수정 응답 DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class updateReviewResponseDTO {

        @Schema(description = "리뷰 Id", defaultValue = "1")
        private Long reviewId;

        @Schema(description = "리뷰 수정 응답 메시지")
        private String message;
    }

    // 리뷰 삭제
    @Schema(description = "리뷰 삭제 응답 DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class deleteReviewResponseDTO {

        @Schema(description = "리뷰 삭제 응답 메시지")
        private String message;
    }
}
