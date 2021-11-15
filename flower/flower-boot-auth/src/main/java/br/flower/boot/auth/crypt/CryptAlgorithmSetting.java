package br.flower.boot.auth.crypt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CryptAlgorithmSetting {

	private static Long timeExpire;
	
	// chave deve conter 16, 24 ou 32 bytes
	private static String keyCrypt;

	
	public CryptAlgorithmSetting(@Value("${flower.settings.crypt.validate.expire:0}") String timeExpire, 
			@Value("${flower.settings.crypt.validate.keyCrypt:heheheh46450229892743112}") String keyCrypt) {
		super();
		CryptAlgorithmSetting.timeExpire = Long.parseLong(timeExpire); 
		CryptAlgorithmSetting.keyCrypt = keyCrypt;
	}

	public static Long getTimeExpire() {
		return timeExpire;
	}

	public static String getKeyCrypt() {
		return keyCrypt;
	}
}
