package umc.IRECIPE_Server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberRequest {
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
    public static class JoinDto{
        @NotBlank
        String name;
        @NotNull
        Integer gender;
        @NotNull
        Integer age;
        @NotBlank
        String nickname;
        List<Long> allergyList;
        @NotBlank
        String personalId;
    }
}
