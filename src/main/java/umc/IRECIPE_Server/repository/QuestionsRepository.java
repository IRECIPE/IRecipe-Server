package umc.IRECIPE_Server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.IRECIPE_Server.entity.Query;

public interface QuestionsRepository extends JpaRepository<Query, Long> {
}
