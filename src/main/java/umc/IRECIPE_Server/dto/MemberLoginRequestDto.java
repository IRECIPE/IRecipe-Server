package umc.IRECIPE_Server.dto;

import lombok.Data;

@Data
public class MemberLoginRequestDto {
    private String id;
    private String password;
}