package umc.IRECIPE_Server.dto;

import lombok.Getter;
import java.util.List;


public class MemberSignupRequestDto {
    @Getter
    public static class JoinDto{
        String name;
        Integer gender;
        Integer age;
        String nickname;
        List<Long> allergyList;
        String personalId;
    }
}
