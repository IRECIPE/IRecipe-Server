package umc.IRECIPE_Server.service;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import umc.IRECIPE_Server.dto.request.ReviewRequestDTO;
import umc.IRECIPE_Server.entity.Review;

public interface ReviewService {

    // 리뷰 등록
    Review addReview(String memberId, Long postId, ReviewRequestDTO.@Valid addReviewDTO request, MultipartFile file);

    // 리뷰 조회
    Page<Review> getPostReview(Long postId, int page);

    // 리뷰 수정
    void updatePostReview(Long reviewId, ReviewRequestDTO.addReviewDTO request, MultipartFile file);

    // 리뷰 삭제
    void deletePostReview(Long reviewId);
}
