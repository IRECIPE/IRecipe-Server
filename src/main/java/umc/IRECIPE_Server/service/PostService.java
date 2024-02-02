package umc.IRECIPE_Server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.IRECIPE_Server.apiPayLoad.ApiResponse;
import umc.IRECIPE_Server.apiPayLoad.code.status.ErrorStatus;
import umc.IRECIPE_Server.apiPayLoad.code.status.SuccessStatus;
import umc.IRECIPE_Server.apiPayLoad.exception.GeneralException;
import umc.IRECIPE_Server.common.enums.Status;
import umc.IRECIPE_Server.converter.PostConverter;
import umc.IRECIPE_Server.dto.request.PostRequestDTO;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.Post;
import umc.IRECIPE_Server.repository.MemberRepository;
import umc.IRECIPE_Server.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    // postId 로 Post 찾고 Null 이면 예외 출력하는 메소드
    public Post findByPostId(Long postId){
        Optional<Post> postOptional = postRepository.findById(postId);

        // 만약 post 가 존재하지 않으면 ErrorStatus.POST_NOT_FOUND 반환.
        return postOptional.orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));
    }

    // PostRequestDto를 받아 DB에 게시글 저장 후 PostResponseDto 생성해서 반환하는 메소드.
    // 게시글 생성 (Create)
    public ApiResponse<?> posting(String userId, PostRequestDTO.newRequestDTO postRequestDto, String url){

        Optional<Member> memberOptional = memberRepository.findByPersonalId(userId);
        memberOptional.orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        // 게시글 객체 생성
        Post post = Post.builder()
                .member(memberOptional.get())
                .title(postRequestDto.getTitle())
                .subhead(postRequestDto.getSubhead())
                .content(postRequestDto.getContent())
                .category(postRequestDto.getCategory())
                .level(postRequestDto.getLevel())
                .imageUrl(url)
                .status(postRequestDto.getStatus())
                .build();

        // 게시글 저장
        postRepository.save(post);

        // 응답 반환
        return ApiResponse.onSuccess(PostConverter.toPostResponseDTO(post));
    }

    // 게시글 임시저장 불러오기
    public ApiResponse<?> newOrTemp(String userId){
        // 멤버 찾기.
        Optional<Member> memberOptional = memberRepository.findByPersonalId(userId);
        // null 이면 예외 처리 (NosuchElementException)
        memberOptional.orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));


        // 멤버에 매핑된 게시글 모두 찾아서 임시저장된 글 있으면 반환.
        List<Post> posts = postRepository.findAllByMember(memberOptional.get());
        for (Post post : posts) {
            if(post.getStatus() == Status.TEMP){
                return ApiResponse.onSuccess(PostConverter.toTempResponseDTO(post));
            }
        }

        return ApiResponse.onSuccess("임시 저장된 글이 없습니다.");
    }

    // 게시글 단일 조회 (Read)
    public ApiResponse<?> getPost(Long postId, String userId){

        Post post = findByPostId(postId);

        // userId 로 유저 찾아서 member 객체에 담기
        Optional<Member> memberOptional = memberRepository.findByPersonalId(userId);
        memberOptional.orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        return ApiResponse.onSuccess(PostConverter.toGetResponseDTO(post, memberOptional.get()));

    }

    // 게시글 수정 후 저장.
    public ApiResponse<?> updatePost(Long postId, PostRequestDTO.patchRequestDTO postRequestDTO, String newUrl){

        // 해당 게시글 찾음.
        Post post = findByPostId(postId);
        if(post == null){
            throw new GeneralException(ErrorStatus.POST_NOT_FOUND);
        }

        // 게시글 수정
        post.updateTitle(postRequestDTO.getTitle());

        post.updateSubhead(postRequestDTO.getSubhead());

        post.updateContent(postRequestDTO.getContent());

        post.updateCategory(postRequestDTO.getCategory());

        post.updateLevel(postRequestDTO.getLevel());

        post.updateStatus(postRequestDTO.getStatus());

        post.updateImage(newUrl);

        // 게시글 수정 후 저장
        postRepository.save(post);

        return ApiResponse.onSuccess(PostConverter.toUpdateResponseDTO(post));

    }

    // 게시글 삭제
    public ApiResponse<?> deletePost(Long postId){

        Post post = findByPostId(postId);

        postRepository.delete(post);

        return ApiResponse.onSuccess(SuccessStatus._OK);
    }
}