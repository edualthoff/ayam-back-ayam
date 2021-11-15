package br.flower.boot.auth.mail.view;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class TemplateUriPathSetting {

	//@Value("${jwt.token.expiration-time:600000L}")
	private static String pathAccount;
	
	// 30 Dias
	//@Value("${jwt.token.refresh.expiration-time:2592000000L}")
	private static String pathPassword;

	public static String getPathAccount() {
		return pathAccount;
	}

	public static String getPathPassword() {
		return pathPassword;
	}

	TemplateUriPathSetting(@Value("${flower.usuario.template-uri.email-path:null}") String pathAccount, 
			@Value("${flower.usuario.template-uri.pass-path:null}") String pathPassword1) {
		TemplateUriPathSetting.pathAccount = pathAccount;
		TemplateUriPathSetting.pathPassword = pathPassword1;
	}
	

}
