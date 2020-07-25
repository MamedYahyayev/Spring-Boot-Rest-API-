package az.maqa.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

	
	@Autowired
	private Environment enviroment;
	
	public String getTokenSecret() {
		return enviroment.getProperty("tokenSecret");
	}
	
}
