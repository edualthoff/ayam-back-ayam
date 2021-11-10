package br.aym.base.produto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.aym.base.produto.caracteristica.CaracteristicaProduto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	@EntityGraph(value = "Produto.all", type = EntityGraphType.FETCH)
	Optional<Produto> findById(Long id);
	
	/**
	 * Retorna uma @Page com os @Produtos --
	 * Caso o status for @null retorna todos elemtnso
	 * @param status
	 * @param nome
	 * @param pageable
	 * @return
	 */
	@Query("FROM Produto c WHERE (:status is null OR c.status = :status) AND (:nome is null"
			  + " OR lower(c.nome) LIKE lower(concat('%', :nome,'%')))")
	Page<Produto> findPorStatusAndNomeContaingNullAllValue(
			@Param("status") Boolean status,
			@Param("nome") String nome, 
			Pageable pageable
			);
	
	/**
	 * Retorna uma @Page com os @Produtos --
	 * Caso o status for @null retorna todos elemtnso
	 * Filtra pela lista de Caracteristicas informada
	 * @param status
	 * @param listCaracteristicaProduto
	 * @param pageable
	 * @return
	 */
	@Query("SELECT c FROM Produto c JOIN c.listCaracteristicaProduto l"
			+ " WHERE (:status is null OR c.status = :status)"
			+ " AND (l.id IN :listCaracteristicaProduto)")
	@EntityGraph(value = "Produto.uploadFile", type = EntityGraphType.FETCH)
	Page<Produto> findPorStatusCaracteristicaProdutoNullAllValue(
			@Param("status") Boolean status,
			@Param("listCaracteristicaProduto") List<Long> listCaracteristicaProduto,
			Pageable pageable
			);
	
	/**
	 * Retorna uma @Page com os @Produtos --
	 * Caso o status for @null retorna todos elemtnso
	 * Retorna todos os atributos Files e Caracteristicas
	 * @param status
	 * @param nome
	 * @param pageable
	 * @return
	 */
	@Query("FROM Produto c WHERE (:status is null OR c.status = :status) AND (:nome is null"
			  + " OR lower(c.nome) LIKE lower(concat('%', :nome,'%')))")
	@EntityGraph(value = "Produto.all", type = EntityGraphType.FETCH)
	Page<Produto> findPorStatusAndNomeContaingNullAllValueReturnAllCaracteristicaAndFiles(
			@Param("status") Boolean status,
			@Param("nome") String nome, 
			Pageable pageable
			);
	
	/**
	 * Retorna uma @Page com os @Produtos --
	 * Caso o status for @null retorna todos elemtnso
	 * Retorna Files
	 * @param status
	 * @param nome
	 * @param pageable
	 * @return
	 */
	@Query("FROM Produto c WHERE (:status is null OR c.status = :status) AND (:nome is null"
			  + " OR lower(c.nome) LIKE lower(concat('%', :nome,'%')))")
	@EntityGraph(value = "Produto.uploadFile", type = EntityGraphType.FETCH)
	Page<Produto> findPorStatusAndNomeContaingNullAllValueReturnAllFiles(
			@Param("status") Boolean status,
			@Param("nome") String nome, 
			Pageable pageable
			);
}
