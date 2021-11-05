package br.flower.boot.auth.upload;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileStorage implements Serializable{
	private static final long serialVersionUID = -4277104509010559519L;
	private static final Logger log = LogManager.getLogger(UploadFileService.class);

	/*
	 * Separa o tipo de arquivo, nome e o binario do arquivo a ser armazenado no BD.
	 *  Retorna um tipo @Param UploadFile
	 */
	 public UploadFile storeFile(MultipartFile file) throws IOException {
		 // Normalize file name
	     String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	     try {
	    	 // Check if the file's name contains invalid characters
	         if(fileName.contains("..")) {
	          	log.warn("Error! O arquivo tem sequencias invalidas no nome. " + fileName);
	           	throw new IOException("Error! O arquivo tem sequencias invalidas no nome. " + fileName);
	         }
	         UploadFile upFile = new UploadFile(fileName, file.getContentType(), file.getBytes());
	         return upFile;
	     } catch (IOException ex) {
	       	log.error("Não foi possível armazenar o arquivo " + fileName + ". Tente mais tarde!", ex);
	       	throw new IOException("Não foi possível armazenar o arquivo " + fileName + ". Tente mais tarde!", ex);
	     }
	 }
	 /*
	  * Baixa a Imagem da URL informada e implementa a class @param UploadFile
	  * @Param String tipo - tipo de arquivo exemplo image/jpg
	  * @Param String nome - nome do arquivo exemplo photo.jpg
	  * @Param Strin link - link do arquivo a ser baixado
	  */
	 public UploadFile baixarArquivoUrl(String tipo, String nome, String link) throws IOException {
		 InputStream in = null;
		 try {
			URL url = new URL(link);
			in = new BufferedInputStream(url.openStream());
					    
			UploadFile upFile = new UploadFile(FileTypeEnum.valueOf(tipo.toUpperCase()).getTipo(), nome, in.readAllBytes());
			
			return upFile;
		} catch (MalformedURLException e) {
			log.error("Erro ao baixar a imagem do link ou link informado errado " + e);
			throw new MalformedURLException("Erro ao baixar a imagem do link ou link errado");
		} catch (IOException e) {
			log.error("Erro ao gerar o InputStream " + e);
			throw new IOException("Erro ao gerar o InputStream " + e);
		} finally {
			in.close();
	    }	 
	}
	 
	 public static Optional<UploadFile> uploadFile(String tipoArquivo, String nome, String link) {
		 FileStorage fs = new FileStorage();
		 try {
			return Optional.ofNullable(fs.baixarArquivoUrl(tipoArquivo, nome, link));
		} catch (IOException e) {
			log.error("Error ao baixar o arquiv, {}", e);
			return null;
		}
	 }
}
