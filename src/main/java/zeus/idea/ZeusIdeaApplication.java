package zeus.idea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ZeusIdeaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZeusIdeaApplication.class, args);
	}
}
