package umc.IRECIPE_Server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.IRECIPE_Server.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
