package umc.IRECIPE_Server.service;


import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.IRECIPE_Server.apiPayLoad.code.status.ErrorStatus;
import umc.IRECIPE_Server.apiPayLoad.exception.handler.AllergyHandler;
import umc.IRECIPE_Server.converter.MemberAllergyConverter;
import umc.IRECIPE_Server.converter.MemberConverter;
import umc.IRECIPE_Server.dto.MemberLoginRequestDto;
import umc.IRECIPE_Server.dto.MemberSignupRequestDto;
import umc.IRECIPE_Server.dto.MemberSignupResponseDto;
import umc.IRECIPE_Server.entity.Allergy;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.MemberAllergy;
import umc.IRECIPE_Server.entity.RefreshToken;
import umc.IRECIPE_Server.jwt.JwtProvider;
import umc.IRECIPE_Server.repository.AllergyRepository;
import umc.IRECIPE_Server.repository.MemberRepository;
import umc.IRECIPE_Server.repository.TokenRepository;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final AllergyRepository allergyRepository;
    private final TokenRepository tokenRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public Member joinMember(MemberSignupRequestDto.JoinDto request, String url){
        Member newMem = MemberConverter.toMember(request, url);
        List<Allergy> allergyList = request.getAllergyList().stream()
                .map(allergy -> {
                    return allergyRepository.findById(allergy).orElseThrow(() -> new AllergyHandler(ErrorStatus.ALLERGY_NOT_FOUND));
                }).collect(Collectors.toList());
        List<MemberAllergy> memberAllergyList = MemberAllergyConverter.toMemberAllergyList(allergyList);
        memberAllergyList.forEach(memberAllergy -> {memberAllergy.setMember(newMem);});

        return memberRepository.save(newMem);
    }

    @Transactional
    public Member signup(MemberSignupRequestDto.JoinDto request, String url) {
        Member member = memberRepository.findByPersonalId(request.getPersonalId());
        if (member == null) { // 최초 회원가입
            member = this.joinMember(request, url);
            log.info("[signup] 회원가입을 완료했습니다. " + member);
        }

        //토큰 발급
        MemberSignupResponseDto.JoinResultDTO tokenDto = jwtProvider.generateTokenDto(request.getPersonalId());

        RefreshToken refreshToken = RefreshToken.builder()
                .id(member.getId())
                .token(tokenDto.getRefreshToken())
                .build();
        tokenRepository.save(refreshToken);

        return member;
    }

    @Transactional
    public Member login(MemberLoginRequestDto.JoinDto request){
        Member newMember = memberRepository.findByPersonalId(request.getPersonalId());

        log.info("[login] 로그인을 완료했습니다. " + newMember);

        //토큰 발급
        MemberSignupResponseDto.JoinResultDTO tokenDto = jwtProvider.generateTokenDto(request.getPersonalId());

        RefreshToken refreshToken = RefreshToken.builder()
                .id(newMember.getId())
                .token(tokenDto.getRefreshToken())
                .build();
        tokenRepository.save(refreshToken);

        return newMember;

    }


}