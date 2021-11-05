package br.flower.boot.jwt.core;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.flower.boot.jwt.model.JwtTokenDto;
import br.flower.boot.jwt.util.JwtTokenUtil;



@Component
public class TokenAuthenticationProvider implements TokeneAuthetntication {
	private static final long serialVersionUID = -2001850756896211365L;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public boolean validateToken(String token) {
		return jwtTokenUtil.validateTokenAndExpire(token);
	}

	@Override
	public void authenticationSytemToken(String token) {
		JwtTokenDto dto = jwtTokenUtil.getDeserializeClaimsToken(token);
		final Collection<? extends GrantedAuthority> authorities = dto.getRoles().parallelStream()
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		CustomUserDetails customUserDetails = new CustomUserDetails(dto.getEmail(), "", authorities);
		
		this.authenticantionSystem(customUserDetails, "", authorities);		
	}
	

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
