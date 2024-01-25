package umc.IRECIPE_Server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.IRECIPE_Server.apiPayLoad.ApiResponse;
import umc.IRECIPE_Server.dto.MemberSignupRequestDto;
import umc.IRECIPE_Server.dto.MemberSignupResponseDto;
import umc.IRECIPE_Server.service.MemberService;
import umc.IRECIPE_Server.dto.MemberLoginRequestDto;
import umc.IRECIPE_Server.dto.TokenDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login")
    public TokenDto login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        String nickname = memberLoginRequestDto.getNickname();
        String password = memberLoginRequestDto.getPassword();
        TokenDto tokenDTO = memberService.login(nickname, password);
        return tokenDTO;
    }

    @PostMapping("/test")
    public String test(){
        return "Success";
    }

    public ApiResponse<MemberSignupResponseDto.JoinResultDTO> join(@RequestBody @Valid MemberSignupRequestDto.JoinDto request){
        return null;
    }
}