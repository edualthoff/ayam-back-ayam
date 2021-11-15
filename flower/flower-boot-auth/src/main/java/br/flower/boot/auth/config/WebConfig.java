package br.flower.boot.auth.config;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.flower.boot.auth.security.filter.ClientOauthTokenInterceptor;
import br.flower.boot.auth.util.JacksonEnumConverter;


@Order(value = 1)
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private ClientOauthTokenInterceptor clientOauthTokenInterceptor;

	@Autowired
	private JacksonEnumConverter jacksonEnumConverter;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(clientOauthTokenInterceptor).addPathPatterns("/oauth/**").addPathPatterns("/teste/");
	}
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(jacksonEnumConverter);
	}
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        //return new BCryptPasswordEncoder();
        return PasswordEncoderFactories.createDelegatingPasswordEncoder(); // with new spring security 5
    }
    
    @Bean // Makes ZonedDateTime compatible with auditing fields
    public DateTimeProvider auditingDateTimeProvider() {
        return () -> Optional.of(OffsetDateTime.now());
    }
}
