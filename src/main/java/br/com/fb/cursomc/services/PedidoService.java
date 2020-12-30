package br.com.fb.cursomc.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fb.cursomc.domain.ItemPedido;
import br.com.fb.cursomc.domain.PagamentoComBoleto;
import br.com.fb.cursomc.domain.Pedido;
import br.com.fb.cursomc.domain.enums.EstadoPagamento;
import br.com.fb.cursomc.repositories.ItemPedidoRepository;
import br.com.fb.cursomc.repositories.PagamentoRepository;
import br.com.fb.cursomc.repositories.PedidoRepository;
import br.com.fb.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository rep;
	
	@Autowired
	private PagamentoRepository pagamentoRep;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRep;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = rep.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! ID: " + id +", Tipo: " + Pedido.class.getName()));
	}

	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(LocalDateTime.now());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		// colocando a data de vencimento
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		
		obj = rep.save(obj);
		pagamentoRep.save(obj.getPagamento());
		
		// salvando os itens de pedido
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		
		itemPedidoRep.saveAll(obj.getItens());
		return obj;
	}
	
}
