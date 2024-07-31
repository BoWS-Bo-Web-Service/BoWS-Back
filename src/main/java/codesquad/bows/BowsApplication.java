package codesquad.bows;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BowsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BowsApplication.class, args);
	}

}
