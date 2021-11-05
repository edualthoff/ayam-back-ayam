package br.flower.boot.auth.client;

import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import br.flower.boot.auth.client.resource.ClientResource;
import br.flower.boot.auth.client.resource.ClientResourceService;
import br.flower.boot.auth.exception.type.ClientResourceBadAuthenticationException;


@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class SecurityClientResourceServiceImp implements SecurityClientResourceService {
	private static final long serialVersionUID = 2163951930839111015L;

	@Autowired 
	private ClientResourceService oauthClientResourceService;
	private ClientResource oauthClientResource;
	
	@Override
	public Optional<ClientResource> getContext() {
		return Optional.ofNullable(oauthClientResource);
	}

	@Override
	public void resourceAuthenticate(String clintBase64) {
		if(clintBase64 == null || clintBase64.isEmpty() || clintBase64.isBlank()) {
			throw new ClientResourceBadAuthenticationException("Não foi informado o client");
		}
		oauthClientResource = oauthClientResourceService.getByClientId(UUID.fromString(new String(Base64.getDecoder().decode(clintBase64))));
		if(oauthClientResource == null) {
			throw new ClientResourceBadAuthenticationException("Client não existe ou disabilitado");
		}
	}
}
