package umc.IRECIPE_Server.dto.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import umc.IRECIPE_Server.common.enums.Category;
import umc.IRECIPE_Server.common.enums.Level;
import umc.IRECIPE_Server.common.enums.Status;
import umc.IRECIPE_Server.dto.IngredientResponse;

import java.time.LocalDate;
import java.util.List;

@Data
public class PostResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class postDTO{
        // 게시글 id
        private Long postId;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getTempDTO{
        // 게시글 id
        private Long postId;
        // 게시글 제목
        private String title;
        // 게시글 소제목
        private String subhead;
        // 게시글 내용
        private String content;
        // 카테고리
        private Category category;
        // 난이도
        private Level level;
        // 임시저장, 등록
        private Status status;
        // 게시글 이미지
        private String imageUrl;
        // 생성일
        private LocalDate createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getDTO{
        // 게시글 id
        private Long postId;
        // 게시글 제목
        private String title;
        // 게시글 소제목
        private String subhead;
        // 게시글 내용
        private String content;
        // 게시글 좋아요 수
        private int likes;
        // 카테고리
        private Category category;
        // 난이도
        private Level level;
        // 임시저장, 등록
        private Status status;
        // 평균 별점
        private Float score;
        // 게시글 사진
        private String imageUrl;
        // 작성자 이름
        private String writerNickName;
        // 작성자 프로필 사진
        private String writerImage;
        // 생성일
        private LocalDate createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class updateDTO{
        // 게시글 id
        private Long postId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class likeDTO{
        // 게시글 id
        private Long postId;
        // 게시글 좋아요 수
        private Long likes;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getAllPostDTO{
        // 게시글 id
        private Long postId;
        // 게시글 제목
        private String title;
        // 게시글 소제목
        private String subhead;
        // 게시글 사진
        private String imageUrl;
        // 사용자 별명
        private String nickName;
        // 사용자 사진
        private String memberImage;
        // 게시글 관심수
        private int likes;
        // 게시글 평균 별점
        private Float score;
        // 게시글 리뷰 갯수
        private int reviewsCount;
        // 게시글 생성 날짜
        private LocalDate createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LikePostDTO{
        private Long postId;
        private int likes;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getRankedPostDTO {
        private Long postId;
        private String title;
        private String imageUrl;
        private Integer likes;
        private Float scores;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class findAllResultListDTO{
        List<PostResponseDTO.getRankedPostDTO> postList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }
}
