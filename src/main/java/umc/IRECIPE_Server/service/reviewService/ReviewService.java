package umc.IRECIPE_Server.service.reviewService;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import umc.IRECIPE_Server.dto.request.ReviewRequestDTO;
import umc.IRECIPE_Server.entity.Review;

import java.io.IOException;

public interface ReviewService {

    // 리뷰 등록
    Review addReview(String memberId, Long postId, ReviewRequestDTO.@Valid addReviewDTO request, MultipartFile file) throws IOException;

    // 리뷰 조회
    Page<Review> getPostReview(Long postId, int page);

    // 리뷰 수정
    void updatePostReview(String memberId, Long reviewId, ReviewRequestDTO.addReviewDTO request, MultipartFile file) throws IOException;

    // 리뷰 삭제
    void deletePostReview(String memberId, Long reviewId);
}
