package umc.IRECIPE_Server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.IRECIPE_Server.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
