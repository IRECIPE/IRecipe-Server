package umc.IRECIPE_Server.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.Qna;

public interface QnaRepository extends JpaRepository<Qna, Long> {
    List<Qna> findAllByMember(Member member);
}