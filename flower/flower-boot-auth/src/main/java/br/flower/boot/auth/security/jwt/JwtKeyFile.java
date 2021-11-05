package br.flower.boot.auth.security.jwt;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.springframework.stereotype.Component;

import br.flower.boot.auth.util.KeyRsaApiConvert;

@Component
public class JwtKeyFile extends KeyRsaApiConvert{

	private static PublicKey publicKey;
	private static PrivateKey privateKey;
	
	public JwtKeyFile() {
		super("jwt/public_key.der", "jwt/private_key.der");
		ini();
	}

	
	public static PublicKey publicKey() throws Exception {
		return publicKey;
	}
	
	public static PrivateKey privateKey() throws Exception {
		return privateKey;
	}
	
	
	private void ini() {
		try {
			JwtKeyFile.publicKey = super.getPublicKey();
			JwtKeyFile.privateKey = super.getPrivateKey();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
