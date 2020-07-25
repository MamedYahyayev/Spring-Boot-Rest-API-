package az.maqa.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import az.maqa.project.security.AppProperties;

@SpringBootApplication
public class WsAppApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WsAppApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WsAppApplication.class);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SpringApplicationContext applicationContext() {
		return new SpringApplicationContext();
	}

	
	@Bean(name = "AppProperties")	
	public AppProperties appProperties() {
		return new AppProperties();
	}
}
