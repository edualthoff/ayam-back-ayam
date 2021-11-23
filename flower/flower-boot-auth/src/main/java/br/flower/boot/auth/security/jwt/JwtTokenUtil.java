package br.flower.boot.auth.security.jwt;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;

import org.apache.commons.collections4.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtTokenUtil implements Serializable {
	private static final long serialVersionUID = 441279853708244044L;

	private static final Logger log = LogManager.getLogger(JwtTokenUtil.class);

	static final String CLAIM_KEY_CREATED = "created";


	// Separa o nome do usuario (Username) do token. @Param token
	// retorna o username do token jwt
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// Separa a data que vai expirar o token (Expiration Date) do token. @Param
	// token
	// retorna expiration date do token jwt
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	// Separa os claims e deseraliza na @class JwtTokenDto
	public JwtTokenDto getDeserializeClaimsToken(String token) {
		Set<Entry<String, Object>> claims = getClaimFromToken(token, Claims::entrySet);
		Map<String, Object> map = MapUtils.putAll(new HashMap<>(), claims.toArray());
		ObjectMapper obj = new ObjectMapper();
		//claims.forEach(x -> System.out.println("ee "+x.getKey() +" "+x.getValue()));
		return obj.convertValue(map, JwtTokenDto.class);
	}

	// check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);

	}

	// para retornar qualquer informação do token nos iremos precisar da secret key
	// - Verificar a secret key
	private Claims getAllClaimsFromToken(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(JwtKeyFile.publicKey()).build().parseClaimsJws(token).getBody();
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

	// gera token para user
	public String generateToken(Map<String, Object> claims, Long expiration) {
		return doGenerateToken(claims, expiration);
	}

	// Cria o token e devine tempo de expiração pra ele
	private String doGenerateToken(Map<String, Object> claims, Long expiration) {
		try {
			return Jwts.builder().setClaims(claims)
					.setIssuedAt(new Date(System.currentTimeMillis())).setHeaderParam("typ", "JWT")
					.setExpiration(new Date(System.currentTimeMillis() + expiration))
					.signWith(JwtKeyFile.privateKey(), SignatureAlgorithm.RS256).compact();
		} catch (InvalidKeyException e) {
			log.error("Chave Invalida");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Retorna true caso a assinatura do token for valida
	public boolean validateToken(String token) {
		// Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token);
		return !getAllClaimsFromToken(token).isEmpty();

	}

	// Verificar se o token de Atualizacao ta expirado
	public Boolean canTokenBeRefreshed(String token) {
		return (!isTokenExpired(token));
	}

	/**
	 * // Atualiza a data de criação do token public String refreshToken(String
	 * token) { String refreshedToken; try { final Claims claims =
	 * getAllClaimsFromToken(token); claims.put(CLAIM_KEY_CREATED, new Date());
	 * refreshedToken = doGenerateToken(claims, claims.getSubject()); } catch
	 * (Exception e) { refreshedToken = null; } return refreshedToken; }
	 */
}
