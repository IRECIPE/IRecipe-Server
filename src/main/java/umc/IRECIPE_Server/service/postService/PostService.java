package umc.IRECIPE_Server.service.postService;

import org.springframework.data.domain.Page;
import umc.IRECIPE_Server.apiPayLoad.ApiResponse;
import umc.IRECIPE_Server.common.enums.Category;
import umc.IRECIPE_Server.dto.request.PostRequestDTO;
import umc.IRECIPE_Server.entity.Post;

public interface PostService {

    // postId 로 Post 찾고 Null 이면 예외 출력하는 메소드
    Post findByPostId(Long postId);

    // PostRequestDto를 받아 DB에 게시글 저장 후 PostResponseDto 생성해서 반환하는 메소드.
    // 게시글 생성 (Create)
    ApiResponse<?> posting(String userId, PostRequestDTO.newRequestDTO postRequestDto, String url, String fileName);

    // 게시글 임시저장 불러오기
    ApiResponse<?> newOrTemp(String userId);

    // 게시글 단일 조회 (Read)
    ApiResponse<?> getPost(Long postId, String userId);

    // 게시글 수정 후 저장.
    ApiResponse<?> updatePost(Long postId, PostRequestDTO.patchRequestDTO postRequestDTO, String newUrl);

    // 게시글 삭제
    ApiResponse<?> deletePost(Post post);

    // 커뮤니티 화면 조회
    ApiResponse<?> getPostsPage(int page, String criteria, String userId);

    // 게시글에 관심 눌렀을 때
    ApiResponse<?> pushLike(String userId, Long postId);

    // 관심 한번 더 눌렀을 때
    ApiResponse<?> deleteLike(String userId, Long postId);

    // 게시글 검색
    ApiResponse<?> searchPost(String keyword, String type, int page, String userId);

    // 이달의 랭킹 조회
    Page<Post> getRanking(Integer page);

    // 카테고리별 이달의 랭킹 조회
    Page<Post> getCategoryRanking(Integer page, Category category);
}


