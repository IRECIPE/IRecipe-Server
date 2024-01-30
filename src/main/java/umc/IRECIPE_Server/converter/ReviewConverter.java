package umc.IRECIPE_Server.converter;

import jakarta.validation.Valid;
import umc.IRECIPE_Server.dto.request.ReviewRequestDTO;
import umc.IRECIPE_Server.dto.response.ReviewResponseDTO;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.Post;
import umc.IRECIPE_Server.entity.Review;

import java.time.LocalDateTime;

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

    // 리뷰 조회
    public static ReviewResponseDTO.getReviewResponseDTO getReview(Review review) {
        return ReviewResponseDTO.getReviewResponseDTO.builder()
                .reviewId(review.getId())
                .memberId(review.getMember().getId())
                .memberImage(review.getMember().getProfileImage())
                .context(review.getContext())
                .score(review.getScore())
                .imageUrl(review.getImageUrl())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
