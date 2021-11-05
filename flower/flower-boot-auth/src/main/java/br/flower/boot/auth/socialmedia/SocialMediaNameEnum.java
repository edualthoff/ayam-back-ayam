package br.flower.boot.auth.socialmedia;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum SocialMediaNameEnum {

	FACEBOOK("facebook"),
	GOOGLE("google");

	private String name;
	
	SocialMediaNameEnum(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}
	
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static SocialMediaNameEnum fromValue(String value) {
        for (SocialMediaNameEnum e : values()) {
            if (e.name.equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return String.valueOf(name);
    }
}
