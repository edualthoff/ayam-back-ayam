package br.flower.boot.exception.type;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.flower.boot.exception.msg.ApiErrorCode;



//@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ApiConnectionRefused extends RuntimeException implements ApiErrorCode {
	private static final long serialVersionUID = -581441557989962988L;

	public ApiConnectionRefused(String message) {
        super(message);
    }

	@Override
	public String getErrorCode() {
		return "NE-01";
	}

	@Override
	public String getMessageError() {
		// TODO Auto-generated method stub
		return null;
	}
}
