package br.com.fb.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends AbstractEmailService {
	
	private static Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class); 

	@Autowired
	private MailSender sender;
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Enviando envio de email...");
		sender.send(msg);
		LOG.info("Email enviado");
	}

}
