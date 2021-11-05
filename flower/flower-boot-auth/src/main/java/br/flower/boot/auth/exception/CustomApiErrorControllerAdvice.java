package br.flower.boot.auth.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.flower.boot.auth.exception.type.ClientResourceBadAuthenticationException;
import br.flower.boot.auth.exception.type.SocialMediaException;
import br.flower.boot.exception.core.ApiErrorControllerAdvice;
import br.flower.boot.exception.msg.ApiErrorMessage;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class CustomApiErrorControllerAdvice extends ApiErrorControllerAdvice {

	@ExceptionHandler({ ClientResourceBadAuthenticationException.class })
	public ResponseEntity<ApiErrorMessage> badAuthenticationResourceClientException(ClientResourceBadAuthenticationException e) {
		return errorRequest(HttpStatus.NOT_FOUND,
				new ApiErrorMessage(super.getCurrentApiVersion(), 
						e.getErrorCode(),
						e.getMessageError(), 
						super.getRequest().getRequestURI().toString(),
						e.getMessage()
						)
				);
	}
	
	@ExceptionHandler({ SocialMediaException.class })
	public ResponseEntity<ApiErrorMessage> socialMediaExceptionException(SocialMediaException e) {
		return errorRequest(HttpStatus.NOT_FOUND,
				new ApiErrorMessage(super.getCurrentApiVersion(), 
						e.getErrorCode(),
						e.getMessageError(), 
						super.getRequest().getRequestURI().toString(),
						e.getMessage()
						)
				);
	}
	
	
}
