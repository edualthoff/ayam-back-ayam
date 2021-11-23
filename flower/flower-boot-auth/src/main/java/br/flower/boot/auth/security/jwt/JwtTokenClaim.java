package br.flower.boot.auth.security.jwt;


public enum JwtTokenClaim {

	// O principal sobre o qual o token afirma informações, como o usuário de um aplicativo
	CLAIM_KEY_SUB("sub"),
	// O domínio da aplicação geradora do token
	CLAIM_KEY_ISS("iss"),
	// O id do token
	CLAIM_KEY_ID_TOKEN("jti"),
	/** Tipo de token Ex "Acess", "Refresh" */
	CLAIM_KEY_TYP("type"),
	// (audience) Destinatário do token, representa a aplicação que irá usá-lo.
	CLAIM_KEY_AUD("aud"),
	// Nome do cliente que solicitou o login
	CLAIM_KEY_AZP("azp"),
	// Origem do token quem solicitou - CORS HTTP
	CLAIM_KEY_ALLOWED_ORIGINS("allowed-origins"),
	// id da session do client 
	CLAIM_KEY_SESSION_STATE("session_state");

	
	private String claim;
	
	JwtTokenClaim(String claim) {
		this.claim = claim;
	}

	public String getClaim() {
		return claim;
	}
	
}
