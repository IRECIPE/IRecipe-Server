package umc.IRECIPE_Server.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.IRECIPE_Server.entity.MemberAllergy;

public interface MemberAllergyRepository extends JpaRepository<MemberAllergy, Long> {
    MemberAllergy findByAllergy_IdAndMember_Id(Long memberId, Long allergyId);
}
