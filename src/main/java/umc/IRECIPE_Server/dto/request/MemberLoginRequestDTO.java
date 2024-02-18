package umc.IRECIPE_Server.dto.request;

import lombok.Getter;

public class MemberLoginRequestDTO {
    @Getter
    public static class JoinLoginDto {
        String personalId;
    }
}
