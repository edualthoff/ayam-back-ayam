package br.flower.boot.auth.security.jwt;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtTokenDto implements Serializable{
	private static final long serialVersionUID = -464050028843649521L;

	// O domínio da aplicação geradora do token
	private String iss;
	//É o assunto do token, mas é muito utilizado para guarda o ID do usuário
	private String sub;
	// O id do token
	private String jti;
	/* Tipo de token Ex "Bearer", "Refresh" */
	private String typ;
	// (audience) Destinatário do token, representa a aplicação que irá usá-lo.
	private String aud;
	// Nome do cliente que solicitou o login
	private String azp;
	// id da session
	@JsonProperty("session_state")
	private String sessionState;
	// Origem do token quem solicitou - CORS HTTP
	@JsonProperty("allowed-origins")
	private String allowedOrigins;
	// Data para expirar o token
	private Long exp;
	// Date que foi emitido o token
	private Long iat;
	private String email;
	private String name;
	@JsonProperty("given_name")
	private String givenName;
	private List<String> roles;
	
	
	public JwtTokenDto() {
		super();
	}
	
	
}
