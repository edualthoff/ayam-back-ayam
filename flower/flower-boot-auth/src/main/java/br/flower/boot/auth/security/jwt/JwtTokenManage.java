package br.flower.boot.auth.security.jwt;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.flower.boot.auth.client.SecurityClientResourceContext;
import br.flower.boot.auth.security.core.ResponseAuthDto;
import br.flower.boot.auth.security.jwt.mount.JwtTokenAuthentic;
import br.flower.boot.auth.security.jwt.mount.JwtTokenMountAbstract;
import br.flower.boot.auth.security.jwt.mount.JwtTokenRefresh;
import br.flower.boot.auth.user.Usuario;

@Component
public class JwtTokenManage implements TokenManage {
	private static final long serialVersionUID = 3532021853378615578L;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired 
	private HttpServletRequest httpServletRequest;
	@Autowired
	private SecurityClientResourceContext securityClientResourceContext;
	private String tokenType = "Bearer";
	@Override
	public ResponseAuthDto gerarToken(Usuario user, UUID sessionId) {
		ResponseAuthDto responseAuthDto = new ResponseAuthDto();
		responseAuthDto.setTokenType(tokenType);
		responseAuthDto.setRefreshToken(this.jwtGenerateToken(new JwtTokenRefresh(
				//clientSessionDetails.getTokenRefresh(),
				urlRequestOrigen(),
				//"8e28fa6b-fe94-4508-8c87-546ada92d041",
				securityClientResourceContext.getContext().get().getName(),
				sessionId,
				user)));
		
		responseAuthDto.setToken(this.jwtGenerateToken(new JwtTokenAuthentic(
				urlRequestOrigen(),
				securityClientResourceContext.getContext().get().getName(),
				//"8e28fa6b-fe94-4508-8c87-546ada92d041",
				sessionId,
				user)));
		return responseAuthDto;
	}

	private String urlRequestOrigen() {
		return httpServletRequest.getRequestURL().toString();
	}
	
	private String jwtGenerateToken(JwtTokenMountAbstract jwtTokenMountAbstract) {
		return jwtTokenUtil.generateToken(
				jwtTokenMountAbstract.implementsAllClaims(), 
				jwtTokenMountAbstract.getExpirationTime());
	}

	@Override
	public JwtTokenDto deserializeToken(String token) {
		return this.jwtTokenUtil.getDeserializeClaimsToken(token);
	}
}
