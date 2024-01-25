package umc.IRECIPE_Server.dto;

import lombok.Data;

@Data
public class MemberLoginRequestDto {
    private String nickname;
    private String password;
}