package br.flower.boot.auth.security.core.provider;

import java.io.Serializable;

import br.flower.boot.auth.socialmedia.SocialMediaNameEnum;
import lombok.Data;

@Data
public class AuthenticationProvider implements Serializable {
	private static final long serialVersionUID = 5754854156979333963L;

	private String username;
	private String password;
	private GrantTypeNameEnum grantType;
	private String tokenAcess;
	private SocialMediaNameEnum tokenOrigem;
	
	public AuthenticationProvider(GrantTypeNameEnum grantType, String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.grantType = grantType;
	}

	public AuthenticationProvider(String tokenAcess, SocialMediaNameEnum tokenOrigem, GrantTypeNameEnum grantType) {
		super();
		this.grantType = grantType;
		this.tokenOrigem = tokenOrigem;
		this.tokenAcess = tokenAcess;
	}
	

}
