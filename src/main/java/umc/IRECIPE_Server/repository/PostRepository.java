package umc.IRECIPE_Server.repository;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import umc.IRECIPE_Server.common.enums.Category;
import umc.IRECIPE_Server.common.enums.Status;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.Post;

import java.time.LocalDateTime;
import java.util.List;
import umc.IRECIPE_Server.entity.Review;

public interface PostRepository extends JpaRepository<Post, Long> {


    List<Post> findByMember(Member member);

    Page<Post> findAllByStatus(Pageable pageable, Status status);

    Page<Post> findByTitleContainingAndStatus(Pageable pageable, String keyword, Status status);

    Page<Post> findByContentContainingAndStatus(Pageable pageable, String keyword, Status status);

    Page<Post> findByMemberAndStatus(Pageable pageable, Member member, Status status);

    Post findByStatusAndId(Status status, Long id);

    List<Post> findAllByMember(Member member);

    @Query("select p from Post p where p.status = 'POST' " +
            "order by p.scoreInOneMonth desc, p.likes desc")
    Page<Post> findRankedPost(PageRequest pageRequest);

    @Query("select p from Post p where p.status = 'POST' and p.category = :category " +
            "order by p.scoreInOneMonth desc, p.likes desc")
    Page<Post> findCategoryRankedPost(PageRequest pageRequest, @Param("category") Category category);
}
