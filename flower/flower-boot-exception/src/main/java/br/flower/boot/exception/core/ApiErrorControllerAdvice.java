package br.flower.boot.exception.core;

import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import br.flower.boot.exception.config.ApiMessageSourceError;
import br.flower.boot.exception.msg.ApiErrorMessage;
import br.flower.boot.exception.type.client.ApiBadRequestException;
import br.flower.boot.exception.type.client.ApiConflictDataException;
import br.flower.boot.exception.type.client.ApiNotFoundException;
import br.flower.boot.exception.type.client.ApiUnsupportedMediaType;
import br.flower.boot.exception.type.server.ApiInternalServerError;





@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ApiErrorControllerAdvice {
	private static final Logger log = LoggerFactory.getLogger(ApiErrorControllerAdvice.class);

	@Value("${api.version}")
	private String currentApiVersion;
	@Value("${api.sendreport.uri}")
	private String sendReportUri;
	@Autowired
	private HttpServletRequest request;
	
	
	public String getCurrentApiVersion() {
		return currentApiVersion;
	}

	public String getSendReportUri() {
		return sendReportUri;
	}

	public HttpServletRequest getRequest() {
		return request;
	}
	
	/* 
	 * Error code 400 ~
	 * 
	 */

	/*
	 * Exception(Error) padrao do JAVA - Spring - implementado o response
	 */	
	@ExceptionHandler({ NoSuchElementException.class })
	public ApiErrorMessage handleNotFoundExceptionNoSuchElementException(NoSuchElementException e) {
		return new ApiErrorMessage(this.currentApiVersion, 
						ApiMessageSourceError.toMessage("not_found.error.code"),
						ApiMessageSourceError.toMessage("not_found.error.msg"), 
						this.request.getRequestURI().toString(),
						e.getMessage(), 
						ApiMessageSourceError.toMessage("msg.padrao")
						);
				
	}
	
	@ExceptionHandler({ NoHandlerFoundException.class })
	public ResponseEntity<ApiErrorMessage> handleNotFoundException(NoHandlerFoundException e) {
		return errorRequest(HttpStatus.NOT_FOUND,
				new ApiErrorMessage(this.currentApiVersion, 
						ApiMessageSourceError.toMessage("not_found.error.url.code"),
						ApiMessageSourceError.toMessage("not_found.error.url.msg"), 
						this.request.getRequestURI().toString(),
						e.getMessage(), 
						ApiMessageSourceError.toMessage("msg.padrao")
						)
				);
	}
	
	@ExceptionHandler({ NullPointerException.class })
	public ResponseEntity<ApiErrorMessage> handleNotFoundExceptionNullPointerException(NullPointerException e) {
		return errorRequest(HttpStatus.NOT_FOUND,
				new ApiErrorMessage(this.currentApiVersion, 
						ApiMessageSourceError.toMessage("not_found.error.url.code"),
						ApiMessageSourceError.toMessage("not_found.error.url.msg"), 
						this.request.getRequestURI().toString(),
						e.getMessage(), 
						ApiMessageSourceError.toMessage("msg.padrao")
						)
				);
	}	
	
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler({ ConstraintViolationException.class })
	public ApiErrorMessage handleConstraintViolationException(ConstraintViolationException e) {
		ConstraintViolation<?> value = e.getConstraintViolations().iterator().next();
		return new ApiErrorMessage(this.currentApiVersion, 
						ApiMessageSourceError.toMessage("not_found.error.code"),
				        ApiMessageSourceError.toMessage("not_found.error.msg"),
						this.request.getRequestURI().toString(),
						value.getMessage()+" "+value.getPropertyPath(),
						ApiMessageSourceError.toMessage("msg.padrao")
						);
	}
	/* Fim */	
	
	// 404
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler({ ApiNotFoundException.class })
	public ApiErrorMessage handleNotFoundException(ApiNotFoundException e) {
		return new ApiErrorMessage(this.currentApiVersion, 
						e.getErrorCode(),
						e.getMessageError(), 
						this.request.getRequestURI().toString(),
						e.getMessage(), 
						ApiMessageSourceError.toMessage("msg.padrao")
						);
	}
	
	// 404
	@ResponseStatus(code = HttpStatus.CONFLICT)
	@ExceptionHandler({ ApiConflictDataException.class })
	public ApiErrorMessage handleNotFoundException(ApiConflictDataException e) {
		return new ApiErrorMessage(this.currentApiVersion, 
						e.getErrorCode(),
						e.getMessageError(), 
						this.request.getRequestURI().toString(),
						e.getMessage(), 
						ApiMessageSourceError.toMessage("msg.padrao")
						);
	}
	// 415
	@ResponseStatus(code = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler({ ApiUnsupportedMediaType.class })
	public ApiErrorMessage handleNotFoundExceptionApiUnsupportedMediaType(ApiUnsupportedMediaType e) {
		return new ApiErrorMessage(this.currentApiVersion, 
						e.getErrorCode(),
						e.getMessageError(), 
						this.request.getRequestURI().toString(),
						e.getMessage(), 
						ApiMessageSourceError.toMessage("msg.padrao")
						);
	}
	

	/*
	 * Error 500 ~
	 */

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ ApiBadRequestException.class })
	public ApiErrorMessage handleBadRequestException(ApiBadRequestException e) {
		return new ApiErrorMessage(this.currentApiVersion, 
						e.getErrorCode(),
						e.getMessageError(), 
						this.request.getRequestURI().toString(),
						e.getMessage(), 
						ApiMessageSourceError.toMessage("msg.padrao")
						);
	}


	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ ApiInternalServerError.class })
	public ApiErrorMessage handleApiInternalServerError(ApiInternalServerError e) {
		return new ApiErrorMessage(this.currentApiVersion, 
						e.getErrorCode(),
						e.getMessageError(), 
						this.request.getRequestURI().toString(),
						e.getMessage(), 
						ApiMessageSourceError.toMessage("msg.padrao")
						);
	}
	
	/*
	 * @ExceptionHandler({ApiServiceException.class}) public
	 * ResponseEntity<ApiErrorMessage>
	 * handleDogsServiceException(ApiServiceException e){ return
	 * error(HttpStatus.INTERNAL_SERVER_ERROR, e); }
	 */
	
	public ResponseEntity<ApiErrorMessage> errorRequest(HttpStatus status, ApiErrorMessage error) {
		log.error("Exception : ", error, status);
		return new ResponseEntity<>(error, status);
	}

}
