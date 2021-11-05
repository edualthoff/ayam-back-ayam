package br.flower.boot.auth.security.socialmedia;

import java.io.Serializable;

/**
 * Interface para retornar as informacoes do usuario, passando a @class como parametro
 * 
 * Atual é @class SocialMediaModel
 * @author edu
 *
 * @param <T>
 */
public interface SocialMediaValidade<T> extends Serializable {	
	
	T getAcessUserInfo();
	
}
