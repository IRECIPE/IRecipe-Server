package umc.IRECIPE_Server.controller;

import io.jsonwebtoken.io.IOException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.IRECIPE_Server.apiPayLoad.ApiResponse;
import umc.IRECIPE_Server.common.S3.S3Service;
import umc.IRECIPE_Server.converter.MemberConverter;
import umc.IRECIPE_Server.dto.MemberLoginRequestDto;
import umc.IRECIPE_Server.dto.MemberSignupRequestDto;
import umc.IRECIPE_Server.dto.MemberSignupResponseDto;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.jwt.JwtProvider;
import umc.IRECIPE_Server.service.MemberService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final JwtProvider jwtProvider;
    private final S3Service s3Service;

    @PostMapping("/test")
    public String test(){
        return "Success";
    }

    @PostMapping(value = "/signup", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse<MemberSignupResponseDto.JoinResultDTO> join(
            @RequestPart("request") MemberSignupRequestDto.JoinDto request,
            @RequestPart("file") MultipartFile file
    ) throws IOException, java.io.IOException {
        String url = s3Service.saveFile(file, "/member/profile/${request.getPersonalId()}");
        Member response = memberService.signup(request, url);

        log.info(request.getName(), file);

        return ApiResponse.onSuccess(MemberConverter.toJoinResultDTO(response, jwtProvider));
    }

    @PostMapping(value = "/login")
    public ApiResponse<MemberSignupResponseDto.JoinResultDTO> joinLogin(
            @RequestBody @Valid MemberLoginRequestDto.JoinLoginDto request){
        Member response = memberService.login(request);
        return ApiResponse.onSuccess(MemberConverter.toJoinResultDTO(response, jwtProvider));
    }



}