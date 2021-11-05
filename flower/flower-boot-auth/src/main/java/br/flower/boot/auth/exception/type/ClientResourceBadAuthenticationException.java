package br.flower.boot.auth.exception.type;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.flower.boot.exception.msg.ApiErrorCode;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class ClientResourceBadAuthenticationException extends RuntimeException implements ApiErrorCode {
	private static final long serialVersionUID = 7720650999392898566L;

	private String errorCode;
	private String messageError;
	
	public ClientResourceBadAuthenticationException(String message) {
		super(message);
		this.errorCode = "UCFR-02";
		this.messageError = "Client error";
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
