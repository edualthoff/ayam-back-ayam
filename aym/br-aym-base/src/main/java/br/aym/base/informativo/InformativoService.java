package br.aym.base.informativo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import br.aym.base.util.CrudService;

public interface InformativoService extends CrudService<Informativo, Long>{

	Informativo save(Informativo entities, MultipartFile[] file );
	Informativo update(Informativo entities, MultipartFile[] file );
	
	
	// Page<Informativo> buscarPorNomeAndTipoPagAutoComplete(int page, int size, String keywords, TipoMensagemEnum tipo, String sort, boolean getFile);
	Page<Informativo> buscarPorNomeAndTipoPagAutoComplete(int page, int size, String keywords, TipoMensagemEnum tipo, boolean getFile, String sort, Boolean status);
	
	Page<Informativo> buscarPorTipoPagAutoComplete(int page, int size, TipoMensagemEnum tipo, String sort);

	List<Informativo> buscarPorDestacar(boolean destacar);
	
	List<Informativo> buscarPorTipoAndDestacar(TipoMensagemEnum tipo, boolean destacar, boolean status);
	
	List<Informativo> buscarPorTipoAndDestacarLimitarResultadoOrdbyData(TipoMensagemEnum tipo, boolean destacar, int qtdResult, boolean status);
	
}
