package umc.IRECIPE_Server.converter;

import java.util.List;
import java.util.stream.Collectors;
import umc.IRECIPE_Server.common.enums.Age;
import umc.IRECIPE_Server.common.enums.Gender;
import umc.IRECIPE_Server.common.enums.Role;
import umc.IRECIPE_Server.dto.IngredientResponse;
import umc.IRECIPE_Server.dto.MemberResponse;
import umc.IRECIPE_Server.dto.MemberSignupRequestDto;
import umc.IRECIPE_Server.dto.MemberSignupResponseDto;
import umc.IRECIPE_Server.entity.Ingredient;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.MemberAllergy;
import umc.IRECIPE_Server.jwt.JwtProvider;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MemberConverter {
    public static MemberSignupResponseDto.JoinResultDTO toJoinResultDTO(Member member, JwtProvider jwtProvider){
        return jwtProvider.generateTokenDto(member.getPersonalId());
    }

    public static Member toMember(MemberSignupRequestDto.JoinDto request){

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
                .personalId(request.getPersonalId())
                .role(Role.USER)
                .build();
    }

    public static MemberResponse.getMemberResultDto getMemberResultDTO(Member member){
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

    public static MemberResponse.updateMemberResultDto updateMemberResultDto(Member member){
        return MemberResponse.updateMemberResultDto.builder()
                .memberId(member.getId())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
