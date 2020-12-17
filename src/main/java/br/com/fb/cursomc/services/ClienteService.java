package br.com.fb.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fb.cursomc.domain.Cliente;
import br.com.fb.cursomc.repositories.ClienteRepository;
import br.com.fb.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository rep;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = rep.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! ID: " + id +", Tipo: " + Cliente.class.getName()));
	}
	
}
