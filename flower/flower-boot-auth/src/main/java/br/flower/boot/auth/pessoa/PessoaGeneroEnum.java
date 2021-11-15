package br.flower.boot.auth.pessoa;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum PessoaGeneroEnum {

	M("homem", "m"),
	F("mulher", "f");
	
	private String genero;
	private String generoSigla;

	PessoaGeneroEnum(String genero, String generoSigla) {
		this.genero = genero;
		this.generoSigla = generoSigla;
	}

	@JsonValue
	public String getGenero() {
		return genero;
	}
	
	public String getGeneroSigla() {
		return generoSigla;
	}
	
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static PessoaGeneroEnum fromValue(String value) {
    	return fromGeneroValue(value);
    }
    
    public static PessoaGeneroEnum fromGeneroValue(String value) {
    	String newValue = value.toLowerCase();
    	switch (newValue) {
		case "homem":
			return PessoaGeneroEnum.M;
		case "mulher":
			return PessoaGeneroEnum.F;
		case "m":
			return PessoaGeneroEnum.M;
		case "f":
			return PessoaGeneroEnum.F;
		default:
			return null;
		}
    }
}