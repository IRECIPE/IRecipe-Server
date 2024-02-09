package umc.IRECIPE_Server.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import umc.IRECIPE_Server.common.enums.Category;
import umc.IRECIPE_Server.common.enums.Level;
import umc.IRECIPE_Server.common.enums.Status;

import javax.swing.*;
import java.util.List;


@Getter
@Builder
public class PostRequestDTO {

    @Schema(description = "게시글 신규 등록 요청 DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class newRequestDTO{
        @Schema(description = "게시글 제목", defaultValue = "간장게장")
        private String title;
        @Schema(description = "게시글 소제목", defaultValue = "간장게장은 한국의 전통 음식 중 하나로...")
        private String subhead;
        @Schema(description = "음식 카테고리", defaultValue = "WESTERN/JAPANESE/CHINESE/KOREAN/UNIQUE/SIMPLE/ADVANCED")
        private Category category;
        @Schema(description = "난이도", defaultValue = "EASY/MID/DIFFICULT")
        private Level level;
        @Schema(description = "게시글 상태(임시저장 여부)", defaultValue = "TEMP/POST")
        private Status status;
        @Schema(description = "게시글 내용", defaultValue = "재료 - 꽃게: 2마리 - 간장: 1컵 ...")
        private String content;

    }

    @Schema(description = "게시글 수정 요청 DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class patchRequestDTO{
        @Schema(description = "게시글 제목", defaultValue = "간장게장")
        private String title;
        @Schema(description = "게시글 소제목", defaultValue = "간장게장은 한국의 전통 음식 중 하나로...")
        private String subhead;
        @Schema(description = "음식 카테고리", defaultValue = "WESTERN/JAPANESE/CHINESE/KOREAN/UNIQUE/SIMPLE/ADVANCED")
        private Category category;
        @Schema(description = "난이도", defaultValue = "EASY/MID/DIFFICULT")
        private Level level;
        @Schema(description = "게시글 상태(임시저장 여부)", defaultValue = "TEMP/POST")
        private Status status;
        @Schema(description = "게시글 내용", defaultValue = "재료 - 꽃게: 2마리 - 간장: 1컵 ...")
        private String content;
        @Schema(description = "바뀌기전 사진의 url")
        private String oldUrl;
    }

    @Schema(description = "게시글 검색 요청 DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class searchDTO{
        @Schema(description = "검색어")
        private String keyword;
        @Schema(description = "검색 타입", defaultValue = "title/content/writer")
        private String type;
        @Schema(description = "페이지 번호", defaultValue = "0")
        private int page;
    }
}
