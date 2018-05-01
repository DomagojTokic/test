package hr.ag04.feeddit.ag04feeddit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Entrance in Spring Boot web application.
 */
@SpringBootApplication
public class Ag04FeedditApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Ag04FeedditApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Ag04FeedditApplication.class);
	}
}
