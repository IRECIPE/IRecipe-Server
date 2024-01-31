package umc.IRECIPE_Server.service;


import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import umc.IRECIPE_Server.apiPayLoad.code.status.ErrorStatus;
import umc.IRECIPE_Server.apiPayLoad.exception.handler.AllergyHandler;
import umc.IRECIPE_Server.apiPayLoad.exception.handler.MemberHandler;
import umc.IRECIPE_Server.common.S3.S3Service;
import umc.IRECIPE_Server.converter.MemberAllergyConverter;
import umc.IRECIPE_Server.converter.MemberConverter;
import umc.IRECIPE_Server.dto.MemberRequest;
import umc.IRECIPE_Server.dto.MemberSignupRequestDto;
import umc.IRECIPE_Server.dto.MemberSignupResponseDto;
import umc.IRECIPE_Server.entity.Allergy;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.MemberAllergy;
import umc.IRECIPE_Server.entity.RefreshToken;
import umc.IRECIPE_Server.jwt.JwtProvider;
import umc.IRECIPE_Server.repository.AllergyRepository;
import umc.IRECIPE_Server.repository.MemberAllergyRepository;
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
    private final MemberAllergyRepository memberAllergyRepository;
    private final TokenRepository tokenRepository;
    private final JwtProvider jwtProvider;
    private final S3Service s3Service;

    public Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
    }

    public Member checkNickname(String nickname) {
        Member member = memberRepository.findByNickname(nickname);
        if (member == null){
            return member;
        }
        else
            throw new MemberHandler(ErrorStatus.NICKNAME_ALREADY_EXIST);
    }

    public MemberAllergy findMemberAllergy(Long memberId, Long allergyId){
        return memberAllergyRepository
                .findByAllergy_IdAndMember_Id(memberId, allergyId);


    }

    @Transactional
    public Member joinMember(MemberSignupRequestDto.JoinDto request){
        Member newMem = MemberConverter.toMember(request);
        List<Allergy> allergyList = request.getAllergyList().stream()
                .map(allergy -> {
                    return allergyRepository.findById(allergy).orElseThrow(() -> new AllergyHandler(ErrorStatus.ALLERGY_NOT_FOUND));
                }).collect(Collectors.toList());
        List<MemberAllergy> memberAllergyList = MemberAllergyConverter.toMemberAllergyList(allergyList);
        memberAllergyList.forEach(memberAllergy -> {memberAllergy.setMember(newMem);});

        return memberRepository.save(newMem);
    }

    @Transactional
    public Member login(MemberSignupRequestDto.JoinDto request) {
        Member member = memberRepository.findByPersonalId(request.getPersonalId());
        if (member == null) { // 최초 회원가입
            member = this.joinMember(request);
        }

        log.info("[login] 계정을 찾았습니다. " + member);

        MemberSignupResponseDto.JoinResultDTO tokenDto = jwtProvider.generateTokenDto(request.getPersonalId());

        RefreshToken refreshToken = RefreshToken.builder()
                .id(member.getId())
                .token(tokenDto.getRefreshToken())
                .build();
        tokenRepository.save(refreshToken);

        return member;
    }

    @Transactional
    public Member updateProfileById(MultipartFile file, Long memberId) throws IOException {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        String url = s3Service.saveFile(file, "/members/profiles/{memberId}");
        member.updateUrl(url);
        return member;

    }
    @Transactional
    public Member updateMemberById(MemberRequest.fixMemberInfoDto request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                        .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        List<MemberAllergy> memberAllergyList = member.getMemberAllergyList();
        List<Long> allergyIds = memberAllergyList.stream()
                .map(MemberAllergy::getId)
                .toList(); // 저장되어 있던 알러지 리스트

        //새로 입력된 알러지 리스트
        List<Long> allergyList = request.getAllergyList();

        //원래 있던건 삭제
        for(Long ids : allergyIds){
            deleteMemberAllergy(memberId, ids);
        }

        List<Allergy> allergies = allergyList.stream()
                        .map(allergy -> {
                            return allergyRepository.findById(allergy).orElseThrow(() -> new AllergyHandler(ErrorStatus.ALLERGY_NOT_FOUND));
                        }).toList();
        List<MemberAllergy> memberAllergies = MemberAllergyConverter.toMemberAllergyList(allergies);
        memberAllergies.forEach(memberAllergy -> {memberAllergy.setMember(member);});

        member.updateMember(request.getName(), request.getNickname(), request.getGender(), request.getAge(), request.getImportant(), request.getEvent(), request.getActivity(), memberAllergies);
        log.info("[fix] 멤버 정보를 수정했습니다.");
        return memberRepository.save(member);
    }

    @Transactional
    public void deleteMemberAllergy(Long memberId, Long allergyId) {
        MemberAllergy memberAllergy = findMemberAllergy(memberId, allergyId);
        memberAllergyRepository.delete(memberAllergy);
    }

}