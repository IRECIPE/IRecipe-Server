package umc.IRECIPE_Server.controller;

import io.jsonwebtoken.io.IOException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.IRECIPE_Server.apiPayLoad.ApiResponse;
import umc.IRECIPE_Server.apiPayLoad.code.status.ErrorStatus;
import umc.IRECIPE_Server.apiPayLoad.exception.handler.PostHandler;
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

    @Operation(summary = "회원가입 API",description = "최초 회원가입")
    @PostMapping(value = "/signup", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse<MemberResponse.JoinResultDto> join(
            @RequestPart(value = "request") @Valid MemberRequest.JoinDto request,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException, java.io.IOException {

        String url = null;
        if(file != null){
            url = s3Service.saveFile(file, "/member/profile/${request.getPersonalId()}");
        }

        Member response = memberService.signup(request, url);
        log.info(request.getName(), file);

        return ApiResponse.onSuccess(MemberConverter.toJoinResult(response, jwtProvider));
    }


    @Operation(summary = "프로필 이미지 변경 API",description = "사용자 프로필 이미지 변경")
    @PatchMapping(value = "/fix/profile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse<MemberResponse.updateMemberResultDto> fixMemberProfile(
            @RequestPart(value = "file") MultipartFile file
    ) throws java.io.IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        Member member = memberService.updateProfileById(file, userId);
        return ApiResponse.onSuccess(MemberConverter.updateMemberResult(member));
    }

    @Operation(summary = "마이페이지 API",description = "사용자 정보 받아오기")
    @GetMapping(value = "/")
    public ApiResponse<MemberResponse.getMemberResultDto> findOne() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        Member member = memberService.findMember(userId);
        return ApiResponse.onSuccess(MemberConverter.getMemberResult(member));
    }

    @Operation(summary = "유저 정보 변경 API",description = "사용자 정보 변경")
    @PatchMapping(value = "/fix/info")
    public ApiResponse<MemberResponse.updateMemberResultDto> fixMember(
            @RequestBody MemberRequest.fixMemberInfoDto request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        Member member = memberService.updateMemberById(request, userId);
        return ApiResponse.onSuccess(MemberConverter.updateMemberResult(member));
    }

    @Operation(summary = "닉네임 중복체크 API",description = "닉네임 중복체크")
    @GetMapping(value = "/nickname")
    public ApiResponse<MemberResponse.updateNicknameResultDto> checkNickName(
            @RequestParam("nickname") String nickname
    ){
        memberService.checkNickname(nickname);
        return ApiResponse.onSuccess(MemberConverter.updateNicknameResult());
    }

    @Operation(summary = "로그인 API",description = "로그인")
    @PostMapping(value = "/login")
    public ApiResponse<MemberResponse.JoinResultDto> joinLogin(
            @RequestBody @Valid MemberLoginRequestDto.JoinLoginDto request){
        Member response = memberService.login(request);
        return ApiResponse.onSuccess(MemberConverter.toJoinResult(response, jwtProvider));
    }

    @Operation(summary = "작성 글 API",description = "사용자가 작성한 글 보기")
    @GetMapping(value = "/written")
    public ApiResponse<List<MemberResponse.getPostsDto>> showWrittenPosts(
            @RequestParam(name = "page") Integer page
    ){
        if(page < 0) throw new PostHandler(ErrorStatus.INVALID_PAGE);
        //사용자 id 찾기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();//personal id

        return ApiResponse.onSuccess(MemberConverter.postsListDto(memberService.getWrittenPostList(userId, page)));
    }

    @Operation(summary = "관심 글 API",description = "사용자가 좋아요 누른 글 보기")
    @GetMapping(value = "/liked")
    public ApiResponse<?> showLikedPosts(
            @RequestParam(name = "page") Integer page
    ){
        if(page < 0) throw new PostHandler(ErrorStatus.INVALID_PAGE);
        //사용자 id 찾기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();//personal id

        return ApiResponse.onSuccess(MemberConverter.postsLikedListDto(memberService.getLikedPostList(userId, page)));
    }

    @Operation(summary = "토큰 재발급 API", description = "토큰 재발급")
    @PostMapping(value = "/refresh")
    public ApiResponse<?> refresh(@RequestParam(name = "user information") String id){
        Member member = memberService.findMember(id);
        Member response = memberService.refresh(member);

        return ApiResponse.onSuccess(MemberConverter.toJoinResult(response, jwtProvider));
    }
}