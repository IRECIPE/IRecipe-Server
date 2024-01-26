package umc.IRECIPE_Server.dto;

import lombok.Data;

@Data
public class MemberLoginRequestDTO {
    private String nickname;
    private String password;
}