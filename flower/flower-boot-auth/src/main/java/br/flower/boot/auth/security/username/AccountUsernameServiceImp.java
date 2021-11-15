package br.flower.boot.auth.security.username;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.flower.boot.auth.crypt.CryptAesUrlValueValidate;
import br.flower.boot.auth.crypt.CryptValueValidate;
import br.flower.boot.auth.mail.MailService;
import br.flower.boot.auth.mail.view.MailTemplateMount;
import br.flower.boot.auth.mail.view.RecoveryAccountPassMail;
import br.flower.boot.auth.mail.view.ValidyAccountMail;
import br.flower.boot.auth.pessoa.PessoaService;
import br.flower.boot.auth.security.core.AuthenticationSystemManagerUser;
import br.flower.boot.auth.user.Usuario;
import br.flower.boot.auth.user.UsuarioService;
import br.flower.boot.auth.user.role.UserAuthRole;
import br.flower.boot.auth.user.role.UserRoleEnum;
import br.flower.boot.exception.config.ApiMessageSourceError;
import br.flower.boot.exception.type.client.ApiNotFoundException;

@Service
public class AccountUsernameServiceImp implements AccountUsernameService{
	private static final long serialVersionUID = 1126311965927128544L;

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private PessoaService pessoaService;
	@Autowired 
	private AuthenticationSystemManagerUser authenticationManager;
	@Autowired 
	private MailService mailService;
	@Value("${flower.usuario.validar.email-link:null}")
	private String pathLink;
	
	@Override
	public Usuario login(String username, String password) {
		authenticationManager.authenticantionSystem(username, password);
		return usuarioService.getByEmail(username);
	}
	
	@Override
	public UsuarioMensagemDto registerUser(Usuario user, UserRoleEnum userRoleEnum) {
		usuarioService.verifyUsername(user.getUsername());
		pessoaService.verifyEmail(user.getPessoa().getEmail());
		List<UserAuthRole> roles = new ArrayList<>();
		roles.add(new UserAuthRole(UserRoleEnum.USER, UserRoleEnum.USER.getDescrRole()));
		if(userRoleEnum != null) {
			new UserAuthRole(userRoleEnum, userRoleEnum.getDescrRole());
		}
		user.setUserAuthRole(roles);
		user.setDisabled(false);
		user.setVerificado(false);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Usuario userNews = usuarioService.saveOrUpdate(user);
		this.sendEmailAccountVerify(userNews);
		return new UsuarioMensagemDto(user.getUsername(), "Criado com suscesso");
	}
	
	@Override
	public UsuarioMensagemDto registerUser( Usuario user) {
		return null;
	}
	
	@Override
	public void updateUser(String idUser, Usuario user) {
		if (user.getUserId().compareTo(UUID.fromString(idUser)) == 0) {
			usuarioService.saveOrUpdate(user);
			// return new UsuarioMensagemDto("Usuario update", "Atualizado com sucesso");
		}
		throw new ApiNotFoundException(ApiMessageSourceError.toMessage("not_found.error.user.msg"));
	}
	
	/*
	 * Seta o usuario como verificado caso o hash enviado seja valido
	 */
	public boolean validateEmailAccount(String emailParse) {
		String code = this.valueDecode(emailParse);
		if(code != null) {
			Usuario user = usuarioService.getById(UUID.fromString(code));
			user.setVerificado(true);
			usuarioService.saveOrUpdate(user);
			return true;			
		}
		return false;
	}
	
	/*
	 * Monta o templa de Confirmacao de conta
	 */
	private void sendEmailAccountVerify(Usuario userNews) {
		String value = this.valueEncode(userNews.getUserId().toString());
		this.sendEmailEncode(new ValidyAccountMail(userNews.getPessoa().getEmail(), value));
	}
	
	/*
	 * Codifica em base64 um hash de envio para confirmacao passando o parametro de verificacao
	 */
	private String valueEncode(String valueCode) {
		CryptValueValidate crypt = new CryptAesUrlValueValidate();
		String value = null;
		try {
			value = crypt.encode(valueCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	/*
	 * Decodifica o hash recebido e retorna o parametro para confirmacao
	 */
	private String valueDecode(String valueCode) {
		CryptValueValidate crypt = new CryptAesUrlValueValidate();
		String value = null;
		try {
			value = crypt.decode(valueCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	/*
	 * Enviar o email
	 */
	private void sendEmailEncode(MailTemplateMount mailTemplate) {	
		mailService.sendMailTemplate(mailTemplate);	
	}
	

	/*
	 * Atualiza a senha do usuario passando o hash para de decode e a nova senha 
	 */
	@Override
	public UsuarioMensagemDto recoveryPass(String cod, String value) {
		String email = this.valueDecode(cod);
		if(email != null ) {
			Usuario user = this.usuarioService.getByEmail(email);
			user.setPassword(this.passwordEncoder.encode(value));
			this.usuarioService.saveOrUpdate(user);
			return new UsuarioMensagemDto(email, "Atualizado com sucesso");
		}
		throw new ApiNotFoundException(ApiMessageSourceError.toMessage("not_found.error.user.msg"));
	}

	/*
	 * Enviar um email de recuperacao de senha
	 */
	@Override
	public UsuarioMensagemDto recoveryPass(String userEmail) {
		if (this.usuarioService.existUsername(userEmail)) {
			String value = this.valueEncode(userEmail);
			this.sendEmailEncode(new RecoveryAccountPassMail(userEmail, value));
			return new UsuarioMensagemDto(userEmail, "Enviado com sucesso");
		}
		throw new ApiNotFoundException(ApiMessageSourceError.toMessage("not_found.error.user.msg"));
	}
}
