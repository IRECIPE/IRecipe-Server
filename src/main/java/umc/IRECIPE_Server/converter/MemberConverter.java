package umc.IRECIPE_Server.converter;

import lombok.RequiredArgsConstructor;
import umc.IRECIPE_Server.common.enums.Age;
import umc.IRECIPE_Server.common.enums.Gender;
import umc.IRECIPE_Server.common.enums.Role;
import umc.IRECIPE_Server.dto.MemberSignupRequestDto;
import umc.IRECIPE_Server.dto.MemberSignupResponseDto;
import umc.IRECIPE_Server.entity.Member;
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
                .profileImage(request.getProfileImage())
                .memberAllergyList(new ArrayList<>())
                .age(age)
                .personalId(request.getPersonalId())
                .role(Role.USER)
                .build();
    }
}
