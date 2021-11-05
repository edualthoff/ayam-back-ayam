package br.aym.base.file;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {

	  public void init();

	  public FileInfo save(MultipartFile file);

	  public FileInfo savePassDirectory(MultipartFile file, List<String> directory);
	  public List<FileInfo> saveMultiFilePassDirectory(MultipartFile[] file, List<String> directory);

	  public Resource load(String filename);

	  public Stream<Path> loadAll();
	  
	  public Stream<Path> loadPathAll(String path);

	  public Resource load(String filename, String[] pathAll);
	  
	  public boolean deletePath(List<String> pathName);
}
