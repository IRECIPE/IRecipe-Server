package umc.IRECIPE_Server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import java.util.List;


public class MemberSignupRequestDto {
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
