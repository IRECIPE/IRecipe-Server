package umc.IRECIPE_Server.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import umc.IRECIPE_Server.entity.Post;
import umc.IRECIPE_Server.entity.Qna;

import java.util.List;

import static umc.IRECIPE_Server.entity.QQna.qna;

@Repository
@RequiredArgsConstructor
public class QnaCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    // 커뮤니티 게시글 Qna 댓글 전체 가져오기
    public List<Qna> findAllByPost(Post post) {
        return jpaQueryFactory.selectFrom(qna)
                .leftJoin(qna.parent)
                .fetchJoin()
                .where(qna.post.id.eq(post.getId()))
                .orderBy(qna.parent.id.asc().nullsFirst(), qna.createdAt.asc())
                .fetch();
    }

}
