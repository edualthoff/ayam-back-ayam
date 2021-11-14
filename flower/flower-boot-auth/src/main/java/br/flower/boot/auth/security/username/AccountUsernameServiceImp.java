package br.flower.boot.auth.security.username;

import java.net.URLEncoder;
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
import br.flower.boot.auth.mail.ValidyAccountMail;
import br.flower.boot.auth.pessoa.PessoaService;
import br.flower.boot.auth.security.core.AuthenticationSystemManagerUser;
import br.flower.boot.auth.user.Usuario;
import br.flower.boot.auth.user.UsuarioService;
import br.flower.boot.auth.user.role.UserAuthRole;
import br.flower.boot.auth.user.role.UserRoleEnum;

@Service
public class AccountUsernameServiceImp implements AccountUsernameService{
	private static final long serialVersionUID = -4645022989274315704L;
	

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
	@Value("${usuario.validar.email-link:null}")
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
	public UsuarioMensagemDto registerUser(Usuario user) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void sendEmailAccountVerify(Usuario userNews) {
		CryptValueValidate crypt = new CryptAesUrlValueValidate();
		String value = null;
		try {
			value = crypt.encode(userNews.getUserId().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}		
		mailService.sendMailTemplate(new ValidyAccountMail(userNews.getPessoa().getEmail(),
				pathLink+value));		
	}
	
		
	public boolean validateEmailAccount(String emailParse) {
		CryptValueValidate crypt = new CryptAesUrlValueValidate();
		String value = null;
		try {
			value = crypt.decode(emailParse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(value != null) {
			Usuario user = usuarioService.getById(UUID.fromString(value));
			user.setVerificado(true);
			usuarioService.saveOrUpdate(user);
			return true;			
		}
		return false;
	}

}
