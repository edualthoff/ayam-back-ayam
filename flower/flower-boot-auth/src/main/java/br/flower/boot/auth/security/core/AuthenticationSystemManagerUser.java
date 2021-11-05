package br.flower.boot.auth.security.core;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public interface AuthenticationSystemManagerUser extends Serializable {

	Authentication authenticantionSystem(String username, String password);
	Authentication authenticantionSystem(CustomUserDetails username, String password, Collection<? extends GrantedAuthority> authorities);
}
