package br.flower.boot.auth.session;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.flower.boot.auth.security.jwt.JwtTokenDto;
import br.flower.boot.auth.security.jwt.JwtTokenUtil;
import br.flower.boot.auth.session.client.ClientSessionDetailsService;

@Service
public class LogoutSessionImp implements LogoutSession, Serializable {
	private static final long serialVersionUID = -7945721982972412391L;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private ClientSessionDetailsService clientSessionDetailsService;
	
	@Override
	public void removeToken(String token) {
		JwtTokenDto jwt = this.jwtTokenUtil.getDeserializeClaimsToken(token);		
		this.clientSessionDetailsService.removeSession(UUID.fromString(jwt.getSessionState()));;
	}

}
