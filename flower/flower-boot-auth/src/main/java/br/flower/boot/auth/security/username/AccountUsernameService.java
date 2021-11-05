package br.flower.boot.auth.security.username;

import java.io.Serializable;

import br.flower.boot.auth.user.Usuario;
import br.flower.boot.auth.user.role.UserRoleEnum;

public interface AccountUsernameService extends Serializable{

	Usuario login(String username, String password);
	Usuario registerUser(Usuario user, UserRoleEnum userRoleEnum);
}