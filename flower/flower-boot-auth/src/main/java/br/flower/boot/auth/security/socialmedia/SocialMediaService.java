package br.flower.boot.auth.security.socialmedia;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import br.flower.boot.auth.security.socialmedia.facebook.FacebookSocialMedia;
import br.flower.boot.auth.security.socialmedia.google.GoogleSocialMedia;
import br.flower.boot.auth.socialmedia.SocialMediaNameEnum;

@Service
public class SocialMediaService implements Serializable{
	private static final long serialVersionUID = -1498653619907616391L;

	
	public SocialMediaModel acessarInfoDoUsuario(SocialMediaNameEnum socialMediaNameEnum, String tokenMediaSocial) {
		//SocialMediaModel socialMediaModel = null;
		switch (socialMediaNameEnum) {
		case FACEBOOK:
			//SocialMediaValidade socialMediaValidade = new FacebookSocialMedia(tokenMediaSocial)
			return new FacebookSocialMedia.FacebookSocialMediaBuild().build(tokenMediaSocial);
			//break;
		case GOOGLE:
			return new GoogleSocialMedia.GoogleSocialMediaBuild().build(tokenMediaSocial);
		}
		return null;
	}
}
