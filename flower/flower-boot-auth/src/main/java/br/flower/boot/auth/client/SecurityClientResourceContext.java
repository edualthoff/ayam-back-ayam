package br.flower.boot.auth.client;

import java.io.Serializable;
import java.util.Optional;

import br.flower.boot.auth.client.resource.ClientResource;

public interface SecurityClientResourceContext extends Serializable {

	Optional<ClientResource> getContext();
}
