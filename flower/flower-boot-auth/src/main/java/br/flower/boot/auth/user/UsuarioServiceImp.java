package br.flower.boot.auth.user;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;

import br.flower.boot.exception.type.client.ApiConflictDataException;


@Service
public class UsuarioServiceImp implements Serializable, UsuarioService {
	private static final long serialVersionUID = -5594561370968905605L;
	@Autowired

	private UsuarioRepository usuarioRepository;
	
	@Override
	public Usuario getByEmail(String email) {
		return usuarioRepository.findByUsername(email);
	}
	
	@Override
	public Usuario getById(UUID username) {
		return usuarioRepository.findById(username).orElse(null);
	}
	
	@Transient
	@Override
	public Usuario saveOrUpdate(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}


	@Override
	public boolean existUsername(String username) {
		return usuarioRepository.existsByUsername(username);
	}

	@Override
	public boolean verifyUsernameExist(String username) throws ApiConflictDataException {
		if(existUsername(username)) {
			throw new ApiConflictDataException("Usuario já existe");
		}		
		return true;
	}

	@Override
	public boolean existUser(String userId) {
		return this.usuarioRepository.existsById(UUID.fromString(userId));
	}
	
}
