package umc.IRECIPE_Server.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.IRECIPE_Server.common.BaseEntity;
import umc.IRECIPE_Server.common.enums.Category;
import umc.IRECIPE_Server.common.enums.Level;
import umc.IRECIPE_Server.common.enums.Status;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 게시글 제목
    private String title;

    // 게시글 소제목
    private String subhead;

    // 게시글 내용
    private String content;

    // 게시글 좋아요 수
    private int likes;

    // 게시글 사진 url
    private String imageUrl;

    // 사진 이름
    private String fileName;

    // 카테고리
    @Enumerated(EnumType.STRING)
    private Category category;

    // 난이도
    @Enumerated(EnumType.STRING)
    private Level level;

    // 임시저장, 등록
    @Enumerated(EnumType.STRING)
    private Status status;

    // 평균 별점
    private Float score;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Query> queryList = new ArrayList<>();

    // 게시글 제목 수정
    public void updateTitle(String title){
        this.title = title;
    }

    // 게시글 소제목 수정
    public void updateSubhead(String subhead){
        this.subhead = subhead;
    }

    // 게시글 내용 수정.
    public void updateContent(String content){
        this.content = content;
    }

    // 카테고리 수정.
    public void updateCategory(Category category){
        this.category = category;
    }

    // 난이도 수정.
    public void updateLevel(Level level){
        this.level = level;
    }

    // 상태 수정
    public void updateStatus(Status status){
        this.status = status;
    }

    public void updateImage(String imageUrl){ this.imageUrl = imageUrl; }

    // 좋아요 수정
    public void updateLikes(int likes) { this.likes = likes; }

    // 평균 별점 수정
    public void updateScore(Float score) { this.score = score; }

}
