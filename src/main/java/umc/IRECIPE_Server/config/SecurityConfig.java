package umc.IRECIPE_Server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 리소스 접근 권한 설정
                .authorizeHttpRequests((auth) -> auth

                        .requestMatchers("/", "/health").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
