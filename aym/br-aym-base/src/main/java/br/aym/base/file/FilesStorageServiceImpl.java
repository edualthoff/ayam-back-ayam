package br.aym.base.file;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import br.flower.boot.exception.config.ApiMessageSourceError;
import br.flower.boot.exception.type.server.ApiInternalServerError;

@Service
public class FilesStorageServiceImpl implements FilesStorageService, Serializable {
	private static final long serialVersionUID = -2006216245007988902L;

	private final Path root = Paths.get("uploads");

	@Override
	public void init() {}

	/**
	 * Salvar um unico arquivo na pasta raiz (padrao)
	 */
	@Override
	public FileInfo save(MultipartFile file) {
		try {
			String fileNameNew = this.gerarNamePath() + "." + this.getTypeFile(file.getOriginalFilename());

			Files.copy(file.getInputStream(),
					this.root.resolve(fileNameNew));
			return new FileInfo(
					fileNameNew, 
					MvcUriComponentsBuilder.fromMethodName(
							FilesController.class, "getFile", fileNameNew).build().toString(),
					fileNameNew);
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}

	/*
	 * Verificar se o diretorios informados existe caso não ele cria todos.. e Salvar o arquivo
	 */
	@Override
	public FileInfo savePassDirectory(MultipartFile file, List<String> directory) {
		try {
			File fileFor = new File(directory.get(0));
			for (int i = 1; i < directory.size(); i++) {
				fileFor = new File(fileFor, directory.get(i));
			}
			String fileNameNew = this.gerarNamePath() + "." + this.getTypeFile(file.getOriginalFilename());
			Path pathName =  this.root.resolve(fileFor.getPath()).resolve(fileNameNew);
			
			if (Files.exists(root.resolve(fileFor.getPath()))) {
				Files.copy(file.getInputStream(), pathName);
			} else {
				Files.createDirectories(root.resolve(fileFor.getPath()));
				Files.copy(file.getInputStream(), pathName);				
			}
			String pathRelativo = pathName.toString().replaceAll("\\\\", "/").replace("uploads/", "");
			return new FileInfo(
					fileNameNew, 
					MvcUriComponentsBuilder.fromMethodName(
							FilesController.class, "getFile", pathRelativo).build().toString(),
					pathRelativo);
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}
	@Override
	public List<FileInfo> saveMultiFilePassDirectory(MultipartFile[] fileParts, List<String> directory) {
		try {
			List<FileInfo> listF = new ArrayList<>();
			for(MultipartFile file : fileParts ) {
				File fileFor = new File(directory.get(0));
				for (int i = 1; i < directory.size(); i++) {
					fileFor = new File(fileFor, directory.get(i));
				}
				String fileNameNew = this.gerarNamePath() + "." + this.getTypeFile(file.getOriginalFilename());
				Path pathName =  this.root.resolve(fileFor.getPath()).resolve(fileNameNew);
				
				if (Files.exists(root.resolve(fileFor.getPath()))) {
					Files.copy(file.getInputStream(), pathName);
				} else {
					Files.createDirectories(root.resolve(fileFor.getPath()));
					Files.copy(file.getInputStream(), pathName);				
				}
				String pathRelativo = pathName.toString().replaceAll("\\\\", "/").replace("uploads/", "");
				listF.add(new FileInfo(
						fileNameNew, 
						MvcUriComponentsBuilder.fromMethodName(
								FilesController.class, "getFile", pathRelativo).build().toString(),
						pathRelativo));								
			}
			return listF;

		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}

	/**
	 * Passa como parametro multiplos path, vai montando por ordem do array
	 */
	@Override
	public Resource load(String filename, String[] pathAll) {
		try {
			File fileFor = new File(pathAll[0]);

			for (int i = 1; i < pathAll.length; i++) {
				fileFor = new File(fileFor, pathAll[i]);
			}
			Path file = root.resolve(fileFor.getPath()).resolve(filename);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	@Override
	public Resource load(String filename) {
		try {
			Path file = root.resolve(filename);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	/*
	 * Descontinuado
	 */
	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
		} catch (IOException e) {
			throw new RuntimeException("Could not load the files!");
		}
	}
	

	@Override
	public Stream<Path> loadPathAll(String path2) {
		try {
			System.out.println("aqui load "+this.root.resolve(path2));
			return Files.walk(this.root.resolve(path2), 1).filter(path -> !path.equals(this.root.resolve(path2))).map(this.root::relativize);
		} catch (IOException e) {
			throw new RuntimeException("Could not load the files!");
		}
	}
	
	/**
	 * Gera um nome para o arquivo com base na hora do sistema timestamp long
	 * @return
	 */
	private String gerarNamePath() {
		return Long.toString(OffsetDateTime.now().getLong(ChronoField.INSTANT_SECONDS));
	}

	/**
	 * Pega o tipo de arquivo passando como parametro o filename dele ex: (JPG, PNG, etc)
	 * @param fileName
	 * @return
	 */
	private String getTypeFile(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
	@Override
	public boolean deletePath(List<String> pathName) {
		try {
			for (String x : pathName) {
				Path path =  this.root.resolve(x);
				Files.deleteIfExists(path);		
			}
		} catch (IOException e) {
			throw new ApiInternalServerError(
					ApiMessageSourceError.toMessage("internal_server_error.upload.error.code"), 
					ApiMessageSourceError.toMessage("internal_server_error.upload.error.msg")); 
		}	
		return true;
	}

}
