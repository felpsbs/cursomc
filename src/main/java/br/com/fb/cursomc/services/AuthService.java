package br.com.fb.cursomc.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fb.cursomc.domain.Cliente;
import br.com.fb.cursomc.repositories.ClienteRepository;
import br.com.fb.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository rep;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private EmailService emailService;

	public void sendNewPassword(String email) {
		Cliente cliente = rep.findByEmail(email);
		if (cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}

		String newPass = newPassword();
		cliente.setSenha(encoder.encode(newPass));
		rep.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPass);
	}

	private String newPassword() {
		return RandomStringUtils.randomAlphanumeric(10);
	}

}
