package umc.IRECIPE_Server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.MemberLikes;
import umc.IRECIPE_Server.entity.Post;

import java.util.Optional;

public interface MemberLikesRepository extends JpaRepository<MemberLikes, Long> {

    Optional<MemberLikes> findByMemberAndPost(Member member, Post post);

    Optional<MemberLikes> findByMember(Member member);
}
