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

        @Schema(description = "음식 카테고리", defaultValue = "WESTERN")
        private Category category;

        @Schema(description = "난이도", defaultValue = "EASY")
        private Level level;

        @Schema(description = "게시글 상태(임시저장 여부)", defaultValue = "POST")
        private Status status;

        @Schema(description = "게시글 사진")
        private String imageUrl;

        @Schema(description = "게시글 생성일")
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

        @Schema(description = "음식 카테고리", defaultValue = "WESTERN")
        private Category category;

        @Schema(description = "난이도", defaultValue = "EASY")
        private Level level;

        @Schema(description = "게시글 상태(임시저장 여부)", defaultValue = "POST")
        private Status status;

        @Schema(description = "게시글 평균 별점", defaultValue = "4.3")
        private Float score;

        @Schema(description = "게시글 사진")
        private String imageUrl;

        @Schema(description = "작성자 닉네임", defaultValue = "Alber")
        private String writerNickName;

        @Schema(description = "작성자 프로필 사진")
        private String writerImage;

        @Schema(description = "게시글 리뷰 갯수", defaultValue = "13")
        private int reviewsCount;

        @Schema(description = "해당 사용자가 관심 눌렀는지", defaultValue = "false")
        private boolean likeOrNot;

        @Schema(description = "내가 쓴 글인지 아닌지", defaultValue = "false")
        private boolean myPost;

        @Schema(description = "게시글 생성일")
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

    @Schema(description = "커뮤니티 화면 조회 응답 DTO")
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

        @Schema(description = "게시글 사진")
        private String imageUrl;

        @Schema(description = "작성자 닉네임", defaultValue = "Alber")
        private String nickName;

        @Schema(description = "작성자 프로필 사진")
        private String memberImage;

        @Schema(description = "게시글 관심수", defaultValue = "3")
        private int likes;

        @Schema(description = "게시글 평균 별점", defaultValue = "4.3")
        private Float score;

        @Schema(description = "게시글 리뷰 갯수", defaultValue = "13")
        private int reviewsCount;

        @Schema(description = "게시글 생성일")
        private LocalDate createdAt;

        @Schema(description = "해당 사용자가 관심 눌렀는지", defaultValue = "false")
        private boolean likeOrNot;
    }

    @Schema(description = "게시글 관심 응답 DTO")
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

    @Schema(description = "이달의 랭킹 게시글 응답 DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getRankedPostDTO {

        @Schema(description = "게시글 Id", defaultValue = "1")
        private Long postId;

        @Schema(description = "게시글 제목", defaultValue = "간장게장")
        private String title;

        @Schema(description = "게시글 사진")
        private String imageUrl;

        @Schema(description = "게시글 관심수", defaultValue = "3")
        private Integer likes;

        @Schema(description = "게시글 평균 별점", defaultValue = "4.3")
        private Float scores;

        @Schema(description = "게시글 한달 평균 별점", defaultValue = "4.3")
        private Float scoresInOneMonth;

    }

    @Schema(description = "이달의 랭킹 게시글 리스트 DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class findAllResultListDTO{
        @Schema(description = "게시글 리스트")
        List<PostResponseDTO.getRankedPostDTO> postList;
        @Schema(description = "리스트 사이즈")
        Integer listSize;
        @Schema(description = "전체 페이지 갯수")
        Integer totalPage;
        @Schema(description = "전체 데이터 갯수")
        Long totalElements;
        @Schema(description = "첫 페이지면 true")
        Boolean isFirst;
        @Schema(description = " 마지막 페이지면 true")
        Boolean isLast;
    }
}
