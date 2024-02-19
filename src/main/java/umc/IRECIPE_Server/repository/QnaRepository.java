package umc.IRECIPE_Server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.IRECIPE_Server.entity.Qna;

import java.util.List;

public interface QnaRepository extends JpaRepository<Qna, Long> {

    List<Qna> findAllByPost_IdAndParentId(Long postId, Long parentId);

    void deleteAllByParentId(Long parentId);
}