package br.flower.boot.auth.security.core;

import lombok.Data;

@Data
//@Component
public class ResponseAuthDto {

	private String tokenType;
	private String token;
	private String refreshToken;
	
}
