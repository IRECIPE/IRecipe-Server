package umc.IRECIPE_Server.dto.request;

import lombok.Data;

@Data
public class MemberLoginRequestDto {
    private String nickname;
    private String password;
}