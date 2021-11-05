package br.aym.base.produto;

import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("/produto")
public class ProdutoController {
	private static final Logger log = LoggerFactory.getLogger(ProdutoController.class);

	@Autowired
	private ProdutoService produtoService;

	@Autowired private ObjectMapper objectMapper;
	
	@PostMapping(path = "")
	private Produto saveFile(@RequestParam() Map<String,String> map, @RequestParam("file") MultipartFile[] file) {
		Produto produto = null;
		try {
			produto = objectMapper.readValue(map.get("produto"), Produto.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}		
		return this.produtoService.save(produto, file);
		
	}
	
	@PutMapping(path = "")
	private Produto updateFile(@Valid @RequestParam() Map<String,String> map, @RequestParam(name = "file", required = false) MultipartFile[] file) {
		Produto produto = null;
		try {
			produto = objectMapper.readValue(map.get("produto"), Produto.class);
			//System.out.println("bb "+produto.toString());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}		
		return this.produtoService.update(produto, file);
		
	}
	
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
		return this.produtoService.buscarPorNomePagAutoComplete(page, size, nome, sort, getAll, getFile );
	}
	
	@GetMapping(path = "/{idProduto}")
	public Produto findProdutoId(@PathVariable("idProduto") Long id) {
		return this.produtoService.getById(id);
	}
	
	@DeleteMapping("/{idProduto}")
	public void excluir(@PathVariable("idProduto") Long idProduto) {
		this.produtoService.delete(idProduto);
	}
}
