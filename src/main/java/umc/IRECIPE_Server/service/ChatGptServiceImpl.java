package umc.IRECIPE_Server.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import umc.IRECIPE_Server.config.ChatGptConfig;
import umc.IRECIPE_Server.dto.ChatGptMessageDto;
import umc.IRECIPE_Server.dto.ChatGptRequestDTO;
import umc.IRECIPE_Server.dto.ChatGptResponseDTO;
import umc.IRECIPE_Server.dto.UserChatGptRequestDTO;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChatGptServiceImpl implements ChatGptService {
    private static RestTemplate restTemplate = new RestTemplate();

    @Value("${chatgpt.api-key}")
    private String apiKey;

    // ChatGPT 에 요청할 때 포함해야 하는 값 세팅하기
    public HttpEntity<ChatGptRequestDTO> buildHttpEntity(ChatGptRequestDTO requestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ChatGptConfig.MEDIA_TYPE));
        headers.add(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + apiKey);
        return new HttpEntity<>(requestDTO, headers);
    }

    // ChatGPT API 응답 반환 받기
    public ChatGptResponseDTO getResponse(HttpEntity<ChatGptRequestDTO> chatGptRequestDTOHttpEntity) {
        // 답변 길어질 시 Timeout Error : 1분 설정 60sec * 10000ms
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(60000);
        requestFactory.setReadTimeout(60 * 1000);
        restTemplate.setRequestFactory(requestFactory);

        ResponseEntity<ChatGptResponseDTO> responseEntity = restTemplate.postForEntity(
                ChatGptConfig.URL,
                chatGptRequestDTOHttpEntity,
                ChatGptResponseDTO.class
        );
        return responseEntity.getBody();
    }

    // ChatGPT API 요청하기
    @Override
    public ChatGptResponseDTO askQuestion(String question) {
        List<ChatGptMessageDto> messages = new ArrayList<>();
        messages.add(ChatGptMessageDto.builder()
                .role("user")
                .content(question)
                .build());

        return this.getResponse(
                this.buildHttpEntity(
                        new ChatGptRequestDTO(
                                messages,
                                ChatGptConfig.MODEL,
                                ChatGptConfig.MAX_TOKEN,
                                ChatGptConfig.TEMPERATURE
                        )
                )
        );
    }
}
