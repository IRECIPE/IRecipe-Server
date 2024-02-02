package umc.IRECIPE_Server.converter;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import umc.IRECIPE_Server.dto.request.ReviewRequestDTO;
import umc.IRECIPE_Server.dto.response.ReviewResponseDTO;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.Post;
import umc.IRECIPE_Server.entity.Review;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewConverter {

    // 리뷰 등록
    public static Review addReview(Member member, Post post, ReviewRequestDTO.@Valid addReviewDTO request, String imageUrl){
        return Review.builder()
                .member(member)
                .post(post)
                .score(request.getScore())
                .context(request.getContext())
                .imageUrl(imageUrl)
                .build();
    }

    public static ReviewResponseDTO.addReviewResponseDTO addReviewResult(Review review) {
        return ReviewResponseDTO.addReviewResponseDTO.builder()
                .score(review.getScore())
                .context(review.getContext())
                .imageUrl(review.getImageUrl())
                .build();
    }

    // 리뷰 조회
    public static List<ReviewResponseDTO.getReviewResponseDTO> getReviewResult(Page<Review> reviewList) {
        return reviewList.stream()
                .map(m -> ReviewResponseDTO.getReviewResponseDTO.builder()
                        .reviewId(m.getId())
                        .memberId(m.getMember().getId())
                        .memberImage(m.getMember().getProfileImage())
                        .context(m.getContext())
                        .score(m.getScore())
                        .imageUrl(m.getImageUrl())
                        .createdAt(m.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    // 리뷰 수정
    public static ReviewResponseDTO.updateReviewResponseDTO updateReviewResult(Long reviewId) {
        return ReviewResponseDTO.updateReviewResponseDTO.builder()
                .reviewId(reviewId)
                .message("Review updated successfully")
                .build();
    }

    // 리뷰 삭제
    public static ReviewResponseDTO.deleteReviewResponseDTO deleteReviewResult() {
        return ReviewResponseDTO.deleteReviewResponseDTO.builder()
                .message("Review deleted successfully")
                .build();
    }
}