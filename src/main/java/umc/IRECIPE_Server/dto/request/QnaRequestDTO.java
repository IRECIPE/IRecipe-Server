package umc.IRECIPE_Server.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class QnaRequestDTO {

    @Schema(description = "Qna 신규 등록 요청 DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class addQna {
        @Schema(description = "Qna 내용", defaultValue = "설탕 대신 알룰로스를...")
        private String content;
        @Schema(description = "부모 댓글 Id", defaultValue = "0")
        private Long parentId;
    }

    @Schema(description = "Qna 수정 요청 DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class updateQna {
        @Schema(description = "Qna 내용", defaultValue = "설탕 대신 알룰로스를...")
        private String content;
    }
}
