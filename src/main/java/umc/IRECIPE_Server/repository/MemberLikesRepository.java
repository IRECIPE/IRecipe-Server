package umc.IRECIPE_Server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.IRECIPE_Server.entity.MemberLikes;

public interface MemberLikesRepository extends JpaRepository<MemberLikes, Long> {
}
