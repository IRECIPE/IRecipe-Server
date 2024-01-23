package umc.IRECIPE_Server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
public class ChatGptRequestDTO implements Serializable {
    // 백엔드에서 ChatGpt 에 요청하는 Dto

    private List<ChatGptMessageDto> messages;
    private String model;

    @Builder
    public ChatGptRequestDTO(List<ChatGptMessageDto> messages, String model) {
        this.messages = messages;
        this.model = model;
    }
}
