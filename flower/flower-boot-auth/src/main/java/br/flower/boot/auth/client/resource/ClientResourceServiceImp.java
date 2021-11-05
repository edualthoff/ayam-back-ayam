package br.flower.boot.auth.client.resource;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientResourceServiceImp implements ClientResourceService, Serializable{
	private static final long serialVersionUID = 5384322273260001452L;

	@Autowired
	private ClientResourceDao oauthClientResourceDao;
	
	
	@Override
	public boolean existClient(UUID clientId) {
		return oauthClientResourceDao.existsById(clientId);
	}

	
	@Override
	public ClientResource getByClientId(UUID clientId) {
		return oauthClientResourceDao.findById(clientId).orElse(null);
	}

}
