package br.aym.base.file;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import br.aym.base.file.type.ContentTypeEnum;
import br.flower.boot.exception.config.ApiMessageSourceError;
import br.flower.boot.exception.type.client.ApiUnsupportedMediaType;

@RestController
@RequestMapping("/api/upload")
public class FilesController {
	private static final Logger log = LoggerFactory.getLogger(FilesController.class);

	@Autowired
	FilesStorageService storageService;

	@PostMapping("")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
		String message = "";
		try {
			FileInfo fi = storageService.save(file);
			return ResponseEntity.status(HttpStatus.OK).body(fi);
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}

	@PostMapping("/{pathName}")
	public ResponseEntity<?> uploadFilePath(@RequestParam("file") MultipartFile file,
			@PathVariable("pathName") String pathName) {
		String message = "";
		try {
			FileInfo fi = storageService.savePassDirectory(file, Arrays.asList(pathName));
			return ResponseEntity.status(HttpStatus.OK).body(fi);
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}

	@PostMapping("/{pathName}/{userId}")
	public ResponseEntity<?> uploadFilePath(@RequestParam("file") MultipartFile file,
			@PathVariable("pathName") String pathName, @PathVariable("userId") String userId) {
		String message = "";
		try {
			// String[] var = {pathName, userId};
			FileInfo fi = storageService.savePassDirectory(file, Arrays.asList(pathName, userId));
			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.OK).body(fi);
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}

	/**
	 *  Adicionar / enviar - Icons Custom -
	 * @param file
	 * @return
	 */
	@PostMapping("/icon/custom")
	public ResponseEntity<?> uploadFilePathIconCustom(@RequestParam("file") MultipartFile file) {
		if (ContentTypeEnum.SVG.getType().equals(file.getContentType())) {
			FileInfo fi = storageService.savePassDirectory(file, Arrays.asList("icons", "custom"));
			return ResponseEntity.status(HttpStatus.OK).body(fi);
		}
		throw new ApiUnsupportedMediaType(ApiMessageSourceError.toMessage("unsupportedMediaType.error.code"),
				ApiMessageSourceError.toMessage("unsupportedMediaType.error.msg"));
	}

	/*
	 * Monta uma lista de icones do sistema passando o tipo
	 */
	@GetMapping("/files/{tipo}/icons")
	public ResponseEntity<List<FileInfo>> getListFiles(@PathVariable String tipo) throws IOException {
		List<FileInfo> fileInfos = storageService.loadPathAll("icons/" + tipo).map(path -> {
			String filename = path.getFileName().toString();
			UriComponents url = MvcUriComponentsBuilder
					.fromMethodName(FilesController.class, "getFile2Path", "icons", tipo, filename).build();
			String pathFile = (path.getParent() + "\\" + filename).replace("\\", "/");
			String s = null;
			try {
				s = new String(this.getFile2Path("icons", tipo, filename).getBody().getInputStream().readAllBytes(),
						StandardCharsets.UTF_8);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return new FileInfo(filename, url.toString(), pathFile, s);
		}).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
	}

	@GetMapping("/files/{filename:.+}")
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		Resource file = storageService.load(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, this.contentTypeFile(file.getFilename()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@GetMapping("/files/{pathName}/{userId}/{filename:.+}")
	public ResponseEntity<Resource> getFile2Path(@PathVariable("pathName") String pathName,
			@PathVariable("userId") String userId, @PathVariable String filename) {
		String[] var = { pathName, userId };
		Resource file = storageService.load(filename, var);
		log.debug(" file: " + file.getFilename()+" ");
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, this.contentTypeFile(file.getFilename()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@GetMapping("/files/{pathName}/{pathName2}/{pathName3}/{filename:.+}")
	public ResponseEntity<Resource> getFile3Path(@PathVariable String filename,
			@PathVariable("pathName") String pathName, @PathVariable("pathName2") String pathName2,
			@PathVariable("pathName3") String pathName3) {
		String[] var = { pathName, pathName2, pathName3 };
		Resource file = storageService.load(filename, var);
		log.debug(" file: " + file.getFilename());
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, this.contentTypeFile(file.getFilename()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@GetMapping("/files/{pathName}/{filename:.+}")
	public ResponseEntity<Resource> getFile(@PathVariable String filename, @PathVariable("pathName") String pathName) {
		String[] var = { pathName };
		Resource file = storageService.load(filename, var);
		log.debug(" file: " + file.getFilename());
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.header(HttpHeaders.CONTENT_TYPE, this.contentTypeFile(file.getFilename()))
				.body(file);
	}

	private String contentTypeFile(String fileName) {
	    Tika tika = new Tika();
		return tika.detect(fileName);
	}
}
