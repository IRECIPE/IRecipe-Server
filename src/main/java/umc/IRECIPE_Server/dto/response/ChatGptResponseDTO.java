package umc.IRECIPE_Server.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import umc.IRECIPE_Server.dto.request.ChatGptMessageDto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class ChatGptResponseDTO implements Serializable {
    // ChatGpt 에서 백엔드로 오는 응답 Dto

   private String id;
   private String object;
   private LocalDate created;
   private String model;
   private List<Choice> choices;

   @Getter
   @Setter
   public static class Choice {
      private ChatGptMessageDto message;
      @JsonProperty("finish_reason")
      private String finishReason;
      private int index;

   }

}
