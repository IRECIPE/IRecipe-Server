package umc.IRECIPE_Server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.IRECIPE_Server.entity.Qna;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class QnaResponseDTO {

    // Qna 작성
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class addQnaDTO {
        private Long qnaId;
    }

    // Qna 조회
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getQnaDTO {
        private Long qnaId;
        private Long memberId;
        private String memberNickName;
        private String memberImage;
        private LocalDateTime createdAt;
        private String content;
        private String imageUrl;
        private List<QnaResponseDTO.getQnaDTO> children = new ArrayList<>();

        public getQnaDTO(Long qnaId, Long memberId, String memberNickName, String memberImage,
                         LocalDateTime createdAt, String content, String imageUrl) {
            this.qnaId = qnaId;
            this.memberId = memberId;
            this.memberNickName = memberNickName;
            this.memberImage = memberImage;
            this.createdAt = createdAt;
            this.content = content;
            this.imageUrl = imageUrl;
        }
    }



}
