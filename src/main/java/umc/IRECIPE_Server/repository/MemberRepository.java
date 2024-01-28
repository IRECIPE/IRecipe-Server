package umc.IRECIPE_Server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import umc.IRECIPE_Server.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByPersonalId(String personalId);
    //Optional<Member> findByPersonalId(String personalId);

}
