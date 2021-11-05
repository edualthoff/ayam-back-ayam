package br.flower.boot.auth.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;


/**
 * Ler de um Arquivo ou link e gerar a chave RSA publica e privada 
 * passando como o parametro o local ou o link da chave
 * @author edu
 *
 */
public class KeyRsaApiConvert {
	private static final Logger log = LoggerFactory.getLogger(KeyRsaApiConvert.class);

	private String publicKey;
	private String privateKey;
	
	
	
	public KeyRsaApiConvert(String publicKey, String privateKey) {
		super();
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}
	

	public KeyRsaApiConvert(String publicKey) {
		super();
		this.publicKey = publicKey;
	}


	public PublicKey getPublicKey() throws InvalidKeyException, Exception {
		X509EncodedKeySpec spec = new X509EncodedKeySpec(loadFile(publicKey));
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(spec);
	}

	public PrivateKey getPrivateKey() throws InvalidKeyException, Exception {
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(loadFile(privateKey));
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}

	private byte[] loadFile(String fileName) {
		try {
			
			return Files.readAllBytes(Paths.get(ResourceUtils.getFile("classpath:"+fileName).getPath()));
		} catch (IOException e) {
			log.error("Erro ao ler o arquivo da chave {}", e);
		}
		return null;
	}
}
