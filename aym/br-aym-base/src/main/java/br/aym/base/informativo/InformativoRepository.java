package br.aym.base.informativo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.stereotype.Repository;


@Repository
public interface InformativoRepository extends JpaRepository<Informativo, Long> {
	
	
	Page<Informativo> findByTipo(TipoMensagemEnum tipo, Pageable pageable);

	@EntityGraph(value = "Informativo.uploadFile", type = EntityGraphType.FETCH)
	List<Informativo> findByDestacar(boolean destacar);
	
	@EntityGraph(value = "Informativo.uploadFile", type = EntityGraphType.FETCH)
	List<Informativo> findByTipoAndDestacarAndStatus(TipoMensagemEnum tipo, boolean destacar, boolean status);
	
	@EntityGraph(value = "Informativo.uploadFile", type = EntityGraphType.FETCH)
	Page<Informativo> getByTipoAndDestacarAndStatus(TipoMensagemEnum tipo, boolean destacar, boolean status, Pageable pageable);

	/**
	 * Retorna uma @Page com os @Informativo --
	 * Passando os parametros como busca e Aceita parametros nulos 
	 * @param status
	 * @param titulo
	 * @param tipo
	 * @param pageable
	 * @return
	 */
	@Query("FROM Informativo c WHERE (:status is null OR c.status = :status) AND (:titulo is null"
			  + " OR lower(c.titulo) LIKE lower(concat('%', :titulo,'%'))) AND (c.tipo IN :tipo)")
	Page<Informativo> findPorStatusAndNomeContaingAndTipoIgnorandoCaseValidNullAllValue
	(@Param("status") Boolean status, @Param("titulo") String titulo, @Param("tipo") List<TipoMensagemEnum> tipo, Pageable pageable);
	
	/**
	 * Retorna uma @Page com os @Informativo -- Retorna junto a lista de @UploadsFiles
	 * Passando os parametros como busca e Aceita parametros nulos 
	 * @param status
	 * @param titulo
	 * @param tipo
	 * @param pageable
	 * @return
	 */
	@Query("FROM Informativo c WHERE (:status is null OR c.status = :status) AND (:titulo is null"
			  + " OR lower(c.titulo) LIKE lower(concat('%', :titulo,'%'))) AND (c.tipo IN :tipo)")
	@EntityGraph(value = "Informativo.uploadFile", type = EntityGraphType.FETCH)
	Page<Informativo> findPorStatusAndNomeContaingAndTipoIgnorandoCaseValidNullAllValueGetListUploadFiles
	(@Param("status") Boolean status, @Param("titulo") String titulo, @Param("tipo") List<TipoMensagemEnum> tipo, Pageable pageable);
}
