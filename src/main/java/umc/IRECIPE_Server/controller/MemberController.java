package umc.IRECIPE_Server.controller;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.IRECIPE_Server.apiPayLoad.ApiResponse;
import umc.IRECIPE_Server.common.S3.S3Service;
import umc.IRECIPE_Server.converter.MemberConverter;
import umc.IRECIPE_Server.dto.MemberRequest;
import umc.IRECIPE_Server.dto.MemberResponse;
import umc.IRECIPE_Server.dto.MemberLoginRequestDto;
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
    public ApiResponse<MemberResponse.JoinResultDto> join(
            @RequestPart(value = "request") MemberRequest.JoinDto request,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException, java.io.IOException {
        String url = s3Service.saveFile(file, "/member/profile/${request.getPersonalId()}");
        Member response = memberService.signup(request, url);
        log.info(request.getName(), file);

        return ApiResponse.onSuccess(MemberConverter.toJoinResult(response, jwtProvider));
    }

    @PatchMapping(value = "/profile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse<MemberResponse.updateMemberResultDto> fixMemberProfile(
            @RequestPart(value = "file") MultipartFile file
    ) throws java.io.IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        Member member = memberService.updateProfileById(file, userId);
        return ApiResponse.onSuccess(MemberConverter.updateMemberResult(member));
    }

    @GetMapping(value = "/{member_id}")
    public ApiResponse<MemberResponse.getMemberResultDto> findOne(
            @PathVariable(name = "member_id") Long memberId) {
        Member member = memberService.findMember(memberId);
        return ApiResponse.onSuccess(MemberConverter.getMemberResult(member));
    }

    @PatchMapping(value = "/fix")
    public ApiResponse<MemberResponse.updateMemberResultDto> fixMember(
            @RequestBody MemberRequest.fixMemberInfoDto request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        Member member = memberService.updateMemberById(request, userId);
        return ApiResponse.onSuccess(MemberConverter.updateMemberResult(member));
    }

    @GetMapping(value = "/nickname")
    public ApiResponse<MemberResponse.updateNicknameResultDto> fixMember(
            @RequestParam("nickname") String nickname
    ){
        memberService.checkNickname(nickname);
        return ApiResponse.onSuccess(MemberConverter.updateNicknameResult());
    }

    @GetMapping(value = "/")
    public ApiResponse<MemberResponse.updateMemberResultDto> getMemberId(
            @RequestParam("personalId") String personalId
    ){
        Member member = memberService.findMemberId(personalId);
        return ApiResponse.onSuccess(MemberConverter.updateMemberResult(member));

    }

    @PostMapping(value = "/login")
    public ApiResponse<MemberResponse.JoinResultDto> joinLogin(
            @RequestBody @Valid MemberLoginRequestDto.JoinLoginDto request){
        Member response = memberService.login(request);
        return ApiResponse.onSuccess(MemberConverter.toJoinResult(response, jwtProvider));
    }
}