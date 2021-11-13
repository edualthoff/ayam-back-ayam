package br.flower.boot.auth.security.core.provider;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import br.flower.boot.auth.security.core.AuthenticationSystemManagerUser;
import br.flower.boot.auth.security.core.CustomUserDetails;
import br.flower.boot.auth.security.jwt.JwtTokenDto;
import br.flower.boot.auth.security.jwt.JwtTokenUtil;

@Component
public class TokenAuthenticationProvider implements TokeneAuthentication {
	private static final long serialVersionUID = -2001850756896211365L;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private AuthenticationSystemManagerUser authenticationSystemManagerUser;
	
	@Override
	public boolean validateToken(String token) {
		return jwtTokenUtil.validateToken(token);
	}

	@Override
	public void authenticationSytemToken(String token) {
		JwtTokenDto dto = jwtTokenUtil.getDeserializeClaimsToken(token);
		final Collection<? extends GrantedAuthority> authorities = dto.getRoles().parallelStream()
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		CustomUserDetails customUserDetails = new CustomUserDetails(dto.getEmail(), "", authorities);
		
		authenticationSystemManagerUser.authenticantionSystem(customUserDetails, "", authorities);		
	}
	


}
