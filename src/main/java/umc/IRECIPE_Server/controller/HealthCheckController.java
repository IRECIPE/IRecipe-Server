package umc.IRECIPE_Server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    // AWS Elastic Beanstalk CI/CD Health Check
    @GetMapping("/health")
    public String healthCheck() {
        return "I'm healthy!";
    }
}
