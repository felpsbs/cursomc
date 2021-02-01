package br.com.fb.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fb.cursomc.domain.Cidade;
import br.com.fb.cursomc.repositories.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository rep;
	
	public List<Cidade> findByEstado(Integer estadoId) {
		return rep.findCidades(estadoId);
	}
	
}
