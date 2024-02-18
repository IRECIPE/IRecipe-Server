package umc.IRECIPE_Server.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.resource.jdbc.spi.JdbcSessionContext;
import umc.IRECIPE_Server.entity.Qna;

import java.net.DatagramSocket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class QnaResponseDTO {

    // Qna 작성
    @Schema(description = "Qna 신규 등록 응답 DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class addQnaDTO {
        @Schema(description = "Qna Id")
        private Long qnaId;
    }

    // Qna 조회
    @Schema(description = "Qna 조회 응답 DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getQnaDTO {

        @Schema(description = "Qna Id")
        private Long qnaId;

        @Schema(description = "유저 Personal Id")
        private String memberId;

        @Schema(description = "유저 닉네임")
        private String memberNickName;

        @Schema(description = "유저 프로필 사진")
        private String memberImage;

        @Schema(description = "Qna 생성일자")
        private LocalDateTime createdAt;

        @Schema(description = "Qna 내용")
        private String content;

        @Schema(description = "Qna 사진")
        private String imageUrl;

        @Schema(description = "자식 댓글 리스트")
        private List<QnaResponseDTO.getQnaChildrenDTO> children;
    }

    @Schema(description = "Qna 조회 답변 응답 DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getQnaChildrenDTO {
        @Schema(description = "Qna Id")
        private Long qnaId;

        @Schema(description = "유저 Personal Id")
        private String memberId;

        @Schema(description = "유저 닉네임")
        private String memberNickName;

        @Schema(description = "유저 프로필 사진")
        private String memberImage;

        @Schema(description = "Qna 생성일자")
        private LocalDateTime createdAt;

        @Schema(description = "Qna 내용")
        private String content;

        @Schema(description = "Qna 사진")
        private String imageUrl;
    }

    // Qna 수정
    @Schema(description = "Qna 수정 응답 DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class updateQnaDTO {

        @Schema(description = "Qna Id")
        private Long qnaId;

        @Schema(description = "응답 메시지")
        private String message;
    }

    // Qna 삭제
    @Schema(description = "Qna 삭제 응답 DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class deleteQnaDTO {
        @Schema(description = "응답 메시지")
        private String message;
    }
}
