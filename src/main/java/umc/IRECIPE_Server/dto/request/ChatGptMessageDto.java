package umc.IRECIPE_Server.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class ChatGptMessageDto implements Serializable {
    private String role;
    private String content;

    @Builder
    public ChatGptMessageDto(String role, String content) {
        this.role = role;
        this.content = content;
    }
}
