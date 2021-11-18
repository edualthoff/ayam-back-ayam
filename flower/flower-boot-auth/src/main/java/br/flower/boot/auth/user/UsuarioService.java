package br.flower.boot.auth.user;

import java.util.UUID;

import br.flower.boot.exception.type.client.ApiConflictDataException;


public interface UsuarioService {

	Usuario saveOrUpdate(Usuario user);
	Usuario getByEmail(String email);
	boolean existUsername(String username);
	boolean verifyUsernameExist(String username) throws ApiConflictDataException; 
	Usuario getById(UUID username);
	boolean existUser(String userId);

}
