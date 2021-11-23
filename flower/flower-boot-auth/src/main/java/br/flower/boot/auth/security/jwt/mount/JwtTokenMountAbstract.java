package br.flower.boot.auth.security.jwt.mount;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import br.flower.boot.auth.security.jwt.JwtTokenClaim;

public abstract class JwtTokenMountAbstract implements JwtTokenMount {
	private static final long serialVersionUID = -432469349355580781L;

	private String issDomainNameOrigin;
	private String clientName;
	private String typTipoToken;
	private String aud;
	private String idToken;
	private UUID subIdUser;
	/* Tempo de expiracação do token em milezimos  */
	private Long expiration;
	private UUID sessionId;
	
	public JwtTokenMountAbstract(String issDomainNameOrigin, String clientName, String typTipoToken, String aud,
			UUID subIdUser, Long expiration, UUID sessionId) {
		this.issDomainNameOrigin = issDomainNameOrigin;
		this.clientName = clientName;
		this.typTipoToken = typTipoToken;
		this.aud = aud;
		this.idToken = this.generetedIdToken();
		this.subIdUser = subIdUser;
		this.expiration = expiration;
		this.sessionId = sessionId;
		System.out.println("user abs "+subIdUser);

	}

	public JwtTokenMountAbstract(String idToken, String issDomainNameOrigin, String clientName, String typTipoToken, String aud,
			UUID subIdUser, Long expiration, UUID sessionId) {
		this.issDomainNameOrigin = issDomainNameOrigin;
		this.clientName = clientName;
		this.typTipoToken = typTipoToken;
		this.aud = aud;
		this.idToken = idToken;
		this.subIdUser = subIdUser;
		this.expiration = expiration;
		this.sessionId = sessionId;
		System.out.println("user abs "+subIdUser);
	}
	
	@Override
	public Map<String, Object> implementsCustonsClaims() {
		return null;
	}

	@Override
	public Long getExpirationTime() {
		return this.expiration;
	}

	public Map<String, Object> implementsAllClaims() {
		Map<String, Object> claims = new HashMap<>();
		claims.put(JwtTokenClaim.CLAIM_KEY_SUB.getClaim(), subIdUser);
		claims.put(JwtTokenClaim.CLAIM_KEY_AUD.getClaim(), aud);
		claims.put(JwtTokenClaim.CLAIM_KEY_TYP.getClaim(), typTipoToken);
		claims.put(JwtTokenClaim.CLAIM_KEY_AZP.getClaim(), clientName);
		claims.put(JwtTokenClaim.CLAIM_KEY_ISS.getClaim(), issDomainNameOrigin);
		//claims.put(CLAIM_KEY_EXPIRED, expiration);
		claims.put(JwtTokenClaim.CLAIM_KEY_ID_TOKEN.getClaim(), idToken);
		claims.put(JwtTokenClaim.CLAIM_KEY_SESSION_STATE.getClaim(), sessionId);
		
		if(implementsCustonsClaims() != null && !implementsCustonsClaims().isEmpty()) {
			claims.putAll(implementsCustonsClaims());
		}
		
		return claims;
	}
	
	private String generetedIdToken() {
		return UUID.randomUUID().toString();
	}
}
