package br.aym.base.produto.caracteristica;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.aym.base.informativo.Informativo;
import br.flower.boot.exception.config.ApiMessageSourceError;
import br.flower.boot.exception.type.client.ApiNotFoundException;



@Service
public class CaracteristicaProdutoServiceImp implements CaracteristicaProdutoService{
	private static final long serialVersionUID = -3674966462664602859L;

	@Autowired
	private CaracteristicaProdutoRepository caracteristicaProdutoRepository;
	
	@Override
	public CaracteristicaProduto save(CaracteristicaProduto caracteristicaProduto) {
		return caracteristicaProdutoRepository.save(caracteristicaProduto);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		this.caracteristicaProdutoRepository.deleteById(id);
	}

	@Override
	public CaracteristicaProduto update(CaracteristicaProduto caracteristicaProduto) {
		// TODO Auto-generated method stub
		return this.caracteristicaProdutoRepository.save(caracteristicaProduto);
	}

	@Transactional
	@Override
	public CaracteristicaProduto getById(Long id) {
		CaracteristicaProduto cat = this.caracteristicaProdutoRepository.getById(id);
		cat.setListProduto(null);
		return cat;
	}

	@Override
	public Page<CaracteristicaProduto> buscarAllPagination(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<CaracteristicaProduto> pageResult = caracteristicaProdutoRepository.findAll(pageRequest);
		return new PageImpl<>(pageResult.toList(), pageRequest, pageResult.getTotalElements());
	}

	@Override
	public Page<CaracteristicaProduto> buscarPorCaracteristicaAndStatusPagination(int page, int size,
			CaracteristicaProdutoEnum tipo,  Boolean status) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<CaracteristicaProduto> pageResult = caracteristicaProdutoRepository.findByTipoAndStatus(tipo, status, pageRequest);
		return new PageImpl<>(pageResult.toList(), pageRequest, pageResult.getTotalElements());
	}

	
	public CaracteristicaProduto save(CaracteristicaProduto entities, MultipartFile file) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Page<CaracteristicaProduto> buscarPorNomePagAutoComplete(int page, int size, String keywords, String sort, Boolean status) {
		if (sort == null) {
			sort = "asc";
		}
		PageRequest pageRequest = PageRequest.of(page, size, Direction.fromString(sort), "nome");
		Page<CaracteristicaProduto> pageResult;
		if(status == null) {
			pageResult = caracteristicaProdutoRepository.findByNomeContainingIgnoreCase(keywords, pageRequest);
		} else {
			pageResult = caracteristicaProdutoRepository.findByNomeContainingIgnoreCaseAndStatus(keywords, status, pageRequest);
		}
		return new PageImpl<>(pageResult.toList(), pageRequest, pageResult.getTotalElements());
	}
	
	/**
	 * Recupera todos os produtos referente a caracteristica informada
	 * @param nome - Da caracateristica
	 * @return CaracteristicaProduto
	 */
	@Transactional
	@Override
	public CaracteristicaProduto buscarTodosOsProdutosPorNomeDaCaracteristica(String nome) {
		try {
			CaracteristicaProduto cat = this.caracteristicaProdutoRepository.getByNomeIgnoreCase(nome).orElseThrow();
			cat.getListProduto().size();		
			return cat;
		} catch (NullPointerException e) {
			throw new ApiNotFoundException(ApiMessageSourceError.toMessage("not_found.error.msg"));
		}

	}
	/**
	 * Buscar todas as caracteristicas Ativa
	 */
	@Override
	public List<CaracteristicaProduto> buscarTodosAtivos() {
		List<CaracteristicaProduto> inf = this.caracteristicaProdutoRepository.findByStatus(true);
		if (inf.isEmpty()) {
			throw new ApiNotFoundException(ApiMessageSourceError.toMessage("not_found.error.list.msg"));
		}
		return inf;
	}
	
}
