package br.flower.boot.auth.security.socialmedia.facebook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.json.JsonObject;

import br.flower.boot.auth.exception.type.SocialMediaException;
import br.flower.boot.auth.security.socialmedia.SocialMediaModel;
import br.flower.boot.auth.security.socialmedia.SocialMediaValidade;

public class FacebookSocialMedia implements SocialMediaValidade<SocialMediaModel> {
	private static final Logger log = LoggerFactory.getLogger(FacebookSocialMedia.class);

	private static final long serialVersionUID = 7968038778020567632L;

	private String tokenAcess;
	private static String FIELDS_NAME = "id,last_name,first_name,email,birthday,picture,name";
	
	public FacebookSocialMedia(String tokenAcess) {
		this.tokenAcess = tokenAcess;
	}
	
	@Override
	public SocialMediaModel getAcessUserInfo() {
		log.debug("token {}", tokenAcess);
		try {
		FacebookClient facebookClient = new DefaultFacebookClient(this.tokenAcess, Version.VERSION_10_0);
				JsonObject fetchObjectsResults =
				  facebookClient.fetchObject("me", JsonObject.class, Parameter.with("fields", FIELDS_NAME));
			return this.convertFields(fetchObjectsResults);
		} catch (FacebookOAuthException e) {
			log.error("Token expirado {}", e);
			throw new SocialMediaException("Erro ao validar o token de acesso");
		}
	}

	
	private SocialMediaModel convertFields(JsonObject fetchObjectsResults) {
		SocialMediaModel sm = new SocialMediaModel();
		sm.setId(fetchObjectsResults.get("id").asString());
		sm.setEmail(fetchObjectsResults.get("email").asString());
		sm.setNome(fetchObjectsResults.get("name").asString());
		sm.setPrimeiroNome(fetchObjectsResults.get("first_name").asString());
		sm.setUltimoNome(fetchObjectsResults.get("last_name").asString());
	    sm.setNiverData(fetchObjectsResults.get("birthday").asString());
	    sm.setUrlImagem(fetchObjectsResults.get("picture").asObject().get("data").asObject().get("url").asString());
		sm.setWidthImage(String.valueOf(fetchObjectsResults.get("picture").asObject().get("data").asObject().get("width").asInt()));
		sm.setHeightImagem(String.valueOf(fetchObjectsResults.get("picture").asObject().get("data").asObject().get("height").asInt()));
		return sm;		
	}
	
	public static class FacebookSocialMediaBuild {
		
		public SocialMediaModel build(String tokenAcess) {
			return new FacebookSocialMedia(tokenAcess).getAcessUserInfo();
		}		
	}
}
