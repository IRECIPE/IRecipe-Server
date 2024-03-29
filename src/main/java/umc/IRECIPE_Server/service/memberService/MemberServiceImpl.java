package umc.IRECIPE_Server.service.memberService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import umc.IRECIPE_Server.apiPayLoad.ApiResponse;
import umc.IRECIPE_Server.apiPayLoad.code.status.ErrorStatus;
import umc.IRECIPE_Server.apiPayLoad.exception.handler.AllergyHandler;
import umc.IRECIPE_Server.apiPayLoad.exception.handler.MemberHandler;
import umc.IRECIPE_Server.apiPayLoad.exception.handler.PostHandler;
import umc.IRECIPE_Server.common.S3.S3Service;
import umc.IRECIPE_Server.common.enums.Status;
import umc.IRECIPE_Server.converter.MemberAllergyConverter;
import umc.IRECIPE_Server.converter.MemberConverter;
import umc.IRECIPE_Server.converter.PostConverter;
import umc.IRECIPE_Server.dto.request.MemberRequestDTO;
import umc.IRECIPE_Server.dto.response.MemberResponseDTO;
import umc.IRECIPE_Server.dto.request.MemberLoginRequestDTO;
import umc.IRECIPE_Server.entity.Allergy;
import umc.IRECIPE_Server.entity.DislikedFood;
import umc.IRECIPE_Server.entity.Ingredient;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.MemberAllergy;
import umc.IRECIPE_Server.entity.MemberLikes;
import umc.IRECIPE_Server.entity.Post;
import umc.IRECIPE_Server.entity.Qna;
import umc.IRECIPE_Server.entity.RefreshToken;
import umc.IRECIPE_Server.entity.Review;
import umc.IRECIPE_Server.entity.StoredRecipe;
import umc.IRECIPE_Server.jwt.JwtProvider;
import umc.IRECIPE_Server.repository.AllergyRepository;
import umc.IRECIPE_Server.repository.DislikedFoodRepository;
import umc.IRECIPE_Server.repository.IngredientRepository;
import umc.IRECIPE_Server.repository.MemberAllergyRepository;
import umc.IRECIPE_Server.repository.MemberLikesRepository;
import umc.IRECIPE_Server.repository.MemberRepository;
import umc.IRECIPE_Server.repository.PostRepository;
import umc.IRECIPE_Server.repository.QnaRepository;
import umc.IRECIPE_Server.repository.ReviewRepository;
import umc.IRECIPE_Server.repository.StoredRecipeRepository;
import umc.IRECIPE_Server.repository.TokenRepository;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final AllergyRepository allergyRepository;
    private final MemberAllergyRepository memberAllergyRepository;
    private final TokenRepository tokenRepository;
    private final PostRepository postRepository;
    private final IngredientRepository ingredientRepository;
    private final MemberLikesRepository memberLikesRepository;
    private final QnaRepository qnaRepository;
    private final ReviewRepository reviewRepository;
    private final StoredRecipeRepository storedRecipeRepository;
    private final DislikedFoodRepository dislikedFoodRepository;
    private final JwtProvider jwtProvider;
    private final S3Service s3Service;


    public Boolean findMemberByNickname(String Nickname){
        return memberRepository.existsByNickname(Nickname);
    }

    public Boolean findMemberByPersonalId(String id){
        return memberRepository.existsByPersonalId(id);
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

    @Transactional
    public Member joinMember(MemberRequestDTO.JoinDto request, String url){
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
    public Member signup(MemberRequestDTO.JoinDto request, String url) {
        Member member = memberRepository.findByPersonalId(request.getPersonalId())
                .orElseGet(() -> this.joinMember(request, url));

        log.info("[login] 계정을 찾았습니다. " + member);

        //토큰 발급
        MemberResponseDTO.JoinResultDto tokenDto = jwtProvider.generateTokenDto(request.getPersonalId());

        if(tokenRepository.existsByMember(member)){
            RefreshToken refreshToken = tokenRepository.findByMember(member);
            refreshToken.updateToken(tokenDto.getRefreshToken());
        }
        else {
            RefreshToken refreshToken = RefreshToken.builder()
                    .member(member)
                    .token(tokenDto.getRefreshToken())
                    .build();
            tokenRepository.save(refreshToken);
        }

        return member;
    }

    //프로필 이미지 수정
    @Transactional
    public Member updateProfileById(MultipartFile file, String personalId) throws IOException {
        Member member = memberRepository.findByPersonalId(personalId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        String url = s3Service.saveFile(file, "members/profiles");
        member.updateUrl(url);
        return member;
    }

    //회원 정보 수정
    @Transactional
    public Member updateMemberById(MemberRequestDTO.fixMemberInfoDto request, String personalId) {
        Member member = memberRepository.findByPersonalId(personalId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        // 저장되어 있던 알러지 리스트
        List<MemberAllergy> memberAllergyList = member.getMemberAllergyList();
        List<Long> allergyIds = memberAllergyList.stream()
                .map(MemberAllergy::getId)
                .toList();

        //원래 있던건 삭제
        for (Long ids : allergyIds) {
            memberAllergyRepository.deleteById(ids);
        }

        //새로 입력된 알러지 리스트
        List<Long> allergyList = request.getAllergyList();

        List<Allergy> allergies = allergyList.stream()
                .map(allergy -> {
                    return allergyRepository.findById(allergy)
                            .orElseThrow(() -> new AllergyHandler(ErrorStatus.ALLERGY_NOT_FOUND));
                }).toList();
        List<MemberAllergy> memberAllergies = MemberAllergyConverter.toMemberAllergyList(allergies);
        memberAllergies.forEach(memberAllergy -> {
            memberAllergy.setMember(member);
        });

        member.updateMember(request.getName(), request.getNickname(), request.getGender(), request.getAge(),
                request.getImportant(), request.getEvent(), request.getActivity(), memberAllergies);
        log.info("[fix] 멤버 정보를 수정했습니다.");
        return memberRepository.save(member);
    }


    //로그인
    @Transactional
    public Member login(MemberLoginRequestDTO.JoinLoginDto request){
        Member member = memberRepository.findByPersonalId(request.getPersonalId())
                        .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        log.info("[login] 로그인 했습니다. " + member);

        MemberResponseDTO.JoinResultDto tokenDto = jwtProvider.generateTokenDto(request.getPersonalId());

        if(tokenRepository.existsByMember(member)){
            RefreshToken refreshToken = tokenRepository.findByMember(member);
            refreshToken.updateToken(tokenDto.getRefreshToken());
        }
        else {
            RefreshToken refreshToken = RefreshToken.builder()
                    .member(member)
                    .token(tokenDto.getRefreshToken())
                    .build();
            tokenRepository.save(refreshToken);
        }
        return member;
    }


    //회원이 작성한 글 보기
    @Transactional
    public ApiResponse<?> getWrittenPostList(String personalId, Integer page) {
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

        // memberLike 에서 찾으면 관심 눌렀던 게시글, 못 찾으면 관심 안 누른 게시글
        Map<Long, Boolean> likeMap = new HashMap<>();
        for (Post post : postPage) {
            Boolean likeOrNot = memberLikesRepository.findByMemberAndPost(mem, post).isPresent();
            likeMap.put(post.getId(), likeOrNot);
        }

        return ApiResponse.onSuccess(PostConverter.toGetAllPostDTO(postPage, likeMap));
    }

    //회원 관심글 보기
    @Transactional
    public ApiResponse<?> getLikedPostList(String personalId, Integer page) {
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
            Long id = memberLikes.getPost().getId();
            Post tmp = postRepository.findByStatusAndId(Status.POST, id);
            postList.add(tmp);
        }

        if(postList.isEmpty()){
            throw new MemberHandler(ErrorStatus.POST_NOT_FOUND);
        }

        // memberLike 에서 찾으면 관심 눌렀던 게시글, 못 찾으면 관심 안 누른 게시글
        Map<Long, Boolean> likeMap = new HashMap<>();
        for (Post post : postList) {
            Boolean likeOrNot = memberLikesRepository.findByMemberAndPost(mem, post).isPresent();
            likeMap.put(post.getId(), likeOrNot);
        }

        return ApiResponse.onSuccess(PostConverter.toGetAllPostListDTO(mem, postList, likeMap));
    }

    //토큰 재발급
    @Transactional
    public Member refresh(Member member){
        if(tokenRepository.existsByMember(member)){ // 이미 refresh token이 있다면
            MemberResponseDTO.JoinResultDto token = jwtProvider.generateTokenDto(member.getPersonalId());
            RefreshToken refreshToken = tokenRepository.findByMember(member);

            refreshToken.updateToken(token.getRefreshToken());
        }
        return member;
    }

    //회원 탈퇴
    @Transactional
    public void deleteMember(String personalId){
        Optional<Member> member = memberRepository.findByPersonalId(personalId);
        if(member.isEmpty()){
            throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND);
        }
        Member member1 = member.get();
        RefreshToken refreshToken = tokenRepository.findByMember(member1);

        //재료 삭제
        List<Ingredient> ingredients = ingredientRepository.findNamesByMember_PersonalId(member1.getPersonalId());
        for(Ingredient ingredient : ingredients){
            ingredientRepository.deleteById(ingredient.getIngredientId());
        }

        //포스트 상태 변경
        List<Post> posts = postRepository.findAllByMember(member1);
        for(Post post : posts){
            post.updateStatus(Status.TEMP);
        }

        //QNA 삭제
        List<Qna> qnas = qnaRepository.findAllByMember(member1);
        for(Qna qna : qnas){
            qnaRepository.deleteById(qna.getId());
        }

        //리뷰 삭제
        List<Review> reviews = reviewRepository.findAllByMember(member1);
        for(Review review : reviews){
            reviewRepository.deleteById(review.getId());
        }

        //stored recipe 삭제
        List<StoredRecipe> storedRecipes = storedRecipeRepository.findAllByMember(member1);
        for(StoredRecipe storedRecipe : storedRecipes){
            storedRecipeRepository.deleteById(storedRecipe.getId());
        }

        //싫어하는 음식 삭제
        List<DislikedFood> dislikedFoods = dislikedFoodRepository.findAllByMember_Id(member1.getId());
        for(DislikedFood dislikedFood : dislikedFoods){
            dislikedFoodRepository.deleteById(dislikedFood.getId());
        }

        //token 삭제
        tokenRepository.delete(refreshToken);

        //멤버 삭제
        memberRepository.delete(member1);
    }

}