package br.flower.boot.auth.client.resource;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientResourceDao extends JpaRepository<ClientResource, UUID>{

	//Optional<OauthClientResource> findByClientId(UUID clientId);
	
}
