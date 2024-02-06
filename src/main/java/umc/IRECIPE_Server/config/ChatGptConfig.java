package umc.IRECIPE_Server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatGptConfig {

    public static String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String MODEL = "gpt-3.5-turbo";
    public static final String MEDIA_TYPE = "application/json; charset=UTF-8";
    public static final String URL = "https://api.openai.com/v1/chat/completions";
}
