package br.flower.boot.auth.socialmedia;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.flower.boot.auth.socialmedia.SocialMediaAuth.SocialMediaAuthId;

@Service
public class SocialMediaAuthService implements Serializable {
	private static final long serialVersionUID = 5134841275794819177L;

	@Autowired
	private SocialMediaAuthRepository socialMediaAuthRepository; 
	
	public SocialMediaAuth save(SocialMediaAuth socialMedia) {
		return socialMediaAuthRepository.save(socialMedia);
	}
	
	public SocialMediaAuth findById(SocialMediaAuthId socialMediaId) {
		return socialMediaAuthRepository.findById(socialMediaId).orElse(null);
	}
}
