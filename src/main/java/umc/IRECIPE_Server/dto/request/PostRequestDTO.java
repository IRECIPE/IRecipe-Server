package umc.IRECIPE_Server.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import umc.IRECIPE_Server.common.enums.Category;
import umc.IRECIPE_Server.common.enums.Level;
import umc.IRECIPE_Server.common.enums.Status;

import java.util.List;

@Getter
@Builder
public class PostRequestDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class newRequestDTO{
        private String title;

        private String subhead;

        private Category category;

        private Level level;

        private Status status;

        private String content;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class patchRequestDTO{
        private String title;

        private String subhead;

        private Category category;

        private Level level;

        private Status status;

        private String content;

        // 삭제 할 사진 url
        private String oldUrl;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class searchDTO{
        // 검색어 키워드
        private String keyword;
        // 검색 타입(제목, 작성자, 내용)
        private String type;
    }
}
