package umc.IRECIPE_Server.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ReviewRequestDTO {

    @Schema(description = "리뷰 신규 등록 요청 DTO")
    @Getter
    public static class addReviewDTO {

        @Schema(description = "리뷰 내용", defaultValue = "노력대비 최고의 맛!...")
        private String context;

        @Schema(description = "리뷰 별점", defaultValue = "4.3")
        private int score;
    }
}
