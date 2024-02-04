package umc.IRECIPE_Server.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import umc.IRECIPE_Server.common.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Qna extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_id")
    private Long id;

    // 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 커뮤니티 게시글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    // 부모 댓글 : null 일 경우 최상위 댓글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Qna parent;

    // 자식 댓글들
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Qna> children = new ArrayList<>();

    // 삭제 여부
    @ColumnDefault("FALSE")
    private Boolean isDeleted;

    // 작성 내용
    private String content;

    // 사진 url
    private String imageUrl;

    // 사진 이름
    private String fileName;

    public void updateParent(Qna qna) {
        this.parent = qna;
    }

}
