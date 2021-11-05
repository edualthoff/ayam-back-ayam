package br.flower.boot.auth.exception.type;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.flower.boot.exception.msg.ApiErrorCode;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class SocialMediaException extends RuntimeException implements ApiErrorCode {
	private static final long serialVersionUID = 7720650999392898566L;

	private String errorCode;
	private String messageError;
	
	public SocialMediaException(String message) {
		super(message);
		this.errorCode = "SM-01";
		this.messageError = "Social Media error";
	}
	@Override
	public String getErrorCode() {
		// TODO Auto-generated method stub
		return this.errorCode;
	}

	@Override
	public String getMessageError() {
		// TODO Auto-generated method stub
		return this.messageError;
	}

}
