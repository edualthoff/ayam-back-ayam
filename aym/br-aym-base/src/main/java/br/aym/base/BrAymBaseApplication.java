package br.aym.base;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import br.aym.base.file.FilesStorageService;

@EnableTransactionManagement
@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = {"br.aym.base.*", "br.flower.boot.exception.*"})
public class BrAymBaseApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BrAymBaseApplication.class, args);
	}

	  @Resource
	  FilesStorageService storageService;

	  @Override
	  public void run(String... arg) throws Exception {
	    storageService.init();
	  }
}