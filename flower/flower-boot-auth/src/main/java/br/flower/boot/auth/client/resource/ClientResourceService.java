package br.flower.boot.auth.client.resource;

import java.util.UUID;


public interface ClientResourceService {

	boolean existClient(UUID clientId);
	
	ClientResource getByClientId(UUID clientId);
	
}
