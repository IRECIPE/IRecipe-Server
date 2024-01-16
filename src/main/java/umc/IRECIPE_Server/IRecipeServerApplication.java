package umc.IRECIPE_Server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class IRecipeServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IRecipeServerApplication.class, args);
	}

}
