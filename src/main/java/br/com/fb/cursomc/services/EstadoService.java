package br.com.fb.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fb.cursomc.domain.Estado;
import br.com.fb.cursomc.repositories.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository rep;
	
	public List<Estado> findAll() {
		return rep.findAllByOrderByNome();
	}
	
}
