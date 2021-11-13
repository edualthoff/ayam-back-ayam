package br.flower.boot.auth.security.username;

public class UsuarioMensagemDto {

	private String titulo;
	private String assunto;
	
	public UsuarioMensagemDto(String titulo, String assunto) {
		this.titulo = titulo;
		this.assunto = assunto;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getAssunto() {
		return assunto;
	}
	
	
}
