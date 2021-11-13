package br.flower.boot.auth.security.jwt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import br.flower.boot.auth.user.Usuario;

public class JwtTokenAuthentic extends JwtTokenMountAbstract {
	private static final long serialVersionUID = 2635530871450708442L;

	protected final static String CLAIM_KEY_USERNAME = "email";
	protected final static String CLAIM_KEY_VERIFIED = "email_verified";
	protected final static String CLAIM_KEY_NAME = "name";
	protected final static String CLAIM_KEY_GIVEN_NAME= "given_name";
	protected final static String CLAIM_KEY_ROLE_ACESS = "roles";

	private final static Long expirationTime = 600000L;
	private Usuario usuario;
	//private String allowrdOriginsCors;
	
	static final String typTipoToken = "Bearer";
	static final String aud = "account";
	
	public JwtTokenAuthentic(String issDomainNameOrigin, String clientName, UUID sessionId, Usuario user) {
		super(issDomainNameOrigin, clientName, typTipoToken, aud, user.getPessoa().getPessoaId(), expirationTime, sessionId);
		this.usuario = user;
	}
	
	@Override
	public Map<String, Object> implementsCustonsClaims() {
		Map<String, Object> claims = new HashMap<>();
		/** Claims util para a api */
		claims.put(CLAIM_KEY_USERNAME, usuario.getUsername());
		claims.put(CLAIM_KEY_NAME, usuario.getPessoa().getNome());
		claims.put(CLAIM_KEY_GIVEN_NAME, usuario.getPessoa().getSobrenome());
		claims.put(CLAIM_KEY_VERIFIED, usuario.isVerificado());
		// Verificar - Corrigir processo role
		claims.put(CLAIM_KEY_ROLE_ACESS, mapearRoles());
		
		return claims;
	}

	private List<String> mapearRoles(){
		List<String> roles = new ArrayList<String>();
		usuario.getUserAuthRole().forEach(x -> roles.add(x.getRoleName().getUserRoleName()));
		return roles;
		
	}
}
