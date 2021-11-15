package br.flower.boot.auth.mail.view;

import java.util.HashMap;
import java.util.Map;

import br.flower.boot.auth.mail.MailModel;

public class RecoveryAccountPassMail implements MailTemplateMount {
	private static final long serialVersionUID = -4907534000098474780L;

	private final static String ASSUNTO = "Ayam - Sua Senha";
	private String emailUser;
	private String linkButtonValidyEmail;
	
	
	
	public RecoveryAccountPassMail(String emailUser, String linkButtonRecoveryPass) {
		super();
		this.emailUser = emailUser;
		this.linkButtonValidyEmail = linkButtonRecoveryPass;
	}

	public RecoveryAccountPassMail(String linkButtonRecoveryPass) {
		super();
		this.linkButtonValidyEmail = linkButtonRecoveryPass;
	}
	
	@Override
	public MailModel mount() {
		Map<String, Object> set = new HashMap<>();
		set.put("buttonValidy", TemplateUriPathSetting.getPathPassword()+"?cod="+linkButtonValidyEmail);
		MailModel mailModel = new MailModel(emailUser, ASSUNTO,
				new MailModel.HtmlTemplate("recuperar-pass", set));
		
		return mailModel;
	}

}
