package br.flower.boot.auth.crypt;

import java.time.OffsetDateTime;
import java.util.Base64;

import br.flower.boot.auth.crypt.util.CryptoAlgorithmAes;

public class CryptAesUrlValueValidate implements CryptValueValidate {

	private long timeExpire = 30;
	// chave deve conter 16, 24 ou 32 bytes
	private final String keyCrypt = "heheheh46450229892743112";
	
	public CryptAesUrlValueValidate(long timeExpire) {
		super();
		this.timeExpire = timeExpire;
	}
	
	public CryptAesUrlValueValidate() { }
	
	@Override
	public String encode(String value) throws Exception {
		OffsetDateTime maxTime = OffsetDateTime.now().plusMinutes(timeExpire);
		String userIdAndTime = value+"&"+maxTime;
		CryptoAlgorithmAes cryptoUtil = new CryptoAlgorithmAes();
		return Base64.getUrlEncoder().encodeToString(cryptoUtil.encrypt(keyCrypt, userIdAndTime));
	}

	@Override
	public String decode(String value) throws Exception {
		CryptoAlgorithmAes cryptoUtil = new CryptoAlgorithmAes();
		String userIdAndTime = cryptoUtil.decrypt(keyCrypt, Base64.getUrlDecoder().decode(value));
		String dateTime = userIdAndTime.split("&")[1];
		OffsetDateTime maxTime = OffsetDateTime.now();
		if (maxTime.isBefore(OffsetDateTime.parse(dateTime))) {
			return userIdAndTime.split("&")[0];
		}
		return null;

	}
}
