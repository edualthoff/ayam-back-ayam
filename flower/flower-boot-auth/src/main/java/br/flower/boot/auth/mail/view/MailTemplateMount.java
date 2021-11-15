package br.flower.boot.auth.mail.view;

import java.io.Serializable;

import br.flower.boot.auth.mail.MailModel;

public interface MailTemplateMount extends Serializable{

	MailModel mount();
}
