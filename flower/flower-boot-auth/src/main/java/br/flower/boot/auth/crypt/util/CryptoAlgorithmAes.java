package br.flower.boot.auth.crypt.util;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class CryptoAlgorithmAes {

	public byte[] encrypt(String key, String str) throws Exception {
		SecretKey keySecurity = new SecretKeySpec(key.getBytes(), "AES");
		Cipher ecipher = Cipher.getInstance("AES");
		ecipher.init(Cipher.ENCRYPT_MODE, keySecurity);

		// Encode the string into bytes using utf-8
		byte[] utf8 = str.getBytes("UTF8");

		// Encrypt
		byte[] enc = ecipher.doFinal(utf8);

		// Encode bytes to base64 to get a string
		return enc;
	}

	public String decrypt(String key, byte[] bs) throws Exception {
		SecretKey keySecurity = new SecretKeySpec(key.getBytes(), "AES");
		Cipher dcipher = Cipher.getInstance("AES");
		dcipher.init(Cipher.DECRYPT_MODE, keySecurity);
		
		// Decode base64 to get bytes
		// byte[] dec = bs.getBytes();

		byte[] utf8 = dcipher.doFinal(bs);

		// Decode using utf-8
		return new String(utf8, "UTF8");
	}
}
