package umc.IRECIPE_Server.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import umc.IRECIPE_Server.apiPayLoad.code.status.ErrorStatus;
import umc.IRECIPE_Server.apiPayLoad.exception.handler.AllergyHandler;
import umc.IRECIPE_Server.apiPayLoad.exception.handler.MemberHandler;
import umc.IRECIPE_Server.apiPayLoad.exception.handler.PostHandler;
import umc.IRECIPE_Server.common.S3.S3Service;
import umc.IRECIPE_Server.common.enums.Status;
import umc.IRECIPE_Server.converter.MemberAllergyConverter;
import umc.IRECIPE_Server.converter.MemberConverter;
import umc.IRECIPE_Server.dto.MemberRequest;
import umc.IRECIPE_Server.dto.MemberResponse;
import umc.IRECIPE_Server.dto.MemberLoginRequestDto;
import umc.IRECIPE_Server.entity.Allergy;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.MemberAllergy;
import umc.IRECIPE_Server.entity.MemberLikes;
import umc.IRECIPE_Server.entity.Post;
import umc.IRECIPE_Server.entity.RefreshToken;
import umc.IRECIPE_Server.entity.Review;
import umc.IRECIPE_Server.jwt.JwtProvider;
import umc.IRECIPE_Server.repository.AllergyRepository;
import umc.IRECIPE_Server.repository.MemberAllergyRepository;
import umc.IRECIPE_Server.repository.MemberLikesRepository;
import umc.IRECIPE_Server.repository.MemberRepository;
import umc.IRECIPE_Server.repository.PostRepository;
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
    private final PostRepository postRepository;
    private final MemberLikesRepository memberLikesRepository;
    private final JwtProvider jwtProvider;
    private final S3Service s3Service;

    public Boolean findMemberByNickname(String Nickname){
        return memberRepository.existsByNickname(Nickname);
    }

    public Optional<Member> findMember(Long id) {
        return memberRepository.findById(id);
    }

    public Member findMember(String personalId) {
        return memberRepository.findByPersonalId(personalId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
    }

    public void checkNickname(String nickname) {
        Member member = memberRepository.findByNickname(nickname);
        if (member != null)
            throw new MemberHandler(ErrorStatus.NICKNAME_ALREADY_EXIST);
    }

    public MemberAllergy findMemberAllergy(Long memberId, Long allergyId){
        return memberAllergyRepository
                .findByAllergy_IdAndMember_Id(memberId, allergyId);
    }

    @Transactional
    public Member joinMember(MemberRequest.JoinDto request, String url){
        Member newMem = MemberConverter.toMember(request, url);
        List<Allergy> allergyList = request.getAllergyList().stream()
                .map(allergy -> {
                    return allergyRepository.findById(allergy)
                            .orElseThrow(() -> new AllergyHandler(ErrorStatus.ALLERGY_NOT_FOUND));
                }).collect(Collectors.toList());
        List<MemberAllergy> memberAllergyList = MemberAllergyConverter.toMemberAllergyList(allergyList);
        memberAllergyList.forEach(memberAllergy -> {memberAllergy.setMember(newMem);});

        return memberRepository.save(newMem);
    }

    @Transactional
    public Member signup(MemberRequest.JoinDto request, String url) {
        Member member = memberRepository.findByPersonalId(request.getPersonalId())
                .orElseGet(() -> this.joinMember(request, url));

        log.info("[login] 계정을 찾았습니다. " + member);

        //토큰 발급
        MemberResponse.JoinResultDto tokenDto = jwtProvider.generateTokenDto(request.getPersonalId());

        RefreshToken refreshToken = RefreshToken.builder()
                .member(member)
                .token(tokenDto.getRefreshToken())
                .build();
        tokenRepository.save(refreshToken);

        return member;
    }

    @Transactional
    public Member updateProfileById(MultipartFile file, String personalId) throws IOException {
        Member member = memberRepository.findByPersonalId(personalId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        String url = s3Service.saveFile(file, "/members/profiles");
        member.updateUrl(url);
        return member;

    }

    @Transactional
    public Member updateMemberById(MemberRequest.fixMemberInfoDto request, String personalId) {
        Member member = memberRepository.findByPersonalId(personalId)
                        .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        List<MemberAllergy> memberAllergyList = member.getMemberAllergyList();
        List<Long> allergyIds = memberAllergyList.stream()
                .map(MemberAllergy::getId)
                .toList(); // 저장되어 있던 알러지 리스트

        //새로 입력된 알러지 리스트
        List<Long> allergyList = request.getAllergyList();

        //원래 있던건 삭제
        for(Long ids : allergyIds){
            deleteMemberAllergy(member.getId(), ids);
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

    @Transactional
    public Member login(MemberLoginRequestDto.JoinLoginDto request){
        Member member = memberRepository.findByPersonalId(request.getPersonalId())
                        .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        log.info("[login] 로그인 했습니다. " + member);

        MemberResponse.JoinResultDto tokenDto = jwtProvider.generateTokenDto(request.getPersonalId());

        RefreshToken refreshToken = RefreshToken.builder()
                .member(member)
                .token(tokenDto.getRefreshToken())
                .build();
        tokenRepository.save(refreshToken);
        return member;
    }

    @Transactional
    public List<Post> viewStoredPost(String personalId){
        //누가 쓴거 볼건데
        Member member = memberRepository.findByPersonalId(personalId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        List<Post> postList = postRepository.findAllByMember(member);
        List<Post> posts = new ArrayList<>();
        for(Post post: postList){
            if(post.getMember().equals(member)){
                posts.add(post);
            }
        }

        //사용자가 작성한 글이 없는 경우
        if(posts.isEmpty())
            throw new PostHandler(ErrorStatus.POST_NOT_FOUND);

        return posts;

    }

    public Page<Post> getWrittenPostList(String personalId, Integer page) {
        Page<Post> postPage;
        Pageable pageable = PageRequest.of(page, 10);

        Optional<Member> member = memberRepository.findByPersonalId(personalId);
        if(member.isEmpty()){
            throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND);
        }
        Member mem = member.get();
        log.info(mem.getName());

        postPage = postRepository.findByMemberAndStatus(pageable, mem, Status.POST);
        if(postPage.isEmpty()) {
            if(page > 0) throw new PostHandler(ErrorStatus.NO_MORE_PAGE);
            else if(page == 0) throw new PostHandler(ErrorStatus.MEMBER_DONT_HAVE_POSTS);
        }

        return postPage;
    }

    public List<Post> getLikedPostList(String personalId, Integer page) {
        Page<MemberLikes> postIdPage;
        Pageable pageable = PageRequest.of(page, 10);

        Optional<Member> member = memberRepository.findByPersonalId(personalId);
        if(member.isEmpty()){
            throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND);
        }
        Member mem = member.get();
        log.info(mem.getName());

        postIdPage = memberLikesRepository.findByMember(mem, pageable);
        if(postIdPage.isEmpty()){
            if(page > 0) throw new PostHandler(ErrorStatus.NO_MORE_PAGE);
            if(page == 0) throw new PostHandler(ErrorStatus.MEMBER_DONT_HAVE_POSTS);
        }

        List<Post> postList = new ArrayList<>();
        for(MemberLikes memberLikes : postIdPage.toList()){
            Long id = memberLikes.getId();
            Post tmp = postRepository.findByStatusAndId(Status.POST, id);
            postList.add(tmp);
        }

        return postList;
    }

}