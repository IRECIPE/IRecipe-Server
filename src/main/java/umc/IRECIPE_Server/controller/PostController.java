package umc.IRECIPE_Server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.IRECIPE_Server.apiPayLoad.ApiResponse;
import umc.IRECIPE_Server.common.S3.S3Service;
import umc.IRECIPE_Server.dto.request.PostRequestDTO;
import umc.IRECIPE_Server.service.PostService;

import java.io.IOException;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final S3Service s3Service;

    //게시글 등록하는 컨트롤러
    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ApiResponse<?> posting(@RequestBody PostRequestDTO postRequestDto, @RequestPart MultipartFile file) throws IOException {

        // 현재 토큰을 사용중인 유저 고유 id 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        // s3 에 이미지 저장 및 경로 가져오기.
        String url = s3Service.saveFile(file, "images");

        return postService.posting(userId, postRequestDto, url);
    }

    @GetMapping("/{postId}")
    public ApiResponse<?> getPost(@PathVariable Long postId){

        return postService.getPost(postId);
    }
}
