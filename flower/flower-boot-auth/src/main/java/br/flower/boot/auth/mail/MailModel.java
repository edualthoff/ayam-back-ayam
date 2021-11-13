package br.flower.boot.auth.mail;

import java.util.Map;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Data
public class MailModel {

    @Getter
    @Setter
    public static class HtmlTemplate {
    	 /** Nome do template - EX: do HTML */
        private String template;
        /** Nome da variavel e o valor que será implemtado no template */
        private Map<String, Object> props;

        public HtmlTemplate(String template, Map<String, Object> props) {
            this.template = template;
            this.props = props;
        }
    }

    
    public MailModel(String from, String to, String subject, HtmlTemplate htmlTemplate) {
		super();
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.htmlTemplate = htmlTemplate;
	}
    
    
	public MailModel(String to, String subject, HtmlTemplate htmlTemplate) {
		super();
		this.to = to;
		this.subject = subject;
		this.htmlTemplate = htmlTemplate;
	}


	/** Destinatario - quem esta enviando */
    private String from;
    /** Destinatario - para quem vai o email */
    private String to;
    /** Assunto do email */
    private String subject;
    private HtmlTemplate htmlTemplate;
}
