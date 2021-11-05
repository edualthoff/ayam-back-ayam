package br.flower.boot.auth.mail;

import java.io.Serializable;

import br.flower.boot.auth.mail.model.MailModel;

public interface MailTemplateMount extends Serializable{

	MailModel mount();
}
