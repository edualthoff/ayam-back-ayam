package br.flower.boot.auth.security.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import br.flower.boot.auth.client.SecurityClientResourceService;

@Component
public class ClientOauthTokenInterceptor implements HandlerInterceptor {
	private static final Logger log = LoggerFactory.getLogger(ClientOauthTokenInterceptor.class);

	@Autowired 
	private SecurityClientResourceService securityClientResourceService;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.debug("Client interceptor");
		securityClientResourceService.resourceAuthenticate(request.getHeader("client"));
		return securityClientResourceService.getContext().isPresent();
	}
}
