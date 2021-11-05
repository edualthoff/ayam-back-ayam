package br.flower.boot.auth.security.jwt;

import java.io.Serializable;
import java.util.UUID;

import br.flower.boot.auth.security.core.ResponseAuthDto;
import br.flower.boot.auth.user.Usuario;

public interface TokenManage extends Serializable {

	public ResponseAuthDto gerarToken(Usuario user, UUID sessionId);
	
	public JwtTokenDto deserializeToken(String token);
	
	//public String refreshToken();
	
	//public String authenticSystemToken();
}
