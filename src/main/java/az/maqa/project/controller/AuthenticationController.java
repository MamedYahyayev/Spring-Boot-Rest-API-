package az.maqa.project.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import az.maqa.project.model.request.LoginRequestModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

@RestController
public class AuthenticationController {

	
	@ApiOperation("User Login")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Response Headers" , 
					responseHeaders = {
					@ResponseHeader(name = "authorization" , description = "Bearer <JWT value here>" , response = String.class),
					@ResponseHeader(name = "userId" , description = "<Public user id value here>" , response = String.class)
			})
	})
	@PostMapping("/users/login")
	public void login(@RequestBody LoginRequestModel loginRequestModel) {
		
		throw new IllegalStateException("This method should not be called. This method is implemented by Spring Security");
	}
	
	
	
}
