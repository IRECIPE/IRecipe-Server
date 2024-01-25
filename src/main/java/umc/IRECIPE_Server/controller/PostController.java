package umc.IRECIPE_Server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.IRECIPE_Server.apiPayLoad.ApiResponse;
import umc.IRECIPE_Server.dto.PostRequestDto;

@RestController
@RequestMapping("/post")
public class PostController {


    // 게시글 등록하는 컨트롤러
//    @PostMapping("")
//    public ApiResponse<?> posting(@RequestBody PostRequestDto postRequestDto){
//
//        // 현재 토큰을 사용중인 유저 고유 id 조회
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userId = authentication.getName();
//
//        return ApiResponse.onSuccess();
//    }
}
