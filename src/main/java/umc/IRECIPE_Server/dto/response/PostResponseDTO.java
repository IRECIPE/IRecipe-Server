package umc.IRECIPE_Server.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import umc.IRECIPE_Server.common.enums.Category;
import umc.IRECIPE_Server.common.enums.Level;
import umc.IRECIPE_Server.common.enums.Status;
import umc.IRECIPE_Server.dto.IngredientResponse;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

@Data
public class PostResponseDTO {
    @Schema(description = "게시글 신규 등록 응답 DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class postDTO{
        @Schema(description = "게시글 Id", defaultValue = "1")
        private Long postId;

    }

    @Schema(description = "임시저장글 응답 DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getTempDTO{
        @Schema(description = "게시글 Id", defaultValue = "1")
        private Long postId;

        @Schema(description = "게시글 제목", defaultValue = "간장게장")
        private String title;

        @Schema(description = "게시글 소제목", defaultValue = "간장게장은 한국의 전통 음식 중 하나로...")
        private String subhead;

        @Schema(description = "게시글 내용", defaultValue = "재료 - 꽃게: 2마리 - 간장: 1컵 ...")
        private String content;

        @Schema(description = "음식 카테고리", defaultValue = "WESTERN/JAPANESE/CHINESE/KOREAN/UNIQUE/SIMPLE/ADVANCED")
        private Category category;

        @Schema(description = "난이도", defaultValue = "EASY/MID/DIFFICULT")
        private Level level;

        @Schema(description = "게시글 상태(임시저장 여부)", defaultValue = "TEMP/POST")
        private Status status;

        @Schema(description = "게시글 사진", defaultValue = "~~~~/images/test.png")
        private String imageUrl;

        @Schema(description = "게시글 생성일", defaultValue = "2024/02/08")
        private LocalDate createdAt;
    }

    @Schema(description = "게시글 단일 조회 응답 DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getDTO{
        @Schema(description = "게시글 Id", defaultValue = "1")
        private Long postId;

        @Schema(description = "게시글 제목", defaultValue = "간장게장")
        private String title;

        @Schema(description = "게시글 소제목", defaultValue = "간장게장은 한국의 전통 음식 중 하나로...")
        private String subhead;

        @Schema(description = "게시글 내용", defaultValue = "재료 - 꽃게: 2마리 - 간장: 1컵 ...")
        private String content;

        @Schema(description = "게시글 관심수", defaultValue = "3")
        private int likes;

        @Schema(description = "음식 카테고리", defaultValue = "WESTERN/JAPANESE/CHINESE/KOREAN/UNIQUE/SIMPLE/ADVANCED")
        private Category category;

        @Schema(description = "난이도", defaultValue = "EASY/MID/DIFFICULT")
        private Level level;

        @Schema(description = "게시글 상태(임시저장 여부)", defaultValue = "TEMP/POST")
        private Status status;

        @Schema(description = "게시글 평균 별점", defaultValue = "4.3")
        private Float score;

        @Schema(description = "게시글 사진", defaultValue = "~~~~/images/test.png")
        private String imageUrl;

        @Schema(description = "작성자 닉네임", defaultValue = "Alber")
        private String writerNickName;

        @Schema(description = "작성자 프로필 사진", defaultValue = "~~~~~/Alber.jpg")
        private String writerImage;

        @Schema(description = "게시글 리뷰 갯수", defaultValue = "13")
        private int reviewsCount;

        @Schema(description = "게시글 생성일", defaultValue = "2024/02/08")
        private LocalDate createdAt;
    }

    @Schema(description = "게시글 수정 응답 DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class updateDTO{
        @Schema(description = "게시글 Id", defaultValue = "1")
        private Long postId;
    }

    @Schema(description = "게시글 관심 버튼 응답 DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class likeDTO{
        @Schema(description = "게시글 Id", defaultValue = "1")
        private Long postId;

        @Schema(description = "게시글 관심수", defaultValue = "3")
        private Long likes;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getAllPostDTO{
        @Schema(description = "게시글 Id", defaultValue = "1")
        private Long postId;

        @Schema(description = "게시글 제목", defaultValue = "간장게장")
        private String title;

        @Schema(description = "게시글 소제목", defaultValue = "간장게장은 한국의 전통 음식 중 하나로...")
        private String subhead;

        @Schema(description = "게시글 사진", defaultValue = "~~~~/images/test.png")
        private String imageUrl;

        @Schema(description = "작성자 닉네임", defaultValue = "Alber")
        private String nickName;

        @Schema(description = "작성자 프로필 사진", defaultValue = "~~~~~/Alber.jpg")
        private String memberImage;

        @Schema(description = "게시글 관심수", defaultValue = "3")
        private int likes;

        @Schema(description = "게시글 평균 별점", defaultValue = "4.3")
        private Float score;

        @Schema(description = "게시글 리뷰 갯수", defaultValue = "13")
        private int reviewsCount;

        @Schema(description = "게시글 생성일", defaultValue = "2024/02/08")
        private LocalDate createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LikePostDTO{
        @Schema(description = "게시글 Id", defaultValue = "1")
        private Long postId;

        @Schema(description = "게시글 관심수", defaultValue = "3")
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
