package umc.IRECIPE_Server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.IRECIPE_Server.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
