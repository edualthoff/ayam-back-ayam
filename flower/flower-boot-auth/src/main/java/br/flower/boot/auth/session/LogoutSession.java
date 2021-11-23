package br.flower.boot.auth.session;

public interface LogoutSession {

	void removeToken(String token);
}
