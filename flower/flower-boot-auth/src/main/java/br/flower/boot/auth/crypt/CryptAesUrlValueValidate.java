package br.flower.boot.auth.crypt;

import java.time.OffsetDateTime;
import java.util.Base64;

import br.flower.boot.auth.crypt.util.CryptoAlgorithmAes;

public class CryptAesUrlValueValidate implements CryptValueValidate {

	private Long timeExpire;
	// chave deve conter 16, 24 ou 32 bytes
	private final String keyCrypt;
	
	public CryptAesUrlValueValidate(Long timeExpire) {
		super();
		this.timeExpire = timeExpire;
		this.keyCrypt = CryptAlgorithmSetting.getKeyCrypt();
	}
	
	public CryptAesUrlValueValidate() { 
		this.timeExpire = CryptAlgorithmSetting.getTimeExpire();
		this.keyCrypt = CryptAlgorithmSetting.getKeyCrypt();
	}
	
	@Override
	public String encode(String value) throws Exception {
		CryptoAlgorithmAes cryptoUtil = new CryptoAlgorithmAes();
		String userIdAndTime;
		if(timeExpire == 0L) {
			userIdAndTime = value+"&"+timeExpire;
		} else {
			OffsetDateTime maxTime = OffsetDateTime.now().plusMinutes(timeExpire);
			userIdAndTime = value+"&"+maxTime;
		}
		return Base64.getUrlEncoder().encodeToString(cryptoUtil.encrypt(keyCrypt, userIdAndTime));
	}

	@Override
	public String decode(String value) throws Exception {
		CryptoAlgorithmAes cryptoUtil = new CryptoAlgorithmAes();
		String userIdAndTime = cryptoUtil.decrypt(keyCrypt, Base64.getUrlDecoder().decode(value));
		if(timeExpire == 0L) {
			return userIdAndTime.split("&")[0];
		}
		String dateTime = userIdAndTime.split("&")[1];
		OffsetDateTime maxTime = OffsetDateTime.now();
		if (maxTime.isBefore(OffsetDateTime.parse(dateTime))) {
			return userIdAndTime.split("&")[0];
		}
		return null;

	}
}
