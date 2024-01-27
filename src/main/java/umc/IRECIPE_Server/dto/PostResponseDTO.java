package umc.IRECIPE_Server.dto;

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
    public static class postResponseDTO{
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
    public static class getResponseDTO{
        // 게시글 id
        private Long postId;
        // 게시글 제목
        private String title;
        // 게시글 소제목
        private String subhead;
        // 게시글 내용
        private String content;
        // 게시글 좋아요 수
        private Long likes;
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
    public static class updateResponseDTO{
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
        // 평균 별점
        private Float score;
        // 게시글 좋아요 수
        private Long likes;
        // 게시글 사진
        private String imageUrl;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class likeResponseDTO{
        // 게시글 id
        private Long postId;
        // 게시글 좋아요 수
        private Long likes;
    }

//    @Builder
//    @Getter
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class AllPostResponseDTO{
//
//    }
}
