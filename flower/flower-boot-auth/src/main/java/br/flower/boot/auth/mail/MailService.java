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

import br.flower.boot.auth.mail.view.MailTemplateMount;

/**
 * Class para Enviar email @async 
 * @author edu
 *
 */
@Service
public class MailService {
	private static final Logger log = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private JavaMailSender emailSender;
    
	@Autowired()
	private SpringTemplateEngine springTemplateEngine;
	
	
	/** Envio de email com template HTML Thymeleaf
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
	        String htmlTemplate = getHtmlContent(mail);

	        // System.out.println("email send "+mail.toString());
	        // endereco de envio
	        // helper.setFrom(mail.getFrom());
	        // endereco de destino
	        helper.setTo(mail.getTo());
	        // assunto do email
	        helper.setSubject(mail.getSubject());
	        // corpo do email
	        helper.setText(htmlTemplate, true);
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
