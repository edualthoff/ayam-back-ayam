package br.flower.boot.auth.mail;

import java.util.HashMap;
import java.util.Map;

import br.flower.boot.auth.mail.model.MailModel;


/**
 * Implementa a @imterface {@link MailTemplateMount} 
 * 
 * Enviar o email de confirmação de conta
 * 
 * @author edu
 *
 */
public class ValidyAccountMail implements MailTemplateMount {
	private static final long serialVersionUID = -4907534000098474780L;

	private final static String assunto = "Validar conta Carro Lucrativo";
	private String emailUser;
	private String linkButtonValidyEmail;
	
	
	
	public ValidyAccountMail(String emailUser, String linkButtonValidyEmail) {
		super();
		this.emailUser = emailUser;
		this.linkButtonValidyEmail = linkButtonValidyEmail;
	}

	@Override
	public MailModel mount() {
		Map<String, Object> set = new HashMap<>();
		set.put("buttonValidy", linkButtonValidyEmail);
		MailModel mailModel = new MailModel(emailUser, assunto,
				new MailModel.HtmlTemplate("ativar-conta", set));
		
		return mailModel;
	}

}
