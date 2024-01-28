package umc.IRECIPE_Server.converter;

import umc.IRECIPE_Server.dto.response.ChatGptButtonResponseDTO;
import umc.IRECIPE_Server.dto.response.UserChatGptResponseDTO;

public class ChatGptConverter {

    // 버튼 질문 반환
    public static ChatGptButtonResponseDTO.buttonResponseDTO tobuttonResponseDTO() {
        return ChatGptButtonResponseDTO.buttonResponseDTO.builder()
                .button1("랜덤으로 음식 레시피 추천받기")
                .button2("냉장고에 있는 재료로 음식 레시피 추천받기")
                .button3("유통기한이 얼마 남지 않은 냉장고 재료로 음식 레시피 추천받기")
                .build();
    }

    // 랜덤 레시피 반환
    public static UserChatGptResponseDTO.UserGptResponseDTO toUserGptResponseDTO(String gptResponse) {
        return UserChatGptResponseDTO.UserGptResponseDTO.builder()
                .gptResponse(gptResponse)
                .build();
    }

}
