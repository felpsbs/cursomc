package br.com.fb.cursomc.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import br.com.fb.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, LocalDateTime instante) {
		pagto.setDataVencimento(instante.plusDays(7));
	}

}
