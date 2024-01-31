package umc.IRECIPE_Server.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.IRECIPE_Server.common.enums.Age;
import umc.IRECIPE_Server.common.enums.Gender;
import umc.IRECIPE_Server.entity.MemberAllergy;

public class MemberResponse {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getMemberResultDto{
        String name;
        String nickname;
        Gender gender;
        Age age;
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
    public static class updateMemberResultDto{
        Long memberId;
        LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class updateNicknameResultDto{
        String str;
    }
}
