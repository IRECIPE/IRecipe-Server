package umc.IRECIPE_Server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.IRECIPE_Server.entity.Post;
import umc.IRECIPE_Server.entity.PostImage;

import java.util.List;
import java.util.Optional;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    // post 객체로 postImage 찾기
    Optional<PostImage> findByPost(Post post);

    List<PostImage> findAllByPost(Post post);

}
