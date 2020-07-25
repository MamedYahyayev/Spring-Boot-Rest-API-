package az.maqa.project;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
			
		registry
			.addMapping("/**")
			.allowedMethods("*")
			.allowedOrigins("*");
	}

	// registry.addMapping("/users/email-verification") -- Specific Urls
	// registry.addMapping("/**").allowedMethods("GET" , "POST" , "PUT") -- Specific Methods
}
