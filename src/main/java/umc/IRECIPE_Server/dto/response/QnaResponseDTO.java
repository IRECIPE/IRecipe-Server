package umc.IRECIPE_Server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class QnaResponseDTO {

    // 작성
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class addQnaDTO {
        private Long qnaId;
    }
}
