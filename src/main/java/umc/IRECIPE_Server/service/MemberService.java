package umc.IRECIPE_Server.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.IRECIPE_Server.apiPayLoad.code.status.ErrorStatus;
import umc.IRECIPE_Server.converter.MemberAllergyConverter;
import umc.IRECIPE_Server.converter.MemberConverter;
import umc.IRECIPE_Server.dto.MemberSignupRequestDto;
import umc.IRECIPE_Server.entity.Allergy;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.MemberAllergy;
import umc.IRECIPE_Server.jwt.JwtTokenProvider;
import umc.IRECIPE_Server.repository.AllergyRepository;
import umc.IRECIPE_Server.repository.MemberRepository;
import umc.IRECIPE_Server.dto.TokenDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final AllergyRepository allergyRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenDto login(String id, String password){
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDTO = jwtTokenProvider.generateToken(authentication);

        return tokenDTO;

    }

    @Transactional
    public Member joinMember(MemberSignupRequestDto.JoinDto request){
        Member newMem = MemberConverter.toMember(request);
        List<Allergy> allergyList = request.getAllergyList().stream()
                .map(allergy -> {
                    return allergyRepository.findById(allergy).orElseThrow(() -> new AllergyHandler(ErrorStatus.FOOD_CATEGORY_NOT_FOUND)); // 바꿔야돼
                }).collect(Collectors.toList());

        List<MemberAllergy> memberAllergyList = MemberAllergyConverter.toMemberAllergyList(allergyList);

        allergyList.forEach(memberAllergy -> {memberAllergy.setMember(newMember);});

        return memberRepository.save(newMem);
    }
}