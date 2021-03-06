package br.flower.boot.auth.user.role;

import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum UserRoleEnum {

	ADMINISTRADOR("administrador", "Adminitrador do sistema"),
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
