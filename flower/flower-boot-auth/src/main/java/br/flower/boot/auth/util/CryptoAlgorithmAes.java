package br.flower.boot.auth.util;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CryptoAlgorithmAes {

	public String encrypt(String key, String str) throws Exception {
		SecretKey keySecurity = new SecretKeySpec(key.getBytes(), "AES");
		Cipher ecipher = Cipher.getInstance("AES");
		ecipher.init(Cipher.ENCRYPT_MODE, keySecurity);

		// Encode the string into bytes using utf-8
		byte[] utf8 = str.getBytes("UTF8");

		// Encrypt
		byte[] enc = ecipher.doFinal(utf8);

		// Encode bytes to base64 to get a string
		return Base64.getEncoder().encodeToString(enc);
	}

	public String decrypt(String key, String str) throws Exception {
		SecretKey keySecurity = new SecretKeySpec(key.getBytes(), "AES");
		Cipher dcipher = Cipher.getInstance("AES");
		dcipher.init(Cipher.DECRYPT_MODE, keySecurity);
		
		// Decode base64 to get bytes
		byte[] dec = Base64.getDecoder().decode(str);

		byte[] utf8 = dcipher.doFinal(dec);

		// Decode using utf-8
		return new String(utf8, "UTF8");
	}
}
