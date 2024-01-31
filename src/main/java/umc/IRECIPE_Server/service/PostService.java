package umc.IRECIPE_Server.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.IRECIPE_Server.apiPayLoad.ApiResponse;
import umc.IRECIPE_Server.apiPayLoad.code.status.ErrorStatus;
import umc.IRECIPE_Server.apiPayLoad.code.status.SuccessStatus;
import umc.IRECIPE_Server.apiPayLoad.exception.GeneralException;
import umc.IRECIPE_Server.converter.ReviewConverter;
import umc.IRECIPE_Server.dto.request.PostRequestDTO;
import umc.IRECIPE_Server.dto.request.ReviewRequestDTO;
import umc.IRECIPE_Server.dto.response.PostResponseDTO;
import umc.IRECIPE_Server.dto.response.ReviewResponseDTO;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.Post;
import umc.IRECIPE_Server.entity.PostImage;
import umc.IRECIPE_Server.entity.Review;
import umc.IRECIPE_Server.repository.MemberRepository;
import umc.IRECIPE_Server.repository.PostImageRepository;
import umc.IRECIPE_Server.repository.PostRepository;
import umc.IRECIPE_Server.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostImageRepository postImageRepository;
    private final ReviewRepository reviewRepository;

    // PostRequestDto 를 받아 DB에 게시글 저장 후 PostResponseDto 생성해서 반환하는 메소드.
    // 게시글 생성 (Create)
    @Transactional // Read only 해제
    public ApiResponse<?> posting(String userId, PostRequestDTO postRequestDto, String url){

        // 리포지토리에서 멤버 찾기.
        //Optional<Member> memberOptional = memberRepository.findByPersonalId(userId);

        Member member = memberRepository.findByPersonalId(userId);
        if(member == null){
            throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
        }

        // 게시글 객체 생성
        Post post = Post.builder()
                .member(member)
                .title(postRequestDto.getTitle())
                .subhead(postRequestDto.getSubhead())
                .category(postRequestDto.getCategory())
                .level(postRequestDto.getLevel())
                .status(postRequestDto.getStatus())
                .build();

        // 게시글 사진 객제 생성
        PostImage postImage = PostImage.builder()
                .post(post)
                .imageUrl(url)
                .build();

        // 게시글 저장
        postRepository.save(post);

        // 게시글 사진 저장
        postImageRepository.save(postImage);

        // responseDTO 생성
        PostResponseDTO postResponseDTO = PostResponseDTO.builder()
                .postId(post.getId())
                .build();

        // 응답 반환.
        return ApiResponse.onSuccess(postResponseDTO);
    }

    // 게시글 단일 조회 (Read)
    public ApiResponse<?> getPost(Long postId){

        // 리포지토리에서 게시글 찾기. 없으면 에러 출력.
        Optional<Post> postOptional = postRepository.findById(postId);
        Post post;

        if(postOptional.isEmpty()){
            throw new GeneralException(ErrorStatus.POST_NOT_FOUND);
        }
        post = postOptional.get();

        // post 객체로 postImage 찾기
        Optional<PostImage> postImageOptional = postImageRepository.findByPost(post);
        PostImage postImage;
        String imageUrl;

        if(postImageOptional.isEmpty()){
            imageUrl = null;
        }
        else{
            postImage = postImageOptional.get();
            imageUrl = postImage.getImageUrl();
        }

        // PostResponseDTO 생성
        PostResponseDTO postResponseDTO = PostResponseDTO.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .subhead(post.getSubhead())
                .category(post.getCategory())
                .content(post.getContent())
                .level(post.getLevel())
                .likes(post.getLikes())
                .score(post.getScore())
                .status(post.getStatus())
                .url(imageUrl)
                .build();

        return ApiResponse.onSuccess(postResponseDTO);

    }

    // 게시글 리뷰 등록
    @Transactional
    public ApiResponse<?> addReview(String memberId, Long postId, ReviewRequestDTO.@Valid addReviewDTO request, String imageUrl) {

        Member member = memberRepository.findByPersonalId(memberId);
        if(member == null){
            throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
        }

        Post post = postRepository.findById(postId).orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));
        Review review = ReviewConverter.addReview(member, post, request, imageUrl);
        reviewRepository.save(review);

        return ApiResponse.onSuccess(SuccessStatus._OK);
    }

    // 게시글 리뷰 조회
    public ApiResponse<List<ReviewResponseDTO.getReviewResponseDTO>> getPostReview(Long postId, int page) {

        // 게시글 조회
        Post post = postRepository.findById(postId).orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));

        // 게시글 id 를 가진 리뷰 조회 (+ 페이지네이션)
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdAt"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Page<Review> reviewList = reviewRepository.findAllByPost(post, pageable);

        return ApiResponse.onSuccess(ReviewConverter.getReview(reviewList));
    }
}