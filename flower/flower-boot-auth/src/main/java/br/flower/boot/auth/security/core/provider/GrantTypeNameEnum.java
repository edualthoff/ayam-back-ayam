package br.flower.boot.auth.security.core.provider;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum GrantTypeNameEnum {

	AUTHORIZATIONMEDIASOCIAL("authorization_mediasocial"),
	AUTHORIZATIONCODE("authorization_code");
	
	private String name;
	

	GrantTypeNameEnum(String name) {
		this.name = name;
	}

	@Enumerated(EnumType.STRING)
	public String getName() {
		return name;
	}
	
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static GrantTypeNameEnum fromValue(String value) {
        for (GrantTypeNameEnum e : values()) {
            if (e.name.equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }
}
