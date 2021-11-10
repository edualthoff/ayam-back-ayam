package br.flower.boot.exception.type.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.flower.boot.exception.config.ApiMessageSourceError;
import br.flower.boot.exception.msg.ApiErrorCode;



@ResponseStatus(code = HttpStatus.CONFLICT)
public class ApiConflictDataException extends RuntimeException implements ApiErrorCode {
	private static final long serialVersionUID = 992428721490241972L;

	private static final Logger log = LoggerFactory.getLogger(ApiConflictDataException.class);

	private String errorCode;
	private String messageError;
	
	/**
	 * Mensagme do error referente ao objeto
	 * @param message
	 */
	public ApiConflictDataException(String message) {
        super(message);
        this.errorCode = ApiMessageSourceError.toMessage("conflict.error.code");
        this.messageError = ApiMessageSourceError.toMessage("conflict.error.msg");
    }
	
	/**
	 * Codigo do error, mesagem padrao do error e a mensagem referente ao o objeto
	 * @param message
	 * @param errorCode
	 * @param messageError
	 */
	public ApiConflictDataException(String errorCode, String messageError, String message) {
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
	public ApiConflictDataException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.messageError = ApiMessageSourceError.toMessage("conflict.error.msg");
	}


	@Override
	public String getErrorCode() {
		log.debug("error code: "+ApiMessageSourceError.toMessage("conflict.error.code"));
		return errorCode;
	}


	@Override
	public String getMessageError() {
		// TODO Auto-generated method stub
		return messageError;
	}
}
