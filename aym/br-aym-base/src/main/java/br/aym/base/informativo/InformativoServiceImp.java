package br.aym.base.informativo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.aym.base.file.FileInfo;
import br.aym.base.file.FilesDirectoryEnum;
import br.aym.base.file.FilesStorageService;
import br.aym.base.upload.UploadFile;
import br.flower.boot.exception.config.ApiMessageSourceError;
import br.flower.boot.exception.type.client.ApiNotFoundException;


@Service
public class InformativoServiceImp implements InformativoService {
	private static final long serialVersionUID = -3817744089568264488L;

	@Autowired
	private InformativoRepository informativoRepository;
	@Autowired
	private FilesStorageService filesStorageService;

	@Override
	public Informativo save(Informativo informativo) {
		return this.informativoRepository.save(informativo);
	}

	@Override
	public Informativo save(Informativo entities, MultipartFile[] file) {
		System.out.println("trace 4 "+entities.toString());

		Set<UploadFile> listUp = this.chamaOmetodoSalvarDeArquivoFile(entities, file);
		entities.setUploadFile(listUp);
		return this.informativoRepository.save(entities);
	}
	

	@Override
	public Informativo update(Informativo informativo) {
		return this.informativoRepository.save(informativo);		
	}

	@Override
	public Informativo update(Informativo entities, MultipartFile[] file) {
		if (file == null) {
			return this.informativoRepository.saveAndFlush(entities);
		} else {
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
			return this.informativoRepository.saveAndFlush(entities);
		}
	}
	
	@Transactional
	@Override
	public Informativo getById(Long id) {
		Informativo info = this.informativoRepository.findById(id).get();
		info.getUploadFile().size();
		return info;
	}

	@Override
	public void delete(Long id) {
		this.informativoRepository.deleteById(id);		
	}
	
	@Override
	public Page<Informativo> buscarAllPagination(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Informativo> pageResult = informativoRepository.findAll(pageRequest);
		return new PageImpl<>(pageResult.toList(), pageRequest, pageResult.getTotalElements());
	}

	@Override
	public Page<Informativo> buscarPorNomeAndTipoPagAutoComplete(int page, int size, String keywords,
			TipoMensagemEnum tipo, boolean getFile, String sort, Boolean status) {
		List<TipoMensagemEnum> tipoEnum = new ArrayList<>();
		if (sort == null) {
			sort = "asc";
		}
		if (tipo == null) {
			tipoEnum.addAll(Arrays.asList(TipoMensagemEnum.values()));
		} else {
			tipoEnum.add(tipo);
		}
		
		PageRequest pageRequest = PageRequest.of(page, size, Direction.fromString(sort), "dateCreated");
		Page<Informativo> pageResult = null;		

		if(getFile == true) {
			pageResult = this.informativoRepository.findPorStatusAndNomeContaingAndTipoIgnorandoCaseValidNullAllValueGetListUploadFiles(
					status, keywords, tipoEnum, pageRequest);
		} else {
			pageResult = this.informativoRepository.findPorStatusAndNomeContaingAndTipoIgnorandoCaseValidNullAllValue(
					status, keywords, tipoEnum, pageRequest);		

		}
		return new PageImpl<>(pageResult.toList(), pageRequest, pageResult.getTotalElements());
	}
	
	@Override
	public Page<Informativo> buscarPorTipoPagAutoComplete(int page, int size, TipoMensagemEnum tipo, String sort) {
		if (sort == null) {
			sort = "asc";
		}		
		PageRequest pageRequest = PageRequest.of(page, size, Direction.fromString(sort), "dateCreated");
		Page<Informativo> pageResult = this.informativoRepository.findByTipo(tipo, pageRequest);		
		return new PageImpl<>(pageResult.toList(), pageRequest, pageResult.getTotalElements());
	}

	@Override
	public List<Informativo> buscarPorDestacar(boolean destacar) {
		List<Informativo> inf = this.informativoRepository.findByDestacar(destacar);
		if (inf.isEmpty()) {
			throw new ApiNotFoundException(ApiMessageSourceError.toMessage("not_found.error.list.msg"));
		}
		return inf;
	}
	@Override
	public List<Informativo> buscarPorTipoAndDestacar(TipoMensagemEnum tipo, boolean destacar, boolean status) {
		List<Informativo> inf = this.informativoRepository.findByTipoAndDestacarAndStatus(tipo, destacar, status);
		if (inf.isEmpty()) {
			throw new ApiNotFoundException(ApiMessageSourceError.toMessage("not_found.error.list.msg"));
		}
		return inf;
	}
	
	@Override
	public List<Informativo> buscarPorTipoAndDestacarLimitarResultadoOrdbyData(TipoMensagemEnum tipo, boolean destacar, int qtdResult, boolean status) {
		PageRequest pageRequest = PageRequest.of(0, qtdResult, Direction.fromString("asc"), "dateCreated");
		try {
			Page<Informativo> pageResult = this.informativoRepository.getByTipoAndDestacarAndStatus(tipo, destacar, status,  pageRequest);
			if(pageResult.getContent().isEmpty()) {
				throw new ApiNotFoundException(ApiMessageSourceError.toMessage("not_found.error.list.msg"));
			}
			return pageResult.getContent();
		} catch (Exception e) {
			throw new ApiNotFoundException(ApiMessageSourceError.toMessage("not_found.error.list.msg"));
		}
	}
	
	/**
	 * Chama o metodo salvar de arquivos do Files Storage Service e implementa a @class UploadFile
	 * @param entities
	 * @param file
	 * @return Set<UploadFile>
	 */
	private Set<UploadFile> chamaOmetodoSalvarDeArquivoFile(Informativo entities, MultipartFile[] file) {
		List<FileInfo> fileInfo = this.filesStorageService.saveMultiFilePassDirectory(
				file, 
				Arrays.asList(FilesDirectoryEnum.INFORMATIVO.getDirectory(), entities.getTipo().getTipo(), UUID.randomUUID().toString().replace("-", "")));
		Set<UploadFile> listUp = new LinkedHashSet<UploadFile>();
		fileInfo.forEach(x -> {
			listUp.add(new UploadFile(x.getName(), x.getUrl(), x.getPathRelative()));
		});
		return listUp;
	}

}