package umc.IRECIPE_Server.controller;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import umc.IRECIPE_Server.entity.Post;
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

    @Description("유저 회원가입 api")
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


    @Description("유저 프로필 이미지 변경 api")
    @PatchMapping(value = "/fix/profile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse<MemberResponse.updateMemberResultDto> fixMemberProfile(
            @RequestPart(value = "file") MultipartFile file
    ) throws java.io.IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        Member member = memberService.updateProfileById(file, userId);
        return ApiResponse.onSuccess(MemberConverter.updateMemberResult(member));
    }

    @Description("유저 정보 받아오는 api")
    @GetMapping(value = "/")
    public ApiResponse<MemberResponse.getMemberResultDto> findOne() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        Member member = memberService.findMember(userId);
        return ApiResponse.onSuccess(MemberConverter.getMemberResult(member));
    }

    @Description("유저 정보 변경 api")
    @PatchMapping(value = "/fix/info")
    public ApiResponse<MemberResponse.updateMemberResultDto> fixMember(
            @RequestBody MemberRequest.fixMemberInfoDto request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        Member member = memberService.updateMemberById(request, userId);
        return ApiResponse.onSuccess(MemberConverter.updateMemberResult(member));
    }

    @Description("닉네임 중복체크 api")
    @GetMapping(value = "/nickname")
    public ApiResponse<MemberResponse.updateNicknameResultDto> checkNickName(
            @RequestParam("nickname") String nickname
    ){
        memberService.checkNickname(nickname);
        return ApiResponse.onSuccess(MemberConverter.updateNicknameResult());
    }

    @Description("로그인")
    @PostMapping(value = "/login")
    public ApiResponse<MemberResponse.JoinResultDto> joinLogin(
            @RequestBody @Valid MemberLoginRequestDto.JoinLoginDto request){
        Member response = memberService.login(request);
        return ApiResponse.onSuccess(MemberConverter.toJoinResult(response, jwtProvider));
    }

    @Description("사용자가 작성한 글 보기 api")
    @GetMapping(value = "/written")
    public ApiResponse<MemberResponse.getPostsListDto> showPosts(
            @RequestParam(name = "page") Integer page
    ){
        //사용자 id 찾기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();//personal id

        memberService.getPostList(userId, page);
        return null;

    }
}