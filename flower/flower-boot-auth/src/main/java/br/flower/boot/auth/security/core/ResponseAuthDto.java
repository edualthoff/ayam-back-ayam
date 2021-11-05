package br.flower.boot.auth.security.core;

import lombok.Data;

@Data
//@Component
public class ResponseAuthDto {

	
	private String token;
	private String refreshTorkn;
	
}
