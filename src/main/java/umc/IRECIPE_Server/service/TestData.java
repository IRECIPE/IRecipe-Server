package umc.IRECIPE_Server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import umc.IRECIPE_Server.entity.Post;
import umc.IRECIPE_Server.repository.MemberRepository;
import umc.IRECIPE_Server.repository.PostRepository;

@RequiredArgsConstructor
@Transactional
public class TestData {

    private final PostRepository postRepository;

    public void initData(){
        postRepository.save(Post.builder()
                        .title("테스트1")
                        .subhead("1")
                        .content("테스트")
                .build());

        postRepository.save(Post.builder()
                .title("테스트2")
                .subhead("2")
                .content("테스트")
                .build());

        postRepository.save(Post.builder()
                .title("테스트3")
                .subhead("3")
                .content("테스트")
                .build());

        postRepository.save(Post.builder()
                .title("테스트4")
                .subhead("4")
                .content("테스트")
                .build());

        postRepository.save(Post.builder()
                .title("테스트5")
                .subhead("5")
                .content("테스트")
                .build());

        postRepository.save(Post.builder()
                .title("테스트6")
                .subhead("6")
                .content("테스트")
                .build());
    }
}
