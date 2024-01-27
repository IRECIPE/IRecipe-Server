package umc.IRECIPE_Server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.IRECIPE_Server.apiPayLoad.ApiResponse;
import umc.IRECIPE_Server.apiPayLoad.code.status.ErrorStatus;
import umc.IRECIPE_Server.apiPayLoad.exception.GeneralException;
import umc.IRECIPE_Server.common.S3.S3Service;
import umc.IRECIPE_Server.dto.PostRequestDTO;
import umc.IRECIPE_Server.service.PostService;

import java.io.IOException;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final S3Service s3Service;

    //게시글 등록하는 컨트롤러
    @PostMapping(value = "",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ApiResponse<?> posting(@RequestPart("postRequestDTO") PostRequestDTO postRequestDto, @RequestPart("file") MultipartFile file) throws IOException {

        try{
            // 현재 토큰을 사용중인 유저 고유 id 조회
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = authentication.getName();

            // s3 에 이미지 저장 및 경로 가져오기.
            String url = s3Service.saveFile(file, "images");

            return postService.posting(userId, postRequestDto, url);
        }catch(IOException e){
            throw new GeneralException(ErrorStatus._INTERNAL_SERVER_ERROR);
        }

    }

    // 게시글 단일 조회 컨트롤러
    @GetMapping("/{postId}")
    public ApiResponse<?> getPost(@PathVariable Long postId){

        // 현재 토큰을 사용중인 유저 고유 id 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        return postService.getPost(postId, userId);
    }

    // 게시글 수정 컨트롤러
    @PatchMapping(value = "/{postId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ApiResponse<?> updatePost(@PathVariable Long postId, @RequestPart("postRequestDTO") PostRequestDTO postRequestDTO, @RequestPart("file") MultipartFile file) throws IOException {

        String url;

        if(file != null){
            // s3 에 이미지 저장 및 경로 가져오기.
            url = s3Service.saveFile(file, "images");
        }
        else
            url = null;


        return postService.updatePost(postId, postRequestDTO, url);
    }

    // 게시글 삭제 컨트롤러
    @DeleteMapping("/{postId}")
    public ApiResponse<?> deletePost(@PathVariable Long postId){

        return postService.deletePost(postId);
    }
}
