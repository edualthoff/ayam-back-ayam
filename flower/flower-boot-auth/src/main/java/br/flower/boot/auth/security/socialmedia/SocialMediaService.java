package br.flower.boot.auth.security.socialmedia;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import br.flower.boot.auth.security.socialmedia.facebook.FacebookSocialMedia;
import br.flower.boot.auth.security.socialmedia.google.GoogleSocialMedia;
import br.flower.boot.auth.socialmedia.SocialMediaNameEnum;
import br.flower.boot.exception.config.ApiMessageSourceError;
import br.flower.boot.exception.type.ApiBadRequestException;

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
		/* Desabilitado 
		case GOOGLE:
			return new GoogleSocialMedia.GoogleSocialMediaBuild().build(tokenMediaSocial);*/
		default:
			break;
		} 
		throw new ApiBadRequestException(
				ApiMessageSourceError.toMessage("bad_request.erro.grant_type.code"),
				ApiMessageSourceError.toMessage("bad_request.erro.grant_type.msg"));
	}
}
