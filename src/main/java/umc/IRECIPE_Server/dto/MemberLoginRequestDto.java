package umc.IRECIPE_Server.dto;

import java.util.List;
import lombok.Data;
import lombok.Getter;

public class MemberLoginRequestDto {
    @Getter
    public static class JoinLoginDto {
        String personalId;
    }
}
