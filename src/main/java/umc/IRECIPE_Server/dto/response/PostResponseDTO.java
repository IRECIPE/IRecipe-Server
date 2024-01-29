package umc.IRECIPE_Server.dto.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import umc.IRECIPE_Server.common.enums.Category;
import umc.IRECIPE_Server.common.enums.Level;
import umc.IRECIPE_Server.common.enums.Status;

@Data
public class PostResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class postDTO{
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
        // 게시글 사진 (일단 단일 사진만 구현)
        private String imageUrl;
        // 작성자 이름
        private String writerNickName;
        // 작성자 프로필 사진
        private String writerImage;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class updateDTO{
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
        // 좋아요 수
        private int likes;
        // 평균 별점
        private Float score;
        // 게시글 사진
        private String imageUrl;
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
    public static class getAllDTO{
        // 게시글 id
        private Long postId;
        // 게시글 제목
        private String title;
        // 게시글 소제목
        private String subhead;
        // 사용자 별명
        private String nickName;
        // 사용자 사진
        private String memberImage;

    }
}
