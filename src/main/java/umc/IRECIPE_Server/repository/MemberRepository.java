package umc.IRECIPE_Server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.IRECIPE_Server.domain.mapping.Ingredient;
import umc.IRECIPE_Server.domain.mapping.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(String username);
}