package br.flower.boot.exception.core;

import java.util.Objects;

import org.springframework.util.ObjectUtils;

import br.flower.boot.exception.config.ApiMessageSourceError;
import br.flower.boot.exception.type.client.ApiNotFoundException;

public class ApiThrowGeneratedFacade {

	/**
	 * Gerador de trown generico para valor não encontrado
	 * @param <T>
	 * @param value
	 * @return
	 */
	public static <T> T throwEmptyOrNullValue(T value) {
		if( Objects.isNull(value) || ObjectUtils.isEmpty(value) )
			throw new ApiNotFoundException(ApiMessageSourceError.toMessage("not_found.error.list.msg"));
		
		return value;
	}
}
