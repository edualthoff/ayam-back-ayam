package br.aym.base.produto.caracteristica;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;


public enum CaracteristicaProdutoEnum {

	@JsonProperty("vibracao")
	VIBRACAO("vibracao"),
	@JsonProperty("elemento")
	ELEMENTO("elemento");

	private String tipo;

	
	CaracteristicaProdutoEnum(String tipo) {
		// TODO Auto-generated constructor stub
		this.tipo = tipo;
	}

	@JsonValue()
	public String getTipo() {
		return tipo;
	}

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static CaracteristicaProdutoEnum fromValue(String value) {
        for (CaracteristicaProdutoEnum e : values()) {
            if (e.tipo.equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }
}
