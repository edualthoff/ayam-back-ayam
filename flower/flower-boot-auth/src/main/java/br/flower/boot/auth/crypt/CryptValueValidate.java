package br.flower.boot.auth.crypt;

public interface CryptValueValidate {

	
	String encode(String value) throws Exception;
	
	String decode(String value) throws Exception;
	
}
