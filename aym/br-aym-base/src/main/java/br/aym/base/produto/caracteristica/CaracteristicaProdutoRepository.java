package br.aym.base.produto.caracteristica;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CaracteristicaProdutoRepository extends JpaRepository<CaracteristicaProduto, Long>{
	
	Page<CaracteristicaProduto> findByTipo(CaracteristicaProdutoEnum tipo, Pageable pageRequest);
	Page<CaracteristicaProduto> findByTipoAndStatus(CaracteristicaProdutoEnum tipo, Boolean status, Pageable pageRequest);

	
	Page<CaracteristicaProduto> findByNomeContainingIgnoreCaseAndStatus(String nome, Boolean status, Pageable pageable);
	Page<CaracteristicaProduto> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

	CaracteristicaProduto findByNomeIgnoreCase(String nome);

	@EntityGraph(value = "CaracteristicaProduto.listProduto", type = EntityGraphType.LOAD)
	Optional<CaracteristicaProduto> getByNomeIgnoreCase(String name);

	List<CaracteristicaProduto> findByStatus(boolean status);
}
