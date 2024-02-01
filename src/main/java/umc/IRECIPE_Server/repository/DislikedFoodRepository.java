package umc.IRECIPE_Server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.IRECIPE_Server.entity.DislikedFood;

public interface DislikedFoodRepository extends JpaRepository<DislikedFood, Long> {
}
