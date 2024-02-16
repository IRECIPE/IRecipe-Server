package umc.IRECIPE_Server.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.IRECIPE_Server.validation.annotation.ExistAllergies;
import umc.IRECIPE_Server.validation.annotation.ExistNickname;
import umc.IRECIPE_Server.validation.annotation.ExistPersonalId;

public class MemberRequestDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class fixMemberInfoDto{
        @NotBlank
        String name;
        @NotBlank
        String nickname;
        @NotNull
        Integer gender;
        @NotNull
        Integer age;
        String imageUrl;
        Boolean important;
        Boolean activity;
        Boolean event;
        List<Long> allergyList;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class checkNicknameIsExist{
        @NotNull
        String nickname;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class findMemberIdDto{
        @NotNull
        String personalId;
    }

    @Getter
    @Valid
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinDto{
        @NotBlank
        String name;

        @NotNull
        Integer gender;

        @NotNull
        Integer age;

        @ExistNickname
        String nickname;

        @ExistAllergies
        List<Long> allergyList;

        @ExistPersonalId
        String personalId;
    }
}
