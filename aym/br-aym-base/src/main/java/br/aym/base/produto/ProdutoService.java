package br.aym.base.produto;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import br.aym.base.util.CrudService;

public interface ProdutoService extends CrudService<Produto, Long> {

	Produto save(Produto entities, MultipartFile[] file );
	Produto update(Produto entities, MultipartFile[] file );

	Page<Produto> buscarPorNomePagAutoComplete(int page, int size, String keywords, String sort, boolean getAll, boolean getFile);

}
