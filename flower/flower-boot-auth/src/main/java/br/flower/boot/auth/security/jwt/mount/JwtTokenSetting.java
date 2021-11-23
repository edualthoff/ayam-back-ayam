package br.flower.boot.auth.security.jwt.mount;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public  class JwtTokenSetting {

	private static Long expirationTimeToken;
	private static Long expirationTimeRefreshToken;

	JwtTokenSetting(
			@Value("${flower.settings.jwt.token.expiration: 600000}") String expireToken, 
			@Value("${flower.settings.jwt.token.refresh.expiration: 2592000000}") String expireRefresh
			) {
		JwtTokenSetting.expirationTimeRefreshToken = Long.parseLong(expireRefresh);
		JwtTokenSetting.expirationTimeToken = Long.parseLong(expireToken);
	}
	
	protected static Long getExpirationTimeToken() {
		return expirationTimeToken;
	}

	protected static Long getExpirationTimeRefreshToken() {
		return expirationTimeRefreshToken;
	}

	
	
}
