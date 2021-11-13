package br.flower.boot.auth.security.socialmedia;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.flower.boot.auth.pessoa.Pessoa;
import br.flower.boot.auth.security.core.AuthenticationSystemManagerUser;
import br.flower.boot.auth.socialmedia.SocialMediaAuth;
import br.flower.boot.auth.socialmedia.SocialMediaAuth.SocialMediaAuthId;
import br.flower.boot.auth.socialmedia.SocialMediaAuthService;
import br.flower.boot.auth.socialmedia.SocialMediaNameEnum;
import br.flower.boot.auth.upload.FileStorage;
import br.flower.boot.auth.user.Usuario;
import br.flower.boot.auth.user.role.UserAuthRole;
import br.flower.boot.auth.user.role.UserRoleEnum;

@Service
public class AccountLoginSocialMediaImp implements AccountLoginSocialMedia, Serializable {
	private static final long serialVersionUID = 7877562880121481460L;

	private static final Logger log = LoggerFactory.getLogger(AccountLoginSocialMediaImp.class);

	@Autowired
	private SocialMediaService socialMediaService;
	@Autowired
	private SocialMediaAuthService socialMediaAuthService;
	@Autowired
	private AuthenticationSystemManagerUser authenticationManager;
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Valida o usuario vindo Dos meios de acesso - Facebook, google e etc no
	 * Sistema Caso não existir cadastra
	 * 
	 * @param socialMediaNameEnum
	 * @param tokenMediaSocial
	 * @return
	 */
	// Refatorar a parde de autenticacao no sistema com senha "123" - se o usuario
	// redefinir senh por email vai da erro
	@Override
	public Usuario login(SocialMediaNameEnum socialMediaNameEnum, String tokenMediaSocial) {
		SocialMediaModel socialMediaModel = socialMediaService.acessarInfoDoUsuario(socialMediaNameEnum,
				tokenMediaSocial);
		SocialMediaAuth socialMediaAuth = socialMediaAuthService
				.findById(new SocialMediaAuthId(socialMediaNameEnum, socialMediaModel.getId()));
		if (socialMediaAuth == null) {
			socialMediaAuth = this.register(socialMediaModel, socialMediaNameEnum);
		}
		authenticationManager.authenticantionSystem(socialMediaAuth.getUsuario().getUsername(), "123");
		return socialMediaAuth.getUsuario();
	}
	
	@Override
	public SocialMediaAuth register(SocialMediaModel socialMediaModel, SocialMediaNameEnum socialMediaNameEnum) {
		SocialMediaAuth socialMediaAuth = new SocialMediaAuth();
		SocialMediaAuthId socialMediaAuthId = new SocialMediaAuthId(socialMediaNameEnum, socialMediaModel.getId());
		socialMediaAuth.setSocialMediaAuthId(socialMediaAuthId);
		Usuario usuario = new Usuario();
		usuario.setUsername(socialMediaModel.getEmail());
		usuario.setVerificado(true);
		usuario.setDisabled(false);
		usuario.setPassword(passwordEncoder.encode("123"));
		usuario.setUserAuthRole(Arrays.asList(new UserAuthRole(UserRoleEnum.USER, UserRoleEnum.USER.getDescrRole())));
		Pessoa person = new Pessoa();
		person.setEmail(socialMediaModel.getEmail());
		person.setGenero(socialMediaModel.getGenero());
		person.setNome(socialMediaModel.getPrimeiroNome());
		person.setSobrenome(socialMediaModel.getNome());
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		try {
			person.setDateNascimento(formato.parse(socialMediaModel.getNiverData()));
		} catch (ParseException e) {
			person.setDateNascimento(null);
			log.error("Error ao converter a data recebida do UserInfo Media Social, {}", e);
		}
		FileStorage.uploadFile("jpg", "photo" + socialMediaModel.getId() + ".jpg", socialMediaModel.getUrlImagem())
				.ifPresentOrElse(x -> person.setFotoDaPessoa(x), null);
		usuario.setPessoa(person);
		socialMediaAuth.setUsuario(usuario);

		return socialMediaAuthService.save(socialMediaAuth);
	}

}
