package br.flower.boot.jwt.util;

import java.security.PrivateKey;
import java.security.PublicKey;


public class JwtKeyFile extends KeyRsaApiConvert implements JwtPublicKey {

	private  PublicKey publicKey;
	private  PrivateKey privateKey;
	
	public JwtKeyFile() {
		super("jwt/public_key.der", "jwt/private_key.der");
		ini();
	}
	
	public JwtKeyFile(String publicKey, String privateKey) {
		super(publicKey, privateKey);
		ini();
	}
	
	public JwtKeyFile(String publicKey) {
		super(publicKey, "");
		ini();
	}	
	
	@Override
	public PublicKey publicKey() {
		return publicKey;
	}
	
	public PrivateKey privateKey() {
		return privateKey;
	}
	
	private void ini() {
		try {
			publicKey = super.getPublicKey();
			privateKey = super.getPrivateKey();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
