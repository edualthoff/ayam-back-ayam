package br.flower.boot.auth.client;



public interface SecurityClientResourceService extends SecurityClientResourceContext {

		void resourceAuthenticate(String clintBase64);
}
