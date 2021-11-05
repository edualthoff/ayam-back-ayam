package br.flower.boot.auth.security.core;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSystemManager implements AuthenticationSystemManagerUser {
	private static final long serialVersionUID = 6716659934344958808L;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public Authentication authenticantionSystem(String username, String password) {
		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		setSecurityContext(authentication);
		return authentication;
	}

	@Override
	public Authentication authenticantionSystem(CustomUserDetails customUserDetails, String password,
			Collection<? extends GrantedAuthority> authorities) {
		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(customUserDetails, password, authorities));
		setSecurityContext(authentication);
		return authentication;
	}
	
	private void setSecurityContext(Authentication auth) {
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

}
