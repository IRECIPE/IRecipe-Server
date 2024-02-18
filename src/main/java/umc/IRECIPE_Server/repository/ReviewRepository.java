package umc.IRECIPE_Server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.Post;
import umc.IRECIPE_Server.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 게시글 리뷰 조회 (0번 페이지부터 10개씩 최신순으로 조회)
    Page<Review> findAllByPost(Post past, Pageable pageable);

    List<Review> findAllByMember(Member member);

    // 게시글의 리뷰 개수 조회
    int countByPost_Id(Long postId);
}
