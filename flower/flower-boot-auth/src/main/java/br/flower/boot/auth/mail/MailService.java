package br.flower.boot.auth.mail;

import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import br.flower.boot.auth.mail.model.MailModel;

/**
 * Enviar email @async 
 * @author edu
 *
 */
@Service
public class MailService {
	private static final Logger log = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private JavaMailSender emailSender;
    
	@Autowired()
	//@Qualifier("emailSpringTemplateEngine")
	private SpringTemplateEngine springTemplateEngine;
	
	
	/**
	 * Template - spring boot thymeleaf
	 * @param mailTemplateMount
	 */
    @Async("threadMaling")
    public void sendMailTemplate(MailTemplateMount mailTemplateMount) {
    	MailModel mail = mailTemplateMount.mount();
    	
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message,
			        MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
			        StandardCharsets.UTF_8.name());
	        String html = getHtmlContent(mail);

	        helper.setTo(mail.getTo());
	        helper.setFrom(mail.getFrom());
	        helper.setSubject(mail.getSubject());
	        helper.setText(html, true);
	        emailSender.send(message);
	        
		} catch (MessagingException e) {
			log.error("Envio de email, {}", e);
		}
    }
    
    private String getHtmlContent(MailModel mail) {
        Context context = new Context();
        context.setVariables(mail.getHtmlTemplate().getProps());
        return springTemplateEngine.process(mail.getHtmlTemplate().getTemplate(), context);
    }
}
