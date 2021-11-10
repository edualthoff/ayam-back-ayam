package br.aym.base.produto.caracteristica.adm;

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

import br.aym.base.produto.caracteristica.CaracteristicaProduto;
import br.aym.base.produto.caracteristica.CaracteristicaProdutoService;

@RestController
@RequestMapping("/adm/caracteristica")
public class CaracteristicaControllerAdm {

	@Autowired
	private CaracteristicaProdutoService caracteristicaProdutoService;
	
	@PostMapping("")
	private CaracteristicaProduto save(@RequestBody() CaracteristicaProduto caract) {
		return this.caracteristicaProdutoService.save(caract);
		
	}
	
	@PutMapping("")
	private CaracteristicaProduto update(@RequestBody() CaracteristicaProduto caract) {
		return this.caracteristicaProdutoService.save(caract);		
	}
	
	@GetMapping(path = "/autosuggest")
	public Page<CaracteristicaProduto> buscarModeloAllPageAutoComplete (
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "nome", defaultValue = "") String nome,
			@RequestParam(name = "sort", defaultValue = "asc") String sort,
			@RequestParam(name = "status", required = false) Boolean status){
		return this.caracteristicaProdutoService.buscarPorNomePagAutoComplete(page, size, nome, sort, status);
	}
	
	@GetMapping(path = "/")
	public Page<CaracteristicaProduto> buscarAllPage (
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size){
	    return this.caracteristicaProdutoService.buscarAllPagination(page, size);
	 
	}
	
	@DeleteMapping("/{idCaracteristica}")
	public void excluir(@PathVariable("idCaracteristica") Long idCaracteristica) {
		this.caracteristicaProdutoService.delete(idCaracteristica);
	}
}
