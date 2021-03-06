package br.flower.boot.auth.session.client;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientSessionDetailsService implements Serializable {
	private static final long serialVersionUID = -24387276505270878L;

	@Autowired
	private ClientSessionDetailsRepository clientSessionDetailsRepository;
	
	public ClientSessionDetails save(ClientSessionDetails clientSessionDetails) {
		return clientSessionDetailsRepository.save(clientSessionDetails);
	}
	
	public ClientSessionDetails findById(UUID clientId) {
		return clientSessionDetailsRepository.findById(clientId).orElse(null);
	}
	
	public void removeSession(UUID idSession) {
		 clientSessionDetailsRepository.deleteById(idSession);
	}
	
}
