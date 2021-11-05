package br.aym.base.informativo;

import java.util.List;
import java.util.Map;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@RestController
@RequestMapping("/informativo")
public class InformativoController {

	@Autowired
	private InformativoService informativoService;
	@Autowired
	private ObjectMapper objectMapper;
	
	@PostMapping("/")
	public Informativo save(@RequestBody() Informativo informativo) {
		return this.informativoService.save(informativo);
	}
	
	// multipart/form-data
	@PostMapping(path = "")
	private Informativo saveFile(@RequestParam() Map<String,String> map, @RequestParam("file") MultipartFile[] file) {
		Informativo informativo = null;
		try {
			informativo = objectMapper.readValue(map.get("informativo"), Informativo.class);
			System.out.println("ifo "+informativo.toString());

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}		
		return this.informativoService.save(informativo, file);
	}
	
	@PutMapping(path = "")
	private Informativo updateFile(@Valid @RequestParam() Map<String,String> map, @RequestParam(name = "file", required = false) MultipartFile[] file) {
		Informativo informativo = null;
		try {
			informativo = objectMapper.readValue(map.get("informativo"), Informativo.class);
			System.out.println("ifo "+informativo.toString());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}		
		return this.informativoService.update(informativo, file);
		
	}
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
	
	@DeleteMapping("/{id}")
	public void excluir(@PathVariable("id") Long idProduto) {
		this.informativoService.delete(idProduto);
	}
	
	/** 
	 * A baixo Request Administrativo ...
	 */
	
	@GetMapping(path = "/adm/autosuggest")
	public Page<Informativo> admBuscarPorTituloOuTipollPageAutoComplete (
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "titulo", defaultValue = "") String titulo,
			@RequestParam(name = "tipo", defaultValue = "") TipoMensagemEnum tipo,
			@RequestParam(name = "status", required = false) Boolean status,
			@RequestParam(name = "getFile", defaultValue = "false") boolean getFile,
			@RequestParam(name = "sort", defaultValue = "asc") String sort){
		System.out.println("ss "+status);
		return this.informativoService.buscarPorNomeAndTipoPagAutoComplete(page, size, titulo, tipo, getFile, sort, status );
	}

}
