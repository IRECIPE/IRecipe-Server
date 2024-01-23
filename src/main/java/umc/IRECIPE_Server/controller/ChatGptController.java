package umc.IRECIPE_Server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.IRECIPE_Server.apiPayLoad.ApiResponse;
import umc.IRECIPE_Server.converter.ChatGptConverter;
import umc.IRECIPE_Server.dto.ChatGptResponseDTO;
import umc.IRECIPE_Server.dto.UserChatGptRequestDTO;
import umc.IRECIPE_Server.dto.UserChatGptResponseDTO;
import umc.IRECIPE_Server.service.ChatGptService;

@RestController
@RequestMapping("/ai-chat")
@RequiredArgsConstructor
public class ChatGptController {
    private final ChatGptService chatGPTService;

    // 사용자가 ChatGPT 에 처음 접속했을 때 반환할 메세지


    // 사용자가 ChatGPT 에 랜덤 레시피 요청 or 직접 입력
    @PostMapping("/random")
    public ApiResponse<UserChatGptResponseDTO.UserGptResponseDTO> getRandomResponse(@RequestBody @Valid UserChatGptRequestDTO.UserGptRequestDTO request) {
        // 1. 유저 질문 : request
        // 2. chatgptrequest 에 전달
        // 3. chatgptresponse 반환
        // 4. 유저에 response 반환

        String response = chatGPTService.askQuestion(request.getQuestion()).getChoices().get(0).getMessage().getContent();
        return ApiResponse.onSuccess(ChatGptConverter.toUserGptResponseDTO(response));
    }
}
