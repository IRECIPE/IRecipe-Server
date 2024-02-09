package umc.IRECIPE_Server.repository;

import javax.swing.text.html.Option;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import umc.IRECIPE_Server.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByPersonalId(String personalId);
    Optional<Member> findById(Long id);

    Boolean existsByNickname(String nickname);
    Boolean existsByPersonalId(String id);

    Member findByNickname(String nickname);

}
