package umc.IRECIPE_Server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.IRECIPE_Server.entity.DislikedFood;

import java.util.List;
import java.util.Optional;

public interface DislikedFoodRepository extends JpaRepository<DislikedFood, Long> {
    List<DislikedFood> findAllByMember_Id(Long memberId);
}
