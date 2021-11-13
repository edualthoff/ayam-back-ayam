package br.flower.boot.auth.pessoa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, UUID>{

	Pessoa findByEmail(String email);
	
	boolean existsByEmail(String email);
}
