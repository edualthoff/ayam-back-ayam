package br.flower.boot.auth.security.jwt.mount;

public enum JwtTypeTokenEnum {

	ACCESS("access"),
	REFRESH("refresh");

	private String type;
	
	JwtTypeTokenEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
	
}
