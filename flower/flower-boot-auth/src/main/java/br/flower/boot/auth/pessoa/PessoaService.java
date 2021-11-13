package br.flower.boot.auth.pessoa;

import java.util.UUID;

import br.flower.boot.exception.type.ApiConflictDataException;

public interface PessoaService {

	Pessoa saveOrUpdate(Pessoa pessoa);
	Pessoa getByEmail(String email);
	boolean existEmail(String pessoa);
	boolean verifyEmail(String pessoa) throws ApiConflictDataException; 
	Pessoa getById(UUID pessoaId);
}
