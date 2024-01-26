package umc.IRECIPE_Server.dto.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import umc.IRECIPE_Server.common.enums.Category;
import umc.IRECIPE_Server.common.enums.Level;
import umc.IRECIPE_Server.common.enums.Status;

@Getter
@Builder
public class PostResponseDTO {

    // 게시글 id
    private Long postId;

    // 게시글 제목
    private String title;

    // 게시글 소제목
    private String subhead;

    // 게시글 내용
    private String content;

    // 게시글 좋아요 수
    private Long likes;

    // 카테고리
    private Category category;

    // 난이도
    private Level level;

    // 임시저장, 등록
    private Status status;

    // 평균 별점
    private Float score;

    // 게시글 사진 (일단 단일 사진만 구현)
    private String url;
}
