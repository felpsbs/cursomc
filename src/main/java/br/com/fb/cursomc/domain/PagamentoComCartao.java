package br.com.fb.cursomc.domain;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;

import br.com.fb.cursomc.domain.enums.EstadoPagamento;

@Entity // nas subclasses basta coloca o @Entity
@JsonTypeName("pagamentoComCartao")
public class PagamentoComCartao extends Pagamento {

	private static final long serialVersionUID = 1L;

	private Integer numeroDeParcelas;

	public PagamentoComCartao() {
	}

	public PagamentoComCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas) {
		super(id, estado, pedido);
		this.numeroDeParcelas = numeroDeParcelas;
	}

	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}

}
