package umc.IRECIPE_Server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.IRECIPE_Server.apiPayLoad.ApiResponse;
import umc.IRECIPE_Server.apiPayLoad.code.status.ErrorStatus;
import umc.IRECIPE_Server.apiPayLoad.exception.ExceptionAdvice;
import umc.IRECIPE_Server.apiPayLoad.exception.GeneralException;
import umc.IRECIPE_Server.dto.PostRequestDTO;
import umc.IRECIPE_Server.dto.PostResponseDTO;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.Post;
import umc.IRECIPE_Server.repository.MemberRepository;
import umc.IRECIPE_Server.repository.PostRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    // requestDto 를 받아 게시글 저장 후 responseDto 생성해서 반환하는 메소드.
    @Transactional // Read only 해제
    public ApiResponse<?> posting(String userId, PostRequestDTO postRequestDto){

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
                .category(postRequestDto.getCategory())
                .level(postRequestDto.getLevel())
                .status(postRequestDto.getStatus())
                .build();


        // 게시글 저장
        postRepository.save(post);

        // responseDTO 생성
        PostResponseDTO postResponseDTO = new PostResponseDTO();
        postResponseDTO.setPostId(post.getId());

        // 응답 반환.
        return ApiResponse.onSuccess(postResponseDTO);
    }
}
