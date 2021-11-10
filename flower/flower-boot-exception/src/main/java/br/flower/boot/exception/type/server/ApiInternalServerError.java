package br.flower.boot.exception.type.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.flower.boot.exception.config.ApiMessageSourceError;
import br.flower.boot.exception.msg.ApiErrorCode;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ApiInternalServerError extends RuntimeException implements ApiErrorCode {
	private static final long serialVersionUID = 4029014937971868111L;
	private static final Logger log = LoggerFactory.getLogger(ApiInternalServerError.class);

	private String errorCode;
	private String messageError;
	
	/**
	 * Mensagme do error referente ao objeto
	 * @param message
	 */
	public ApiInternalServerError(String message) {
        super(message);
        this.errorCode = ApiMessageSourceError.toMessage("internal_server_error.error.code");
        this.messageError = ApiMessageSourceError.toMessage("internal_server_error.error.msg");
    }
	
	/**
	 * Codigo do error, mesagem padrao do error e a mensagem referente ao o objeto
	 * @param message
	 * @param errorCode
	 * @param messageError
	 */
	public ApiInternalServerError(String errorCode, String messageError, String message) {
		super(message);
		this.errorCode = errorCode;
		this.messageError = messageError;
	}

	/**
	 * Codigo do error e a mensagem referente ao o objeto
	 * @param message
	 * @param errorCode
	 * @param messageError
	 */
	public ApiInternalServerError(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
        this.messageError = ApiMessageSourceError.toMessage("internal_server_error.error.msg");
	}


	@Override
	public String getErrorCode() {
        this.errorCode = ApiMessageSourceError.toMessage("internal_server_error.error.code");
		return errorCode;
	}


	@Override
	public String getMessageError() {
		// TODO Auto-generated method stub
		return messageError;
	}
}