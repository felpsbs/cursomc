package br.com.fb.cursomc.dto;

import br.com.fb.cursomc.domain.Cidade;

public class CidadeDTO {

	private Integer id;
	private String nome;

	public CidadeDTO() {

	}

	public CidadeDTO(Cidade obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
