package umc.IRECIPE_Server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByMember(Member member);
}
