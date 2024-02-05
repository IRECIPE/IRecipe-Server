package umc.IRECIPE_Server.dto.request;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ReviewRequestDTO {

    @Getter
    public static class addReviewDTO {
        private String context;
        private Float score;
    }
}
