package br.flower.boot.auth.security.username;

import java.util.Arrays;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.flower.boot.auth.mail.MailService;
import br.flower.boot.auth.mail.ValidyAccountMail;
import br.flower.boot.auth.security.core.AuthenticationSystemManagerUser;
import br.flower.boot.auth.user.Usuario;
import br.flower.boot.auth.user.UsuarioService;
import br.flower.boot.auth.user.role.UserAuthRole;
import br.flower.boot.auth.user.role.UserRoleEnum;
import br.flower.boot.auth.util.CryptoUtil;

@Service
public class AccountUsername implements AccountUsernameService{
	private static final long serialVersionUID = -4645022989274315704L;

	private final String keyCrypt = "heheheh";
	
	@Autowired
	private UsuarioService usuarioService;
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
	public Usuario registerUser(Usuario user, UserRoleEnum userRoleEnum) {
		usuarioService.verifyUsername(user.getUsername());
		user.setUserAuthRole(Arrays.asList(
				new UserAuthRole(UserRoleEnum.USER, UserRoleEnum.USER.getDescrRole()),
				new UserAuthRole(userRoleEnum, userRoleEnum.getDescrRole())
				));
		user.setDisabled(false);
		user.setVerificado(false);
		Usuario userNews = usuarioService.saveOrUpdate(user);
		this.sendEmailAccountVerify(userNews);
		return userNews;
	}
	
	private void sendEmailAccountVerify(Usuario userNews) {
		mailService.sendMailTemplate(new ValidyAccountMail(userNews.getPerson().getEmail(),
				pathLink+mountVerifyAccount(userNews.getUserId().toString())));
		
	}
	
	private String mountVerifyAccount(String userId) {
		Calendar maxTime = Calendar.getInstance();
		maxTime.add(Calendar.MINUTE, 30);
		String userIdAndTime = userId+"&"+maxTime;
		CryptoUtil cryptoUtil = new CryptoUtil();
		try {
			return cryptoUtil.encrypt(keyCrypt, userIdAndTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean validateEmailAccount(String emailParse) {
		CryptoUtil cryptoUtil = new CryptoUtil();
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
