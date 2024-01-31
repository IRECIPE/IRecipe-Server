package umc.IRECIPE_Server.controller;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.IRECIPE_Server.apiPayLoad.ApiResponse;
import umc.IRECIPE_Server.converter.IngredientConverter;
import umc.IRECIPE_Server.converter.MemberConverter;
import umc.IRECIPE_Server.dto.MemberRequest;
import umc.IRECIPE_Server.dto.MemberResponse;
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

    @PostMapping("/test")
    public String test(){
        return "Success";
    }

    @PostMapping(value = "/signup", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse<MemberSignupResponseDto.JoinResultDTO> join(
            @RequestPart(value = "request") MemberSignupRequestDto.JoinDto request,
            @RequestPart(value = "file", required = false) MultipartFile file
    )throws IOException {
        Member response = memberService.login(request);

        log.info(request.getName(), file);

        return ApiResponse.onSuccess(MemberConverter.toJoinResult(response, jwtProvider));
    }

    @PatchMapping(value = "/{member_id}/profile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse<MemberResponse.updateMemberResultDto> fixMemberProfile(
            @PathVariable(name = "member_id") Long memberId,
            @RequestPart(value = "file") MultipartFile file
    ) throws java.io.IOException {
        Member member = memberService.updateProfileById(file, memberId);
        return ApiResponse.onSuccess(MemberConverter.updateMemberResult(member));
    }

    @GetMapping(value = "/{member_id}")
    public ApiResponse<MemberResponse.getMemberResultDto> findOne(
            @PathVariable(name = "member_id") Long memberId) {
        Member member = memberService.findMember(memberId);
        return ApiResponse.onSuccess(MemberConverter.getMemberResult(member));
    }

    @PatchMapping(value = "/{member_id}")
    public ApiResponse<MemberResponse.updateMemberResultDto> fixMember(
            @PathVariable(name = "member_id") Long memberId,
            @RequestBody MemberRequest.fixMemberInfoDto request){
        Member member = memberService.updateMemberById(request, memberId);
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
}