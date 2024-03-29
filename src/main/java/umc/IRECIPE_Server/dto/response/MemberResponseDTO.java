package umc.IRECIPE_Server.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.IRECIPE_Server.common.enums.Age;
import umc.IRECIPE_Server.common.enums.Category;
import umc.IRECIPE_Server.common.enums.Gender;
import umc.IRECIPE_Server.common.enums.Level;

public class MemberResponseDTO {
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

    @Builder
    @Getter
    @AllArgsConstructor
    public static class JoinResultDto{
        Long memberId;
        String grantType;
        String accessToken;
        String refreshToken;
        Long accessTokenExpiresIn;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getPostsDto{
        String title;
        String subhead;
        String content;
        Integer likes;
        String imageUrl;
        String fileName;
        Category category;
        Level level;
        Float score;
    }


}
