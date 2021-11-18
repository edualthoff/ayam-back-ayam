package br.flower.boot.auth.security;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.flower.boot.auth.security.core.ResponseAuthDto;
import br.flower.boot.auth.security.core.provider.AuthenticationProvider;
import br.flower.boot.auth.security.core.provider.AuthenticationProviderFacade;
import br.flower.boot.auth.security.core.provider.GrantTypeNameEnum;
import br.flower.boot.auth.security.username.AccountUsernameService;
import br.flower.boot.auth.security.username.UsuarioMensagemDto;
import br.flower.boot.auth.socialmedia.SocialMediaNameEnum;
import br.flower.boot.auth.user.Usuario;
import br.flower.boot.auth.user.role.UserRoleEnum;

@RestController
@RequestMapping(path = "/oauth")
public class AuthenticationRestController {
	
	@Autowired private AuthenticationProviderFacade authenticationProviderFacade;
	@Autowired private AccountUsernameService accountUsernameService;
	
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

	/**
	 * Cadastro de usuario - Caso o {@value tipo} for null cadastra usuario Padrao
	 * @param usuario
	 * @param tipoUser
	 * @return
	 */
	@PostMapping("/usuario")
	public UsuarioMensagemDto creatUser(@Valid @RequestBody Usuario usuario, 
			@RequestParam(value = "tipo", required = false) UserRoleEnum tipoUser ) {
		return this.accountUsernameService.registerUser(usuario, tipoUser);		
	}
	
	/**
	 * Atualiza o registro do usuario
	 * @param usuario
	 * @return
	 */
	@PutMapping("/usuario")
	public UsuarioMensagemDto atualizarUser(@Valid @RequestBody Usuario usuario) {
		return this.accountUsernameService.registerUser(usuario);		
	}
	
	/**
	 * Confirma o cadastro do usuario - atravez do link enviado para o email
	 * @param value
	 * @return
	 */
	@PutMapping("/usuario/verificar/{value}")
	public Boolean verificarUsuario(@PathVariable(name = "value") String value) {
		return this.accountUsernameService.validateEmailAccount(value);
	}

	/**
	 * Altera a senha do usuario passando o hash de verificacao mais a nova senha informado pelo usuario
	 * @param cod
	 * @param value
	 * @return
	 */
	@PatchMapping("/usuario/recovery")
	public UsuarioMensagemDto recoveryVerifyPass(@RequestParam(name = "cod") String cod,
			@RequestParam(name = "value") String value) {
		return this.accountUsernameService.recoveryPass(cod, value);

	}
	
	/**
	 * Envia o email de recuperar senha passando o username como parametro
	 * @param value
	 * @return
	 */
	@PostMapping("/usuario/{value}/recovery")
	public UsuarioMensagemDto recoveryPass(@PathVariable(name = "value") String value) {
		return 	this.accountUsernameService.recoveryPass(value);
	}
	
	@PostMapping("/refresh")
	public ResponseAuthDto refreshToken() {
		return null;
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

