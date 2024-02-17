package umc.IRECIPE_Server.service.reviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import umc.IRECIPE_Server.apiPayLoad.code.status.ErrorStatus;
import umc.IRECIPE_Server.apiPayLoad.exception.GeneralException;
import umc.IRECIPE_Server.common.S3.S3Service;
import umc.IRECIPE_Server.converter.ReviewConverter;
import umc.IRECIPE_Server.dto.request.ReviewRequestDTO;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.Post;
import umc.IRECIPE_Server.entity.Review;
import umc.IRECIPE_Server.repository.MemberRepository;
import umc.IRECIPE_Server.repository.PostRepository;
import umc.IRECIPE_Server.repository.ReviewRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final ReviewRepository reviewRepository;
    private final S3Service s3Service;

    // 게시글 리뷰 등록
    public Review addReview(String memberId, Long postId, ReviewRequestDTO.@Valid addReviewDTO request, MultipartFile file) throws IOException {

        // 사용자 조회
        Optional<Member> member = memberRepository.findByPersonalId(memberId);
        if(member.isEmpty()){
            throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
        }

        // 이미지 경로값 세팅
        String imageUrl = null;
        String fileName = null;
        if (file != null) {
            imageUrl = s3Service.saveFile(file, "images");
            fileName = file.getOriginalFilename();
        }

        // 게시글 평균 별점 수정 : (원래 평균 별점 * 리뷰 개수 + 새로 들어온 별점) / (원래 리뷰 개수 + 1)
        Post post = postRepository.findById(postId).orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));
        int reviewCount = reviewRepository.countByPost_Id(post.getId());
        Float newScore = (post.getScore() * reviewCount + (float)request.getScore()) / (reviewCount + 1);
        post.updateScore(newScore);

        // 리뷰 등록
        Review review = ReviewConverter.addReview(member.get(), post, request, imageUrl, fileName);
        return reviewRepository.save(review);
    }

    // 게시글 리뷰 조회
    public Page<Review> getPostReview(Long postId, int page) {

        // 게시글 조회
        Post post = postRepository.findById(postId).orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));

        // 게시글 id 를 가진 리뷰 조회 (+ 페이지네이션)
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdAt"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return reviewRepository.findAllByPost(post, pageable);
    }

    // 게시글 리뷰 수정
    public void updatePostReview(String memberId, Long reviewId, ReviewRequestDTO.addReviewDTO request, MultipartFile file) throws IOException {

        // 리뷰 조회
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new GeneralException(ErrorStatus.POST_REVIEW_NOT_FOUND));

        // 작성자만 수정 가능
        Optional<Member> member = memberRepository.findByPersonalId(memberId);
        if (member.isEmpty()) {
            throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
        }
        if (member.get().getId() != review.getMember().getId()) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }

        // 기존 사진 S3 삭제
        String oldUrl = review.getImageUrl();
        if (oldUrl != null) {
            s3Service.deleteImage(review.getFileName(), "images");
        }

        // 새로 들어온 사진 S3 업로드
        String newUrl = null;
        String newFileName = null;
        if (file != null) {
            newUrl = s3Service.saveFile(file, "images");
            newFileName = file.getOriginalFilename();
        }

        // 별점 변경 시 게시글 평균 별점 업데이트
        if (request.getScore() != review.getScore()) {
            Post post = postRepository.findById(review.getPost().getId()).orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));
            int reviewCount = reviewRepository.countByPost_Id(post.getId());
            Float newScore = (post.getScore() * reviewCount - (float)review.getScore() + (float)request.getScore()) / reviewCount;
            post.updateScore(newScore);
        }

        // 데이터 업데이트
        review.updateReview(request.getScore(), request.getContext(), newUrl, newFileName);
    }

    // 게시글 리뷰 삭제
    public void deletePostReview(String memberId, Long reviewId) {

        // 리뷰 조회
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new GeneralException(ErrorStatus.POST_REVIEW_NOT_FOUND));

        // 작성자만 삭제 가능
        Optional<Member> member = memberRepository.findByPersonalId(memberId);
        if (member.isEmpty()) {
            throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
        }
        if (member.get().getId() != review.getMember().getId()) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }

        // S3 버킷에 저장된 게시글 리뷰 사진 삭제
        if (review.getImageUrl() != null) {
            s3Service.deleteImage(review.getFileName(), "images");
        }

        // 게시글 평균 별점 업데이트
        Post post = postRepository.findById(review.getPost().getId()).orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));
        int reviewCount = reviewRepository.countByPost_Id(post.getId());
        Float newScore = (post.getScore() * reviewCount - (float)review.getScore()) / (reviewCount - 1);
        post.updateScore(newScore);

        // 게시글 리뷰 삭제
        reviewRepository.deleteById(reviewId);
    }
}