package umc.IRECIPE_Server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TokenDto {
    private final String grantType;
    private final String accessToken;
    private final String refreshToken;
}