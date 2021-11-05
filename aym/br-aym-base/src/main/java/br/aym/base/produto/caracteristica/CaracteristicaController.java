package br.aym.base.produto.caracteristica;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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



@RestController
@RequestMapping("/caracteristica")
public class CaracteristicaController {
	private static final Logger log = LoggerFactory.getLogger(CaracteristicaController.class);
	
	@Autowired
	private CaracteristicaProdutoService caracteristicaProdutoService;
	

	@GetMapping("/{id}")
	public CaracteristicaProduto getPorId(@PathVariable("id") Long id) {
		return this.caracteristicaProdutoService.getById(id);
	}
	
	@GetMapping("/tipo/{tipo}")
	public CaracteristicaProduto getTodosOsProdutosPorNomeDoTipo(@PathVariable("tipo") String tipo) {
		return this.caracteristicaProdutoService.buscarTodosOsProdutosPorNomeDaCaracteristica(tipo);
	}
	
	
	@GetMapping(path = "/status/ativo/pg")
	public Page<CaracteristicaProduto> buscarAllPageAtivo (
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "tipo", required = false) CaracteristicaProdutoEnum tipo ){
		log.debug("pag "+page+" "+size);
	    return this.caracteristicaProdutoService.buscarPorCaracteristicaAndStatusPagination(page, size, tipo, true);	 
	}
	
	@GetMapping(path = "/autosuggest")
	public Page<CaracteristicaProduto> buscarModeloAllPageAutoComplete (
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "nome", defaultValue = "") String nome,
			@RequestParam(name = "sort", defaultValue = "asc") String sort){
		return this.caracteristicaProdutoService.buscarPorNomePagAutoComplete(page, size, nome, sort, true );
	}
	@GetMapping(path = "/status/ativo")
	public List<CaracteristicaProduto> buscarAllStatusAtivo(){
		return this.caracteristicaProdutoService.buscarTodosAtivos();
	}
	
	@DeleteMapping("/{idCaracteristica}")
	public void excluir(@PathVariable("idCaracteristica") Long idCaracteristica) {
		this.caracteristicaProdutoService.delete(idCaracteristica);
	}
	
}
