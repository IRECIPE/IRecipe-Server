package umc.IRECIPE_Server.controller;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.IRECIPE_Server.apiPayLoad.ApiResponse;
import umc.IRECIPE_Server.converter.MemberConverter;
import umc.IRECIPE_Server.dto.MemberSignupRequestDto;
import umc.IRECIPE_Server.dto.MemberSignupResponseDto;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.jwt.JwtProvider;
import umc.IRECIPE_Server.service.MemberService;
<<<<<<< HEAD
=======
>>>>>>> 32eec6c691e713bf3022877fcf5225892be6f0e1

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    @PostMapping("/test")
    public String test(){
        return "Success";
    }

    @PostMapping(value = "/signup", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse<MemberSignupResponseDto.JoinResultDTO> join(
            @RequestPart("request") MemberSignupRequestDto.JoinDto request,
            @RequestPart("file") MultipartFile file
    )throws IOException {
        log.info(request.getName());

        Member response = memberService.login(request);

        log.info(request.getName(), file);

        return ApiResponse.onSuccess(MemberConverter.toJoinResultDTO(response, jwtProvider));
    }
}