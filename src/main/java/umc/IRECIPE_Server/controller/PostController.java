package umc.IRECIPE_Server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.IRECIPE_Server.apiPayLoad.ApiResponse;
import umc.IRECIPE_Server.apiPayLoad.code.status.ErrorStatus;
import umc.IRECIPE_Server.apiPayLoad.exception.GeneralException;
import umc.IRECIPE_Server.common.S3.S3Service;
import umc.IRECIPE_Server.common.enums.Category;
import umc.IRECIPE_Server.converter.PostConverter;
import umc.IRECIPE_Server.dto.request.PostRequestDTO;
import umc.IRECIPE_Server.dto.response.PostResponseDTO;
import umc.IRECIPE_Server.entity.Post;
import umc.IRECIPE_Server.service.PostService;

import java.io.IOException;

@Tag(name = "커뮤니티", description = "커뮤니티 관련 API")
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final S3Service s3Service;

    //게시글 등록하는 컨트롤러
    @Operation(
            summary = "새로운 게시글 등록 API",
            description = "게시글 내용(DTO, MultipartFile)을 이용하여 게시글을 신규 등록하는 기능"
    )
    @PostMapping(value = "/new-post",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ApiResponse<?> posting(@RequestPart("postRequestDTO") PostRequestDTO.newRequestDTO postRequestDto,
                                  @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {

        try {
            // 현재 토큰을 사용중인 유저 고유 id 조회
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = authentication.getName();

            String fileName = null;
            String url = null;
            if (file != null) {
                fileName = file.getOriginalFilename();
                url = s3Service.saveFile(file, "images");
            }

            return postService.posting(userId, postRequestDto, url, fileName);
        } catch (IOException e) {
            throw new GeneralException(ErrorStatus._INTERNAL_SERVER_ERROR);
        }

    }

    // 글쓰기 버튼 눌렀을 때 임시저장 있으면 불러오고, 없으면 그냥 새 글 쓰는 컨트롤러
    @Operation(
            summary = "임시저장글 불러오기 API",
            description = "글쓰기 버튼 눌렀을 때 임시저장 있으면 불러오고, 없으면 임시저장글 없다는 메시지반환"
    )
    @GetMapping(value = "/new-temp")
    public ApiResponse<?> newOrTemp() {
        // 현재 토큰을 사용중인 유저 고유 id 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        return postService.newOrTemp(userId);
    }

    // 게시글 단일 조회 컨트롤러
    @Operation(
            summary = "게시글 단일 조회 API",
            description = "게시글 Id(PathVariable) 를 이용하여 해당 게시글 조회"
    )
    @GetMapping("/{postId}")
    public ApiResponse<?> getPost(@PathVariable("postId") Long postId) {

        // 현재 토큰을 사용중인 유저 고유 id 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        return postService.getPost(postId, userId);
    }

    // 게시글 수정 컨트롤러
    @Operation(
            summary = "게시글 수정 API",
            description = "게시글 Id(PathVariable) 와 게시글 전체(DTO, MultipartFile)를 다시 한번 받아서 게시글 수정"
    )
    @PatchMapping(value = "/{postId}",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ApiResponse<?> updatePost(@PathVariable("postId") Long postId,
                                     @RequestPart("postRequestDTO") PostRequestDTO.patchRequestDTO postRequestDTO,
                                     @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {
        String newUrl = null;
        if (file != null) {
            newUrl = s3Service.saveFile(file, "images");
        }

        String oldUrl = postRequestDTO.getOldUrl();
        if (oldUrl != null) {
            s3Service.deleteImage(oldUrl, "images");
        }

        return postService.updatePost(postId, postRequestDTO, newUrl);
    }

    // 게시글 삭제 컨트롤러
    @Operation(
            summary = "게시글 삭제 API",
            description = "게시글 Id(PathVariable) 받아서 게시글 삭제"
    )
    @DeleteMapping("/{postId}")
    public ApiResponse<?> deletePost(@PathVariable("postId") Long postId) {

        Post post = postService.findByPostId(postId);
        s3Service.deleteImage(post.getFileName(), "images");

        return postService.deletePost(post);
    }

    // 커뮤니티 화면 조회
    @Operation(
            summary = "커뮤니티 처음 화면 조회 API",
            description = "페이지 번호와 정렬 기준(likes, createdAt, score) 받아서 모든 게시글의 정보 반환"
    )
    @GetMapping("/paging")
    public ApiResponse<?> getPostsPage(@RequestParam(required = false, value = "page") int page,
                                       @RequestParam(required = false, value = "criteria") String criteria
    )
    {
        return postService.getPostsPage(page, criteria);
    }

    @Operation(
            summary = "게시글 관심 추가 API",
            description = "관심 눌렀을 때 게시글의 관심 + 1, 사용자 관심에 추가"
    )
    @PostMapping("/like/{postId}")
    public ApiResponse<?> pushLike(@PathVariable Long postId){

        // 현재 토큰을 사용중인 유저 고유 id 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        return postService.pushLike(userId, postId);

    }

    @Operation(
            summary = "게시글 관심 삭제 API",
            description = "관심 한번 더 누르면 관심 - 1, 관심에서 제거"
    )
    @DeleteMapping("/like/{postId}")
    public ApiResponse<?> deleteLike(@PathVariable Long postId){

        // 현재 토큰을 사용중인 유저 고유 id 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        return postService.deleteLike(userId, postId);
    }

    @Operation(
            summary = "게시글 검색 API",
            description = "페이지 번호, 검색어, 검색 기준(title, content, writer) 받아서 검색된 게시글 정보 반환"
    )
    @GetMapping("/search")
    public ApiResponse<?> searchPost(@RequestParam(required = false, value = "page") int page,
                                     @RequestParam(required = false, value = "keyword") String keyword,
                                     @RequestParam(required = false, value = "type") String type
    )
    {
        return postService.searchPost(keyword, type, page);
    }

    @Operation(
            summary = "이달의 랭킹 전체 조회 API",
            description = "페이지 번호 받아서 이달의 랭킹 레시피들 조회"
    )
    @GetMapping("/ranking")
    public ApiResponse<PostResponseDTO.findAllResultListDTO> getRankedPost(@RequestParam(name = "page") Integer page) {
        Page<Post> ranking = postService.getRanking(page);
        return ApiResponse.onSuccess(PostConverter.toFindAllResultListDTO(ranking));
    }

    @Operation(
            summary = "카테고리별 이달의 랭킹 조회 API",
            description = "페이지 번호와 카테고리(PathVariable) 받아서 카테고리별 랭킹 조회"
    )
    @GetMapping("/ranking/{category}")
    public ApiResponse<PostResponseDTO.findAllResultListDTO> getCategoryRankedPost(@RequestParam(name = "page") Integer page,
                                                                                   @PathVariable(name = "category") Category category) {
        Page<Post> ranking = postService.getCategoryRanking(page, category);
        return ApiResponse.onSuccess(PostConverter.toFindAllResultListDTO(ranking));
    }
}
