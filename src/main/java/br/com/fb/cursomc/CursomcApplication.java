package br.com.fb.cursomc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.fb.cursomc.domain.Categoria;
import br.com.fb.cursomc.domain.Cidade;
import br.com.fb.cursomc.domain.Cliente;
import br.com.fb.cursomc.domain.Endereco;
import br.com.fb.cursomc.domain.Estado;
import br.com.fb.cursomc.domain.Pagamento;
import br.com.fb.cursomc.domain.PagamentoComBoleto;
import br.com.fb.cursomc.domain.PagamentoComCartao;
import br.com.fb.cursomc.domain.Pedido;
import br.com.fb.cursomc.domain.Produto;
import br.com.fb.cursomc.domain.enums.EstadoPagamento;
import br.com.fb.cursomc.domain.enums.TipoCliente;
import br.com.fb.cursomc.repositories.CategoriaRepository;
import br.com.fb.cursomc.repositories.CidadeRepository;
import br.com.fb.cursomc.repositories.ClienteRepository;
import br.com.fb.cursomc.repositories.EnderecoRepository;
import br.com.fb.cursomc.repositories.EstadoRepository;
import br.com.fb.cursomc.repositories.PagamentoRepository;
import br.com.fb.cursomc.repositories.PedidoRepository;
import br.com.fb.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository; 
	
	@Autowired
	private ProdutoRepository produtoRepository; 
	
	@Autowired
	private CidadeRepository cidadeRepository; 
	
	@Autowired
	private EstadoRepository estadoRepository; 
	
	@Autowired
	private ClienteRepository clienteRepository; 
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository; 
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	// Para inserir elementos no banco
	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.0);
		Produto p2 = new Produto(null, "Impressora", 800.0);
		Produto p3 = new Produto(null, "Mouse", 80.0);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().add(cat1);
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().add(cat1);
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().add(c1);
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOA_FISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime instante1 = LocalDateTime.parse("30/09/2017 10:32", formatter);
		LocalDateTime instante2 = LocalDateTime.parse("10/10/2017 19:35", formatter);
		LocalDateTime instante3 = LocalDateTime.parse("20/10/2017 00:00", formatter);
				
		Pedido ped1 = new Pedido(null, instante1, cli1, e1);
		Pedido ped2 = new Pedido(null, instante2, cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, instante3, null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
	}

}
