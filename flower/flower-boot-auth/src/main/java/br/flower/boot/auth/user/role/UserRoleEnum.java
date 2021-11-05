package br.flower.boot.auth.user.role;

import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum UserRoleEnum {

	MOTORISTA("motorista", "Usuario que possui carro" ),
	LOCATARIO("locatario", "Usuario que loca carro"),
	LOCADOR("locador", "Usuario que aluga carro"),
	USER("user", "Usuario simples");
	
	private String userRoleName;
	private String descrRole;
	
	UserRoleEnum(String userRoleName, String descrRole) {
		this.userRoleName = userRoleName;
		this.descrRole = descrRole;
	}

	@Enumerated
	@JsonValue
	public String getUserRoleName() {
		return userRoleName;
	}

	public String getDescrRole() {
		return descrRole;
	}
	
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static UserRoleEnum fromValue(String value) {
        for (UserRoleEnum e : values()) {
            if (e.userRoleName.equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }
}
