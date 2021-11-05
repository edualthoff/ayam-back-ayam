package br.aym.base.produto;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	Page<Produto> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

	@EntityGraph(value = "Produto.all", type = EntityGraphType.FETCH)
	Page<Produto> getByNomeContainingIgnoreCase(String nome, Pageable pageable);

	@EntityGraph(value = "Produto.uploadFile", type = EntityGraphType.FETCH)
	Page<Produto> readByNomeContainingIgnoreCase(String nome, Pageable pageable);
	
	@EntityGraph(value = "Produto.all", type = EntityGraphType.FETCH)
	Optional<Produto> findById(Long id);
	
}
