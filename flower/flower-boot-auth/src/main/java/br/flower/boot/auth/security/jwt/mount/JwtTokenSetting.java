package br.flower.boot.auth.security.jwt.mount;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public  class JwtTokenSetting {

	//@Value("${jwt.token.expiration-time:600000L}")
	private static Long expirationTimeToken;
	
	// 30 Dias
	//@Value("${jwt.token.refresh.expiration-time:2592000000L}")
	private static Long expirationTimeRefreshToken;

	JwtTokenSetting(@Value("${jwt.token.expiration-time:600000}") String expire, 
			@Value("${jwt.token.refresh.expiration-time:2592000000}") String expireRefresh) {
		JwtTokenSetting.expirationTimeRefreshToken = Long.parseLong(expireRefresh);
		JwtTokenSetting.expirationTimeToken = Long.parseLong(expire);
	}
	
	protected static Long getExpirationTimeToken() {
		return expirationTimeToken;
	}

	protected static Long getExpirationTimeRefreshToken() {
		return expirationTimeRefreshToken;
	}

	
	
}
