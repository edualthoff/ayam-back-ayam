package br.aym.base.produto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.aym.base.produto.caracteristica.CaracteristicaProduto;
import br.aym.base.produto.caracteristica.CaracteristicaProdutoEnum;


@RestController
@RequestMapping("/produto")
public class ProdutoController {
	private static final Logger log = LoggerFactory.getLogger(ProdutoController.class);

	@Autowired
	private ProdutoService produtoService;


	
	@GetMapping(path = "/todos")
	public Page<Produto> buscarAllPage (
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size){
		
		log.debug("pag "+page+" "+size);
	    return this.produtoService.buscarAllPagination(page, size);
	 
	}
	
	@GetMapping(path = "/autosuggest")
	public Page<Produto> buscarModeloAllPageAutoComplete (
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "nome", defaultValue = "") String nome,
			@RequestParam(name = "sort", defaultValue = "asc") String sort,
			@RequestParam(name = "getAll", defaultValue = "false") boolean getAll,
			@RequestParam(name = "getFile", defaultValue = "false") boolean getFile){
		return this.produtoService.buscarPorNomePagAutoComplete(page, size, nome, sort, getAll, getFile, true );
	}
	
	@GetMapping(path = "/caracteristica/{id}")
	public Page<Produto> buscarPorCaracteristicallPage (
			@PathVariable(name = "id") Long idCaracteristica,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "sort", defaultValue = "asc") String sort){
		List<Long> list = new ArrayList<Long>();
		list.add(idCaracteristica);
		return this.produtoService.buscarPorCaracteristica(true, list, page, size, sort);
	}
	
	@GetMapping(path = "/{idProduto}")
	public Produto findProdutoId(@PathVariable("idProduto") Long id) {
		return this.produtoService.getById(id);
	}

}
