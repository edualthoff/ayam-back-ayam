package br.aym.base.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


	

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.exceptionHandling().authenticationEntryPoint(null).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/", 
						"/*.html", 
						"/favicon.ico",
						"/**/*.html",
						"/**/*.css", 
						"/**/*.js").permitAll()
				.antMatchers("/oauth/**").permitAll()
				.antMatchers("/teste/").permitAll()
				.anyRequest().authenticated().and()
				.csrf().disable()
				//.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
				.formLogin().disable()
				.headers().cacheControl();
	}
	
    @Override
	public void configure(WebSecurity http) throws Exception {
		http
			.ignoring().antMatchers("/api/rest/**")
			.antMatchers("/api/upload/**")
			.antMatchers("/caracteristica/**")
			.antMatchers("/adm/caracteristica/**")
			.antMatchers("/adm/produto/**")
			.antMatchers("/adm/informativo/**")
			.antMatchers("/informativo/**")
			.antMatchers("/produto/**").antMatchers("/error/**");
    }

}
