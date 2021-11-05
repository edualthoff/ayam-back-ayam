package br.flower.boot.auth.security.jwt;

import java.io.Serializable;
import java.util.Map;

public interface JwtTokenMount extends Serializable {

	Map<String, Object> implementsCustonsClaims();
	
	Long getExpirationTime();
	
}
