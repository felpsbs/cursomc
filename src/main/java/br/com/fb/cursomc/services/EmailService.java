package br.com.fb.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import br.com.fb.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
