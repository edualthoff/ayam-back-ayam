package br.flower.boot.auth.security.jwt.mount;

import java.util.UUID;

import br.flower.boot.auth.user.Usuario;

public class JwtTokenRefresh extends JwtTokenMountAbstract {
	private static final long serialVersionUID = -7543731891383270931L;

	// 30 Dias
	private static final Long expirationTime = JwtTokenSetting.getExpirationTimeRefreshToken();
	//private Usuario usuario;
	//private String allowrdOriginsCors;

	static final String typTipoToken = JwtTypeTokenEnum.REFRESH.getType();
	
	public JwtTokenRefresh(String idToken, String issDomainNameOrigin, String clientName, UUID sessionId, Usuario usuario) {
		super(idToken, issDomainNameOrigin, clientName, typTipoToken, issDomainNameOrigin, usuario.getUserId(),
				expirationTime, sessionId);

		//this.usuario = usuario;
	}
	public JwtTokenRefresh(String issDomainNameOrigin, String clientName, UUID sessionId, Usuario usuario) {
		super(issDomainNameOrigin, clientName, typTipoToken, issDomainNameOrigin, usuario.getUserId(),
				expirationTime, sessionId);

		//this.usuario = usuario;
	}
}
