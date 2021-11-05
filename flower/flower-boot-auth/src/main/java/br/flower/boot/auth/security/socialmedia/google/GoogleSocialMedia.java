package br.flower.boot.auth.security.socialmedia.google;


import java.io.IOException;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;

import br.flower.boot.auth.security.socialmedia.SocialMediaModel;
import br.flower.boot.auth.security.socialmedia.SocialMediaValidade;

public class GoogleSocialMedia implements SocialMediaValidade<SocialMediaModel> {
	private static final long serialVersionUID = 6069333377054931824L;

	private String tokenAcess;
	
	public GoogleSocialMedia(String tokenAcess) {
		this.tokenAcess = tokenAcess;
	}
	
	@Override
	public SocialMediaModel getAcessUserInfo() {
		
		final GoogleCredential googleCredentials = new GoogleCredential().setAccessToken(tokenAcess);
		Oauth2 oauth2 = new Oauth2.Builder(new NetHttpTransport(), new JacksonFactory(), googleCredentials).setApplicationName(
		          "Oauth2").build();
		Userinfo userinfo = null;
		try {
			userinfo = oauth2.userinfo().get().execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.convertFields(userinfo);
	}
	
	private SocialMediaModel convertFields(Userinfo userinfo) {
		SocialMediaModel sm = new SocialMediaModel();
		sm.setId(userinfo.getId());
		sm.setEmail(userinfo.getEmail());
		sm.setNome(userinfo.getName());
		sm.setPrimeiroNome(userinfo.getGivenName());
		sm.setUltimoNome(userinfo.getFamilyName());
	    sm.setGenero(userinfo.getGender());
	    //sm.setNiverData(userinfo.);
	    sm.setUrlImagem(userinfo.getPicture());
		//sm.setWidthImage(fetchObjectsResults.get("picture").asObject().get("data").asObject().get("width").asString());
		//sm.setHeightImagem(fetchObjectsResults.get("picture").asObject().get("data").asObject().get("height").asString());
		return sm;		
	}

	public static class GoogleSocialMediaBuild {
		
		public SocialMediaModel build(String tokenAcess) {
			return new GoogleSocialMedia(tokenAcess).getAcessUserInfo();
		}		
	}
}
