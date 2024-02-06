package umc.IRECIPE_Server.controller;

import jakarta.validation.Valid;
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
import umc.IRECIPE_Server.converter.PostConverter;
import umc.IRECIPE_Server.dto.request.PostRequestDTO;
import umc.IRECIPE_Server.dto.response.PostResponseDTO;
import umc.IRECIPE_Server.entity.Post;
import umc.IRECIPE_Server.service.PostService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final S3Service s3Service;

    //게시글 등록하는 컨트롤러
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
    @GetMapping(value = "/new-temp")
    public ApiResponse<?> newOrTemp() {
        // 현재 토큰을 사용중인 유저 고유 id 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        return postService.newOrTemp(userId);
    }

    // 게시글 단일 조회 컨트롤러
    @GetMapping("/{postId}")
    public ApiResponse<?> getPost(@PathVariable("postId") Long postId) {

        // 현재 토큰을 사용중인 유저 고유 id 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        return postService.getPost(postId, userId);
    }

    // 게시글 수정 컨트롤러
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
    @DeleteMapping("/{postId}")
    public ApiResponse<?> deletePost(@PathVariable("postId") Long postId) {

        Post post = postService.findByPostId(postId);
        s3Service.deleteImage(post.getFileName(), "images");

        return postService.deletePost(post);
    }

    // 커뮤니티 화면 조회
    @GetMapping("/paging")
    public ApiResponse<?> postPaging() {
        return null;
    }

    @GetMapping("/ranking")
    public ApiResponse<PostResponseDTO.findAllResultListDTO> getRankedPost(@RequestParam(name = "page") Integer page) {
        Page<Post> ranking = postService.getRanking(page);
        return ApiResponse.onSuccess(PostConverter.toFindAllResultListDTO(ranking));
    }
}
