package umc.IRECIPE_Server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import umc.IRECIPE_Server.dto.PostRequestDto;
import umc.IRECIPE_Server.dto.PostResponseDto;
import umc.IRECIPE_Server.entity.Post;
import umc.IRECIPE_Server.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // requestDto 를 받아 게시글 저장 후 responseDto 생성해서 반환하는 메소드.
    public PostResponseDto posting(PostRequestDto postRequestDto){






    }
}
