package br.flower.boot.auth.security.jwt.mount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import br.flower.boot.auth.user.Usuario;

/**
 * Mapeia as Clains do token JWT
 * @author edu
 *
 */
public class JwtTokenAuthentic extends JwtTokenMountAbstract {
	private static final long serialVersionUID = 7724276352710804229L;

	protected final static String CLAIM_KEY_USERNAME = "email";
	protected final static String CLAIM_KEY_VERIFIED = "email_verified";
	protected final static String CLAIM_KEY_NAME = "name";
	protected final static String CLAIM_KEY_GIVEN_NAME= "given_name";
	protected final static String CLAIM_KEY_ROLE_ACESS = "roles";

	private final static Long expirationTime = JwtTokenSetting.getExpirationTimeToken();
	
	private Usuario usuario;
	//private String allowrdOriginsCors;
	
	static final String typTipoToken = JwtTypeTokenEnum.ACCESS.getType();
	static final String aud = "account";
	
	public JwtTokenAuthentic(String issDomainNameOrigin, String clientName, UUID sessionId, Usuario user) {
		super(issDomainNameOrigin, clientName, typTipoToken, aud, user.getUserId(), expirationTime, sessionId);
		System.out.println("user "+user.getUserId());
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
