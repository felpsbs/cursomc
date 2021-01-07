package br.com.fb.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import br.com.fb.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String  sender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage message = prepareSimpleMailMessageFromPedido(obj);	
		sendEmail(message);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(obj.getCliente().getEmail());
		message.setFrom(sender);
		message.setSubject("Pedido confirmado: código: " + obj.getId());
		message.setSentDate(new Date(System.currentTimeMillis()));
		message.setText(obj.toString());
		return message;
	}
	
}
