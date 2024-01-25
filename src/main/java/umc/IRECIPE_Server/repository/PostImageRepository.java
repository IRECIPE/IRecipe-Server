package umc.IRECIPE_Server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.IRECIPE_Server.entity.PostImage;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
