package br.flower.boot.jwt.util;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.flower.boot.jwt.model.JwtTokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtTokenUtil implements Serializable {
	private static final long serialVersionUID = 441279853708244044L;
	private static final Logger log = LogManager.getLogger(JwtTokenUtil.class);

	static final String CLAIM_KEY_CREATED = "created";

	@Autowired
	private JwtPublicKey jwtKey;

	// Separa o nome do usuario (Username) do token. @Param token
	// retorna o username do token jwt
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// Separa os claims e deseraliza na @class JwtTokenDto
	public JwtTokenDto getDeserializeClaimsToken(String token) {
		Set<Entry<String, Object>> claims = getClaimFromToken(token, Claims::entrySet);
		Map<String, Object> map = claims.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue));
		ObjectMapper obj = new ObjectMapper();
		//claims.forEach(x -> System.out.println("ee "+x.getKey() +" "+x.getValue()));
		return obj.convertValue(map, JwtTokenDto.class);
	}

	
	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);

	}

	// para retornar qualquer informação do token nos iremos precisar da secret key
	// - Verificar a secret key
	private Claims getAllClaimsFromToken(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(jwtKey.publicKey()).build().parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException exception) {
			log.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
		} catch (UnsupportedJwtException exception) {
			log.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
		} catch (MalformedJwtException exception) {
			log.warn("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
		} catch (SignatureException exception) {
			log.warn("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
		} catch (IllegalArgumentException exception) {
			log.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Retorna true caso a assinatura do token for valida
	public boolean validateTokenAndExpire(String token) {
		// Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token);
		return !getAllClaimsFromToken(token).isEmpty();

	}

	/**
	 * // Atualiza a data de criação do token public String refreshToken(String
	 * token) { String refreshedToken; try { final Claims claims =
	 * getAllClaimsFromToken(token); claims.put(CLAIM_KEY_CREATED, new Date());
	 * refreshedToken = doGenerateToken(claims, claims.getSubject()); } catch
	 * (Exception e) { refreshedToken = null; } return refreshedToken; }
	 */
}
