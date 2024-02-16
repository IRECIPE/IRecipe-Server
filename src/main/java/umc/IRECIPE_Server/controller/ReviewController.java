package umc.IRECIPE_Server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.IRECIPE_Server.apiPayLoad.ApiResponse;
import umc.IRECIPE_Server.converter.ReviewConverter;
import umc.IRECIPE_Server.dto.request.ReviewRequestDTO;
import umc.IRECIPE_Server.dto.response.ReviewResponseDTO;
import umc.IRECIPE_Server.entity.Review;
import umc.IRECIPE_Server.service.reviewService.ReviewService;

import java.io.IOException;
import java.util.List;

@Tag(name = "리뷰", description = "리뷰 관련 API")
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 게시글 리뷰 등록
    @Operation(
            summary = "review 신규 등록하기 API",
            description = "게시글Id(PathVariable)와 리뷰(DTO, MultipartFile) 받아서 신규 등록"
    )
    @PostMapping(value = "/{postId}/review", consumes = "multipart/form-data")
    public ApiResponse<ReviewResponseDTO.addReviewResponseDTO> addPostReview(@PathVariable("postId") Long postId,
                                        @RequestPart(name = "ReviewRequestDTO", required = false) ReviewRequestDTO.addReviewDTO request,
                                        @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        // memberId 값 세팅
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = authentication.getName();

        Review review = reviewService.addReview(memberId, postId, request, file);
        return ApiResponse.onSuccess(ReviewConverter.addReviewResult(review));
    }

    // 게시글 리뷰 조회 (0번 페이지부터 10개씩 최신순으로 조회)
    @Operation(
            summary = "리뷰 조회 API",
            description = "게시글Id(PathVariable)와 페이지 번호 받아서 리뷰 조회"
    )
    @GetMapping("/{postId}/review")
    public ApiResponse<List<ReviewResponseDTO.getReviewResponseDTO>> getPostReview(@PathVariable("postId") Long postId,
                                                                                   @RequestParam(value="page", defaultValue="0") int page) {
        Page<Review> reviewList = reviewService.getPostReview(postId, page);
        return ApiResponse.onSuccess(ReviewConverter.getReviewResult(reviewList));
    }

    // 게시글 리뷰 수정
    @Operation(
            summary = "리뷰 수정 API",
            description = "리뷰Id(PathVariable) 와 리뷰(DTO, MultipartFile) 받아서 수정"
    )
    @PatchMapping(value = "/review/{reviewId}", consumes = "multipart/form-data")
    public ApiResponse<ReviewResponseDTO.updateReviewResponseDTO> updatePostReview(@PathVariable("reviewId") Long reviewId,
                                          @RequestPart(name = "ReviewRequestDTO", required = false) ReviewRequestDTO.addReviewDTO request,
                                          @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {

        reviewService.updatePostReview(reviewId, request, file);
        return ApiResponse.onSuccess(ReviewConverter.updateReviewResult(reviewId));
    }

    // 게시글 리뷰 삭제
    @Operation(
            summary = "리뷰 삭제 API",
            description = "리뷰Id(PathVariable) 받아서 해당 리뷰 삭제"
    )
    @DeleteMapping("/review/{reviewId}")
    public ApiResponse<ReviewResponseDTO.deleteReviewResponseDTO> deletePostReview(@PathVariable("reviewId") Long reviewId) {
        reviewService.deletePostReview(reviewId);
        return ApiResponse.onSuccess(ReviewConverter.deleteReviewResult());
    }
}
