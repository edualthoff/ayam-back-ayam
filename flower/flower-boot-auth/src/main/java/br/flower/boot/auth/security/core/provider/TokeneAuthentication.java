package br.flower.boot.auth.security.core.provider;

import java.io.Serializable;


public interface TokeneAuthentication extends Serializable {

	public boolean validateToken(String token);
	
	public void authenticationSytemToken(String token);
}
