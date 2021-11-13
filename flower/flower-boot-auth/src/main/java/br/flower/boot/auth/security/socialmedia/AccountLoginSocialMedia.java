package br.flower.boot.auth.security.socialmedia;

import br.flower.boot.auth.socialmedia.SocialMediaAuth;
import br.flower.boot.auth.socialmedia.SocialMediaNameEnum;
import br.flower.boot.auth.user.Usuario;

public interface AccountLoginSocialMedia {
	
	Usuario login(SocialMediaNameEnum socialMediaNameEnum, String tokenMediaSocial);
	SocialMediaAuth register(SocialMediaModel socialMediaModel, SocialMediaNameEnum socialMediaNameEnum);
	
}
