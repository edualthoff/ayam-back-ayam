package br.flower.boot.auth.security.jwt;

import java.util.UUID;

import br.flower.boot.auth.user.Usuario;

public class JwtTokenRefresh extends JwtTokenMountAbstract {
	private static final long serialVersionUID = -7543731891383270931L;

	// 30 Dias
	private final static Long expirationTime = 2592000000L;
	//private Usuario usuario;
	//private String allowrdOriginsCors;
	
	static final String typTipoToken = "Refresh";
	
	public JwtTokenRefresh(String idToken, String issDomainNameOrigin, String clientName, UUID sessionId, Usuario usuario) {
		super(idToken, issDomainNameOrigin, clientName, typTipoToken, issDomainNameOrigin, usuario.getPessoa().getPessoaId(), expirationTime, sessionId);

		//this.usuario = usuario;
	}
	public JwtTokenRefresh(String issDomainNameOrigin, String clientName, UUID sessionId, Usuario usuario) {
		super(issDomainNameOrigin, clientName, typTipoToken, issDomainNameOrigin, usuario.getPessoa().getPessoaId(), expirationTime, sessionId);

		//this.usuario = usuario;
	}
}
