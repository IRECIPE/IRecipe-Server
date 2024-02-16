package umc.IRECIPE_Server.converter;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import umc.IRECIPE_Server.common.enums.Age;
import umc.IRECIPE_Server.common.enums.Gender;
import umc.IRECIPE_Server.common.enums.Role;
import umc.IRECIPE_Server.dto.MemberRequest;
import umc.IRECIPE_Server.dto.MemberResponse;
import umc.IRECIPE_Server.dto.MemberResponse.getPostsDto;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.MemberAllergy;
import umc.IRECIPE_Server.entity.Post;
import umc.IRECIPE_Server.entity.Review;
import umc.IRECIPE_Server.jwt.JwtProvider;
import umc.IRECIPE_Server.repository.TokenRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MemberConverter {
    public static MemberResponse.JoinResultDto toJoinResult(Member member, JwtProvider jwtProvider){
        return jwtProvider.generateTokenDto(member.getPersonalId());
    }


    public static Member toMember(MemberRequest.JoinDto request, String url){

        Gender gender = null;
        Age age = null;

        gender = switch (request.getGender()) {
            case 1 -> Gender.MALE;
            case 2 -> Gender.FEMALE;
            default -> gender;
        };

        age = switch (request.getAge()) {
            case 1 -> Age.TEN;
            case 2 -> Age.TWENTY;
            case 3 -> Age.THIRTY;
            case 4 -> Age.FORTY;
            case 5 -> Age.FIFTY;
            case 6 -> Age.SIXTY;
            default -> age;
        };

        return Member.builder()
                .name(request.getName())
                .gender(gender)
                .nickname(request.getNickname())
                .memberAllergyList(new ArrayList<>())
                .age(age)
                .profileImage(url)
                .personalId(request.getPersonalId())
                .role(Role.USER)
                .build();
    }

    public static MemberResponse.getMemberResultDto getMemberResult(Member member){
        List<MemberAllergy> allergyList = member.getMemberAllergyList();

        List<Long> allergyIds = allergyList.stream()
                .map(MemberAllergy::getId)
                .toList();

        return MemberResponse.getMemberResultDto.builder()
                .name(member.getName())
                .nickname(member.getNickname())
                .gender(member.getGender())
                .age(member.getAge())
                .activity(member.getActivity())
                .important(member.getImportant())
                .event(member.getEvent())
                .allergyList(allergyIds)
                .build();
    }

    public static MemberResponse.updateMemberResultDto updateMemberResult(Member member){
        return MemberResponse.updateMemberResultDto.builder()
                .memberId(member.getId())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static MemberResponse.updateNicknameResultDto updateNicknameResult(){
        return MemberResponse.updateNicknameResultDto.builder()
                .str("사용 가능한 닉네임 입니다.")
                .build();
    }

    public static MemberResponse.getPostsDto postsDto(Post post){
        return MemberResponse.getPostsDto.builder()
                .title(post.getTitle())
                .subhead(post.getSubhead())
                .level(post.getLevel())
                .likes(post.getLikes())
                .fileName(post.getFileName())
                .content(post.getContent())
                .category(post.getCategory())
                .imageUrl(post.getImageUrl())
                .build();
    }

}
