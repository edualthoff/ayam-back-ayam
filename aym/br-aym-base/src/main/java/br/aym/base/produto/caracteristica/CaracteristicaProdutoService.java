package br.aym.base.produto.caracteristica;


import java.util.List;

import org.springframework.data.domain.Page;

import br.aym.base.util.CrudService;

public interface CaracteristicaProdutoService extends CrudService<CaracteristicaProduto, Long> {

	Page<CaracteristicaProduto> buscarAllPagination(int page, int size);
	Page<CaracteristicaProduto> buscarPorCaracteristicaAndStatusPagination(int page, int size, CaracteristicaProdutoEnum caracteristicaProdutoEnum, Boolean status);
	Page<CaracteristicaProduto> buscarPorNomePagAutoComplete(int page, int size, String keywords, String sort, Boolean status);
	CaracteristicaProduto buscarTodosOsProdutosPorNomeDaCaracteristica(String nome);
	List<CaracteristicaProduto> buscarTodosAtivos();
}
