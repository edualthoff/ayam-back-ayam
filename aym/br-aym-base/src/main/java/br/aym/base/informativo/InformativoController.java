package br.aym.base.informativo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/informativo")
public class InformativoController {

	@Autowired
	private InformativoService informativoService;


	/*
	 * Sugestao de itens baseado pelo titulo e o tipo informado
	 */
	@GetMapping(path = "/autosuggest")
	public Page<Informativo> buscarPorTituloOuTipollPageAutoComplete (
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "titulo", defaultValue = "") String titulo,
			@RequestParam(name = "tipo", defaultValue = "") TipoMensagemEnum tipo,
			@RequestParam(name = "getFile", defaultValue = "false") boolean getFile,
			@RequestParam(name = "sort", defaultValue = "asc") String sort){
		return this.informativoService.buscarPorNomeAndTipoPagAutoComplete(page, size, titulo, tipo, getFile, sort, true );
	}
	
	@GetMapping(path = "/tipo/{tipo}")
	public Page<Informativo> buscarPorTipollPageAutoComplete (
			@PathVariable(name = "tipo") TipoMensagemEnum tipo,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "sort", defaultValue = "asc") String sort){
		return this.informativoService.buscarPorTipoPagAutoComplete(page, size, tipo, sort );
	}
	
	@GetMapping(path = "{tipo}/destaque/ativo")
	public List<Informativo> buscarDestacar(
			@PathVariable(name = "tipo") TipoMensagemEnum tipo,
			@RequestParam(name = "qtd", required = false) Integer qtdResult){
		if (qtdResult == null) { 
			return this.informativoService.buscarPorTipoAndDestacar(tipo, true, true);
		}
		return this.informativoService.buscarPorTipoAndDestacarLimitarResultadoOrdbyData(tipo, true, qtdResult, true);
	}
	
	@GetMapping(path = "/{id}")
	public Informativo findProdutoId(
			@PathVariable("id") Long id) {
		return this.informativoService.getById(id);
	}
	
}
