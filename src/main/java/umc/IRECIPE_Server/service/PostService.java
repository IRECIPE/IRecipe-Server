package umc.IRECIPE_Server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.IRECIPE_Server.apiPayLoad.ApiResponse;
import umc.IRECIPE_Server.apiPayLoad.code.status.ErrorStatus;
import umc.IRECIPE_Server.apiPayLoad.code.status.SuccessStatus;
import umc.IRECIPE_Server.apiPayLoad.exception.ExceptionAdvice;
import umc.IRECIPE_Server.apiPayLoad.exception.GeneralException;
import umc.IRECIPE_Server.converter.PostConverter;
import umc.IRECIPE_Server.dto.PostRequestDTO;
import umc.IRECIPE_Server.dto.PostResponseDTO;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.Post;
import umc.IRECIPE_Server.entity.PostImage;
import umc.IRECIPE_Server.repository.MemberRepository;
import umc.IRECIPE_Server.repository.PostImageRepository;
import umc.IRECIPE_Server.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostImageRepository postImageRepository;

    // PostRequestDto 를 받아 DB에 게시글 저장 후 PostResponseDto 생성해서 반환하는 메소드.
    // 게시글 생성 (Create)
    @Transactional // Read only 해제
    public ApiResponse<?> posting(String userId, PostRequestDTO postRequestDto, String url){

        // 리포지토리에서 멤버 찾기.
        Optional<Member> memberOptional = memberRepository.findById(userId);
        Member member;
        if(memberOptional.isEmpty()){
            throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
        }
        member = memberOptional.get();

        // 게시글 객체 생성
        Post post = Post.builder()
                .member(member)
                .title(postRequestDto.getTitle())
                .subhead(postRequestDto.getSubhead())
                .content(postRequestDto.getContent())
                .category(postRequestDto.getCategory())
                .level(postRequestDto.getLevel())
                .status(postRequestDto.getStatus())
                .build();

        // 게시글 사진 객체 생성
        PostImage postImage = PostImage.builder()
                .post(post)
                .imageUrl(url)
                .build();

        // 게시글 저장
        postRepository.save(post);

        // 게시글 사진 저장
        postImageRepository.save(postImage);

        // 응답 반환.
        return ApiResponse.onSuccess(PostConverter.toPostResponseDTO(post));
    }

    // 게시글 단일 조회 (Read)
    public ApiResponse<?> getPost(Long postId, String userId){

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

        // userId 로 유저 찾아서 member 객체에 담기
        Optional<Member> memberOptional = memberRepository.findById(userId);
        Member member;

        if (memberOptional.isEmpty()){
            throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
        }
        member = memberOptional.get();

        return ApiResponse.onSuccess(PostConverter.toGetResponseDTO(post, member, imageUrl));

    }

    // 게시글 수정 후 저장.
    @Transactional
    public ApiResponse<?> updatePost(Long postId, PostRequestDTO postRequestDTO, String url){

        // 리포지토리에서 게시글 찾기. 없으면 에러 출력.
        Optional<Post> postOptional = postRepository.findById(postId);
        Post post;

        if(postOptional.isEmpty()){
            throw new GeneralException(ErrorStatus.POST_NOT_FOUND);
        }
        post = postOptional.get();

        // postImage 찾기
        Optional<PostImage> postImageOptional = postImageRepository.findByPost(post);
        PostImage postImage = postImageOptional.get();

        // postRequestDTO 안의 내용이 null 이 아니면 수정
        if(postRequestDTO.getTitle() != null){
            post.updateTitle(postRequestDTO.getTitle());
        }
        if(postRequestDTO.getSubhead() != null){
            post.updateSubhead(postRequestDTO.getSubhead());
        }
        if(postRequestDTO.getContent() != null){
            post.updateContent(postRequestDTO.getContent());
        }
        if(postRequestDTO.getCategory() != null){
            post.updateCategory(postRequestDTO.getCategory());
        }
        if(postRequestDTO.getLevel() != null){
            post.updateLevel(postRequestDTO.getLevel());
        }
        if(postRequestDTO.getStatus() != null){
            post.updateStatus(postRequestDTO.getStatus());
        }
        if(url != null){
            postImage.updateImage(url);
        }


        // 게시글, 사진 수정 후 저장
        postRepository.save(post);
        postImageRepository.save(postImage);

        return ApiResponse.onSuccess(PostConverter.toUpdateResponseDTO(post, postImage));

    }

    // 게시글 삭제
    @Transactional
    public ApiResponse<?> deletePost(Long postId){

        Optional<Post> postOptional = postRepository.findById(postId);
        Post post;

        if(postOptional.isEmpty()){
            throw new GeneralException(ErrorStatus.POST_NOT_FOUND);
        }
        post = postOptional.get();

        postRepository.delete(post);

        return ApiResponse.onSuccess(SuccessStatus._OK);
    }
}
