package br.com.fb.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fb.cursomc.domain.Pedido;
import br.com.fb.cursomc.repositories.PedidoRepository;
import br.com.fb.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository rep;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = rep.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! ID: " + id +", Tipo: " + Pedido.class.getName()));
	}
	
}
