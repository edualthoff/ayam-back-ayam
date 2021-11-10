package br.aym.base.produto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.aym.base.file.FileInfo;
import br.aym.base.file.FilesDirectoryEnum;
import br.aym.base.file.FilesStorageService;
import br.aym.base.produto.caracteristica.CaracteristicaProduto;
import br.aym.base.upload.UploadFile;
import br.flower.boot.exception.config.ApiMessageSourceError;
import br.flower.boot.exception.type.client.ApiNotFoundException;


@Repository
public class ProdutoServiceImp implements ProdutoService {
	private static final long serialVersionUID = -2619055839320135407L;

	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private FilesStorageService filesStorageService;

	@Override
	public Produto save(Produto cristal) {
		return this.produtoRepository.save(cristal);
	}
	
	@Override
	public Produto save(Produto entities, MultipartFile[] file) {
		Set<UploadFile> listUp = this.chamaOmetodoSalvarDeArquivoFile(entities, file);
		entities.setUploadFile(listUp);
		return this.produtoRepository.save(entities);
	}
	//@Transactional
	@Override
	public Produto update(Produto entities, MultipartFile[] file) {
		if (file == null) {
			System.out.println("file null");
			return this.produtoRepository.saveAndFlush(entities);
		} else {
			System.out.println("file 2");

			List<String> listPath = new ArrayList<>();	
			// Deleta os Files antigo passando o Path caminho como parametro e remove da lista de upload o File marcado para remover.
			entities.getUploadFile().forEach(x -> {
				if(x.isRemove()) {
					listPath.add(x.getPathUrl());
					entities.getUploadFile().remove(x);
				}
			});
			if(!listPath.isEmpty()) {
				this.filesStorageService.deletePath(listPath);
			}
			/**
			 * Apos Deletar Salva os Files novos e atualizar o Produto
			 */
			Set<UploadFile> listUp = this.chamaOmetodoSalvarDeArquivoFile(entities, file);
			
			entities.setUploadFile(listUp);
			return this.produtoRepository.saveAndFlush(entities);
		}		
	}

	@Override
	public Produto update(Produto cristal) {
		// TODO Auto-generated method stub
		return this.produtoRepository.save(cristal);
	}
	
	@Override
	public void delete(Long id) {
		this.produtoRepository.deleteById(id);
	}

	@Transactional
	@Override
	public Produto getById(Long id) {
		Produto prod = this.produtoRepository.findById(id).get();
		return prod;
	}

	@Override
	public Page<Produto> buscarAllPagination(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Produto> pageResult = produtoRepository.findAll(pageRequest);
		return new PageImpl<>(pageResult.toList(), pageRequest, pageResult.getTotalElements());
	}
	
	@Transactional
	@Override
	public Page<Produto> buscarPorNomePagAutoComplete(int page, int size, String keywords, String sort, 
			boolean getAll, boolean getFile, Boolean status) {
		if (sort == null) {
			sort = "asc";
		}		
		PageRequest pageRequest = PageRequest.of(page, size, Direction.fromString(sort), "nome");
		Page<Produto> pageResult = null;
		if(getAll == true) {
			pageResult = produtoRepository.findPorStatusAndNomeContaingNullAllValueReturnAllCaracteristicaAndFiles
					(status, keywords, pageRequest);
		} else if(getAll == false && getFile == true) {
			pageResult = produtoRepository.findPorStatusAndNomeContaingNullAllValueReturnAllFiles
					(status, keywords, pageRequest);
		} else if(getAll == false && getFile == false) {
			pageResult = produtoRepository.findPorStatusAndNomeContaingNullAllValue(status, keywords, pageRequest);
		}
		return new PageImpl<>(pageResult.toList(), pageRequest, pageResult.getTotalElements());
	}
	

	@Override
	public Page<Produto> buscarPorCaracteristica(
			Boolean status, List<Long> caracteristicaProduto, int page, int size, String sort) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.fromString(sort), "nome");
		Page<Produto> pageResult = this.produtoRepository.findPorStatusCaracteristicaProdutoNullAllValue
				(status, caracteristicaProduto, pageRequest);
		if(pageResult.getTotalElements() == 0 ) {
			throw new ApiNotFoundException(ApiMessageSourceError.toMessage("not_found.error.list.msg"));
		}
		return pageResult;
	}
	
	/**
	 * Chama o metodo salvar de arquivos do Files Storage Service
	 * @param entities
	 * @param file
	 * @return
	 */
	private Set<UploadFile> chamaOmetodoSalvarDeArquivoFile(Produto entities, MultipartFile[] file) {
		List<FileInfo> fileInfo = this.filesStorageService.saveMultiFilePassDirectory(
				file, 
				Arrays.asList(FilesDirectoryEnum.PRODUTO.getDirectory(), entities.getNome().replaceAll(" ", "-")));
		Set<UploadFile> listUp = new LinkedHashSet<UploadFile>();
		fileInfo.forEach(x -> {
			listUp.add(new UploadFile(x.getName(), x.getUrl(), x.getPathRelative()));
		});
		return listUp;
	}

}
