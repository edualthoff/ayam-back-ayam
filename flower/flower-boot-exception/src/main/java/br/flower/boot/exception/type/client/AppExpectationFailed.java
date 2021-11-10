package br.flower.boot.exception.type.client;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.flower.boot.exception.config.ApiMessageSourceError;
import br.flower.boot.exception.msg.ApiErrorCode;


@ResponseStatus(code = HttpStatus.EXPECTATION_FAILED)
public class AppExpectationFailed extends RuntimeException implements ApiErrorCode  {
	private static final long serialVersionUID = 2610624929176206900L;

	private String errorCode;
	private String messageError;
	
	/**
	 * Mensagme do error referente ao objeto
	 * @param message
	 */
	public AppExpectationFailed(String message) {
        super(message);
        this.errorCode = ApiMessageSourceError.toMessage("expectationFailed.error.code");
        this.messageError = ApiMessageSourceError.toMessage("expectationFailed.error.msg");
    }
	
	/**
	 * Codigo do error, mesagem padrao do error e a mensagem referente ao o objeto
	 * @param message
	 * @param errorCode
	 * @param messageError
	 */
	public AppExpectationFailed(String errorCode, String messageError, String message) {
		super(message);
		this.errorCode = errorCode;
		this.messageError = messageError;
	}
	
	/**
	 * Codigo do error e a mensagem referente ao o objeto
	 * @param errorCode
	 * @param message
	 */
	public AppExpectationFailed(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.messageError = ApiMessageSourceError.toMessage("expectationFailed.error.msg");
	}
	

	@Override
	public String getErrorCode() {
		return errorCode;
	}


	@Override
	public String getMessageError() {
		return messageError;	
	}
}
