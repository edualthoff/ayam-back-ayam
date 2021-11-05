package br.flower.boot.auth.user;

import java.util.UUID;

import br.flower.boot.exception.type.ApiConflictDataException;

public interface UsuarioService {

	Usuario saveOrUpdate(Usuario user);
	Usuario getByEmail(String email);
	boolean existUsername(String username);
	boolean verifyUsername(String username) throws ApiConflictDataException; 
	Usuario getById(UUID username);
}
