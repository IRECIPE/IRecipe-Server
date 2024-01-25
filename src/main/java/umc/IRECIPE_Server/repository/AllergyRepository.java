package umc.IRECIPE_Server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.IRECIPE_Server.entity.Allergy;

public interface AllergyRepository extends JpaRepository<Allergy, Long> {
}
