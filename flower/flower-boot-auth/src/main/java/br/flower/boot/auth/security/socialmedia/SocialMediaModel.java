package br.flower.boot.auth.security.socialmedia;

import lombok.Data;

@Data
public class SocialMediaModel {

	private String id;
	private String email;
	private String nome;
	private String primeiroNome;
	private String ultimoNome;
	private String urlImagem;
	private String niverData;
	private String mediaSocial;
	private String heightImagem;
	private String widthImage;
	private String genero;
	
	public SocialMediaModel() {

	}

}
