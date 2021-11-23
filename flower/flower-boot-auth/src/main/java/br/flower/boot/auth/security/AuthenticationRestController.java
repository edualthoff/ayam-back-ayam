package br.flower.boot.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.flower.boot.auth.security.core.ResponseAuthDto;
import br.flower.boot.auth.security.core.provider.AuthenticationProvider;
import br.flower.boot.auth.security.core.provider.AuthenticationProviderFacade;
import br.flower.boot.auth.security.core.provider.GrantTypeNameEnum;
import br.flower.boot.auth.session.LogoutSession;
import br.flower.boot.auth.socialmedia.SocialMediaNameEnum;

@RestController
@RequestMapping(path = "/oauth")
public class AuthenticationRestController {
	
	@Autowired private AuthenticationProviderFacade authenticationProviderFacade;
	@Autowired private LogoutSession logoutSession;
	
	@PostMapping()
	public ResponseAuthDto createLoginAuthenticationUsernameAndMediaSocialToken(
			@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "password", required = false) String password,
			@RequestParam(name = "token_acess", required = false) String tokenAcess,
			@RequestParam(name = "token_origin", required = false) SocialMediaNameEnum tokenOrigin,
			@RequestParam(name = "grant_type", required = true) GrantTypeNameEnum grantType) {
		System.out.println("test creat ");
		ResponseAuthDto resp =  new ResponseAuthDto();		
		switch (grantType) {
			case AUTHORIZATIONCODE:
				//System.out.println("Account "+username+" "+grantType+" "+origemAcess);
				resp = authenticationProviderFacade.grantType(new AuthenticationProvider(grantType, username, password));
				break;
			case AUTHORIZATIONMEDIASOCIAL:
				//System.out.println("Account "+tokenAcess+" "+tokenOrigin+" "+grantType+" "+origemAcess);
				resp = authenticationProviderFacade.grantType(new AuthenticationProvider(tokenAcess, tokenOrigin, grantType));
				break;
			default:
				break;
		}
		return resp;
	}


	
	@PostMapping("/refresh")
	public ResponseAuthDto refreshToken() {
		return null;
	}

	@PostMapping("/logout")
	public void logoutSystemToken(@RequestParam(name = "rf") String logout) {
		this.logoutSession.removeToken(logout);
	}
	
	/*@PostMapping("/{origem}")
	public ResponseAuthDto createLoginAuthenticationMediaSocialToken(
			@RequestParam(name = "token_acess", required = true) String tokenAcess,
			@PathParam("origem") String tokenOrigem,
			//@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "grant_type", required = true) String grantType) {
		ResponseAuthDto resp =  new ResponseAuthDto();
		System.out.println("Account "+tokenOrigem+" "+grantType);
		return resp;
	}*/
}

