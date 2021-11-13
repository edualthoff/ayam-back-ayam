package br.flower.boot.auth.security.username;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.flower.boot.auth.mail.MailService;
import br.flower.boot.auth.mail.ValidyAccountMail;
import br.flower.boot.auth.pessoa.PessoaService;
import br.flower.boot.auth.security.core.AuthenticationSystemManagerUser;
import br.flower.boot.auth.user.Usuario;
import br.flower.boot.auth.user.UsuarioService;
import br.flower.boot.auth.user.role.UserAuthRole;
import br.flower.boot.auth.user.role.UserRoleEnum;
import br.flower.boot.auth.util.CryptoAlgorithmAes;

@Service
public class AccountUsernameServiceImp implements AccountUsernameService{
	private static final long serialVersionUID = -4645022989274315704L;
	
	// chave deve conter 16, 24 ou 32 bytes
	private final String keyCrypt = "heheheh46450229892743112";
	
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
	private String pathLink = "localhost:8080/oatuh/usuario/verificar/";
	
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
		mailService.sendMailTemplate(new ValidyAccountMail(userNews.getPessoa().getEmail(),
				pathLink+mountVerifyAccount(userNews.getUserId().toString())));
		
	}
	
	private String mountVerifyAccount(String userId) {
		Calendar maxTime = Calendar.getInstance();
		maxTime.add(Calendar.MINUTE, 30);
		String userIdAndTime = userId+"&"+maxTime;
		CryptoAlgorithmAes cryptoUtil = new CryptoAlgorithmAes();
		try {
			return cryptoUtil.encrypt(keyCrypt, userIdAndTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean validateEmailAccount(String emailParse) {
		CryptoAlgorithmAes cryptoUtil = new CryptoAlgorithmAes();
		try {
			String userIdAndTime = cryptoUtil.decrypt(keyCrypt, emailParse);
			String dateTime = userIdAndTime.split("&")[1];
			Calendar maxTime = Calendar.getInstance();
			System.out.println("Calender validate email account "+ dateTime+ " "+userIdAndTime);
			if(maxTime.before(maxTime)) {
				Usuario user = usuarioService.getById(UUID.fromString(userIdAndTime.split("&")[0]));
				user.setVerificado(true);
				usuarioService.saveOrUpdate(user);
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


}
