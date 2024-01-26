package umc.IRECIPE_Server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class MemberSignupResponseDto {
    @Builder
    @Getter
    @AllArgsConstructor
    public static class JoinResultDTO{
        private final String grantType;
        private final String accessToken;
        private final String refreshToken;
        private final Long accessTokenExpiresIn;
    }
}
