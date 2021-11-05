package br.aym.base.core;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private JacksonEnumConverter jacksonEnumConverter;

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(jacksonEnumConverter);
	}

	@Bean(name = "auditingDateTimeProvider") // Makes ZonedDateTime compatible with auditing fields
	public DateTimeProvider auditingDateTimeProvider() {
		System.out.println(" date "+OffsetDateTime.now());
		return () -> Optional.of(OffsetDateTime.now());
	}
	
}
