package br.com.fb.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fb.cursomc.domain.Categoria;
import br.com.fb.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository rep;
	
	public Categoria buscar(Integer id) {
		return rep.findById(id).orElse(null);
	}
	
}
