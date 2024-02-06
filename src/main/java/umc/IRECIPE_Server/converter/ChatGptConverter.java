package umc.IRECIPE_Server.converter;

import umc.IRECIPE_Server.dto.request.DislikedFoodRequestDTO;
import umc.IRECIPE_Server.dto.response.ChatGptButtonResponseDTO;
import umc.IRECIPE_Server.dto.response.UserChatGptResponseDTO;
import umc.IRECIPE_Server.entity.DislikedFood;
import umc.IRECIPE_Server.entity.Member;
import umc.IRECIPE_Server.entity.StoredRecipe;

public class ChatGptConverter {

    // 버튼 질문 반환
    public static ChatGptButtonResponseDTO.buttonResponseDTO tobuttonResponseDTO() {
        return ChatGptButtonResponseDTO.buttonResponseDTO.builder()
                .button1("랜덤으로 음식 레시피 추천받기")
                .button2("냉장고에 있는 재료로 음식 레시피 추천받기")
                .button3("유통기한이 얼마 남지 않은 냉장고 재료로 음식 레시피 추천받기")
                .button4("싫어하는 음식 입력하기")
                .build();
    }

    // 랜덤 레시피 반환
    public static UserChatGptResponseDTO.UserGptResponseDTO toUserGptResponseDTO(String gptResponse) {
        return UserChatGptResponseDTO.UserGptResponseDTO.builder()
                .gptResponse(gptResponse)
                .build();
    }

    // 싫어하는 음식 저장
    public static DislikedFood saveDislikedFood(Member member, String dislikedFood) {
        return DislikedFood.builder()
                .member(member)
                .name(dislikedFood)
                .build();
    }

    // 레시피 저장
    public static StoredRecipe saveStoredRecipe(Member member, String recipe) {
        return StoredRecipe.builder()
                .member(member)
                .body(recipe)
                .build();
    }

}
