package umc.IRECIPE_Server.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.IRECIPE_Server.common.enums.Age;
import umc.IRECIPE_Server.common.enums.Gender;
import umc.IRECIPE_Server.entity.MemberAllergy;

public class MemberRequest {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class fixMemberInfoDto{
        String name;
        String nickname;
        Integer gender;
        Integer age;
        String imageUrl;
        Boolean important;
        Boolean activity;
        Boolean event;
        List<Long> allergyList;

    }
}
