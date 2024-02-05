package umc.IRECIPE_Server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByMember(Member member);

    Page<Post> findAll(Pageable pageable);

    List<Post> findByTitleContaining(String keyword);

    List<Post> findByContentContaining(String keyword);

}
