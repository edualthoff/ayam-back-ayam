package br.flower.boot.auth.config;

import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * Configurar Thymeleaf Template para o envio de email
 * @author edu
 *
 */
@Configuration
public class ThymeleafTemplateConfig {

	
    @Bean
    public SpringTemplateEngine springTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(emailHtmlTemplateResolver());
        return templateEngine;
    }

    /*
     * Carrega os templates HTML da pasta email 
     */
    @Bean
    public ClassLoaderTemplateResolver emailHtmlTemplateResolver(){
        ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
        emailTemplateResolver.setPrefix("/templates/email/");
        emailTemplateResolver.setSuffix(".html");
        emailTemplateResolver.setTemplateMode(TemplateMode.HTML);
        emailTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        emailTemplateResolver.setCacheable(false);
        emailTemplateResolver.setOrder(1);
        emailTemplateResolver.setCheckExistence(true);
        return emailTemplateResolver;
    }
}