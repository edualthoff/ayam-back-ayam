package br.flower.boot.jwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import br.flower.boot.jwt.core.TokeneAuthetntication;



public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


	@Autowired
	private TokeneAuthetntication tokeneAuthetntication;

	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String token = resolveToken((HttpServletRequest) request);
		
		if (token != null && tokeneAuthetntication.validateToken(token)) {
			tokeneAuthetntication.authenticationSytemToken(token);
		}

		chain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
}