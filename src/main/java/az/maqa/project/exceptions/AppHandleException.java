package az.maqa.project.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import az.maqa.project.model.response.ErrorMessage;

@ControllerAdvice
public class AppHandleException {

	@ExceptionHandler(value = { UserServiceExceptions.class })
	public ResponseEntity<Object> handleUserServiceException(UserServiceExceptions e, WebRequest request) {
		
		ErrorMessage errorMessage = new ErrorMessage(e.getMessage() , new Date());
		
		return new ResponseEntity<Object>(errorMessage ,  new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	
	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handleOtherException(Exception e, WebRequest request) {
		
		ErrorMessage errorMessage = new ErrorMessage(e.getMessage() , new Date());
		
		return new ResponseEntity<Object>(errorMessage ,  new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
