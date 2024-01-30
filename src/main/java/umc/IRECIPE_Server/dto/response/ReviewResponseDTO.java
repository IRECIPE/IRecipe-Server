package umc.IRECIPE_Server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ReviewResponseDTO {

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
}
