package br.aym.base.informativo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum TipoMensagemEnum {

	NOTICIA("noticia"),
	DICA("dica");

	private String tipo;
	
	TipoMensagemEnum(String tipo) {
		this.tipo = tipo;
	}

	@JsonValue()
	public String getTipo() {
		return tipo;
	}

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TipoMensagemEnum fromValue(String value) {
        for (TipoMensagemEnum e : values()) {
            if (e.tipo.equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }
	
}
