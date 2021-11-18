package br.flower.boot.auth.pessoa;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;

import br.flower.boot.exception.type.client.ApiConflictDataException;


@Service
public class PessoaServiceImp implements Serializable, PessoaService {
	private static final long serialVersionUID = -5594561370968905605L;

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Override
	public Pessoa getByEmail(String email) {
		return pessoaRepository.findByEmail(email);
	}
	
	@Override
	public Pessoa getById(UUID pessoaId) {
		return pessoaRepository.findById(pessoaId).orElse(null);
	}
	
	@Transient
	@Override
	public Pessoa saveOrUpdate(Pessoa usuario) {
		return pessoaRepository.save(usuario);
	}


	@Override
	public boolean existEmail(String email) {
		return pessoaRepository.existsByEmail(email);
	}

	@Override
	public boolean verifyEmail(String email) throws ApiConflictDataException {
		if(existEmail(email)) {
			throw new ApiConflictDataException("Usuario já existe");
		}		
		return true;
	}
	
}
