package umc.IRECIPE_Server.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Column(name = "post_id")
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

    // 임시저장, 등록, 삭제
    @Enumerated(EnumType.STRING)
    private Status status;

    // 평균 별점
    private Float score;

    //한달 평균 별점
    private Float scoreInOneMonth;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Qna> qnaList = new ArrayList<>();

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

    public void updateScoreInOneMonth(Float score){
        this.scoreInOneMonth = scoreInOneMonth;
    }

    // 이전 30일 동안의 리뷰를 제외한 평균 별점을 계산하는 메서드
    public Float calculateAverageRatingExcludingLast30DaysReviews() {
        // 현재 날짜와 시간을 가져옵니다.
        LocalDateTime currentDateTime = LocalDateTime.now();

        // 현재 날짜와 시간으로부터 30일 전의 LocalDateTime을 계산합니다.
        LocalDateTime thirtyDaysAgoDateTime = currentDateTime.minusDays(30);

        // 30일 전의 LocalDateTime을 LocalDate와 LocalTime으로 분리합니다.
        LocalDate thirtyDaysAgoDate = thirtyDaysAgoDateTime.toLocalDate();
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDateTime thirtyDaysAgo = LocalDateTime.of(thirtyDaysAgoDate, midnight);

        // 이전 30일 동안의 리뷰를 필터링하여 가져옵니다.
        List<Review> filteredReviews = reviewList.stream()
                .filter(review -> review.getCreatedAt().isBefore(thirtyDaysAgo))
                .collect(Collectors.toList());

        // 이전 30일 동안의 리뷰를 제외한 리뷰들의 총 별점과 개수를 계산합니다.
        float totalRatingExcludingLast30DaysReviews = 0;
        int count = 0;
        for (Review review : reviewList) {
            if (!filteredReviews.contains(review)) {
                totalRatingExcludingLast30DaysReviews += review.getScore();
                count++;
            }
        }
        System.out.println("count = " + count);
        scoreInOneMonth = count > 0 ? totalRatingExcludingLast30DaysReviews / count : 0;
        return scoreInOneMonth;
    }

}
