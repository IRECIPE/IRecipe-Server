package umc.IRECIPE_Server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.IRECIPE_Server.entity.MemberAllergy;

public interface MemberAllergyRepository extends JpaRepository<MemberAllergy, Long> {
}
