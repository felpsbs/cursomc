package br.com.fb.cursomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.fb.cursomc.domain.Cliente;
import br.com.fb.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String  sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired(required = false)
	private JavaMailSender javaMailSender;
	
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
	
	protected String htmlFromTemplatePedido(Pedido obj) {
		Context context = new Context();
		context.setVariable("pedido", obj);
		return templateEngine.process("email/confirmacaoPedido", context);
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		try {
			MimeMessage mm = prepareMimeMessageFromPedido(obj);
			sendHtmlEmail(mm);
		} catch (MessagingException e) {			
			sendOrderConfirmationEmail(obj);
		}	
	}

	protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper  mmh = new MimeMessageHelper(mimeMessage, true); 
		mmh.setTo(obj.getCliente().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Pedido confirmado: código: " + obj.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePedido(obj), true);
		return mimeMessage;
	}
	
	@Override
	public void sendNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage message = prepareNewPasswordEmail(cliente, newPass);	
		sendEmail(message);
	}

	protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(cliente.getEmail());
		message.setFrom(sender);
		message.setSubject("Solicitação de nova senha");
		message.setSentDate(new Date(System.currentTimeMillis()));
		message.setText("Sua nova senha: " + newPass);
		return message;
	}
	
}
