package br.flower.boot.auth.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID>{

	Usuario findByUsername(String username);
	
	boolean existsByUsername(String username);
}
