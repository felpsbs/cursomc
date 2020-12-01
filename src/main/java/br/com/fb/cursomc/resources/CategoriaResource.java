package br.com.fb.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.fb.cursomc.domain.Categoria;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@RequestMapping(method = RequestMethod.GET)
	public List<Categoria> listar() {
		Categoria c1 = new Categoria(1,"Nova");
		Categoria c2 = new Categoria(2,"Nova");
		
		List<Categoria> list = new ArrayList<>();
		list.add(c1);
		list.add(c2);
		
		return list; 
	}
	
}