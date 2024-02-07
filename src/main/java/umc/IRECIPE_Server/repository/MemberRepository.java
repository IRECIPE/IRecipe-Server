package umc.IRECIPE_Server.repository;

import javax.swing.text.html.Option;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import umc.IRECIPE_Server.dto.MemberResponse;
import umc.IRECIPE_Server.entity.Member;

import java.util.Optional;
import umc.IRECIPE_Server.entity.Post;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByPersonalId(String personalId);
    Optional<Member> findById(Long id);

    Boolean existsByNickname(String nickname);

    Member findByNickname(String nickname);

}
