package br.flower.boot.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.flower.boot.auth.security.filter.ClientOauthTokenInterceptor;


@Order(value = 1)
@Configuration
public class AppConfig implements WebMvcConfigurer {

	@Autowired
	private ClientOauthTokenInterceptor clientOauthTokenInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(clientOauthTokenInterceptor).addPathPatterns("/oauth/**");
	}
	
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        //return new BCryptPasswordEncoder();
        return PasswordEncoderFactories.createDelegatingPasswordEncoder(); // with new spring security 5
    }
}
