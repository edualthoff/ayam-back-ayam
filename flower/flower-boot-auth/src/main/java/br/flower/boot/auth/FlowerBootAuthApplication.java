package br.flower.boot.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = {"br.flower.boot.auth.*", "br.flower.boot.exception.*"})
public class FlowerBootAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowerBootAuthApplication.class, args);
	}

}
