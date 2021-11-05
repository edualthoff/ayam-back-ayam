package br.flower.boot.jwt.core;

import java.io.Serializable;


public interface TokeneAuthetntication extends Serializable {

	public boolean validateToken(String token);
	
	public void authenticationSytemToken(String token);
}
