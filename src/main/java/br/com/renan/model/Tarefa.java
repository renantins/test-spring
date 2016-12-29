package br.com.renan.model;

import java.util.Calendar;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Tarefa {
	
	private Long id;
	@NotNull @Size(min=5)
	private String descricao;
	private boolean finalizando;
	private Calendar dataFinalizacao;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public boolean isFinalizando() {
		return finalizando;
	}
	public void setFinalizando(boolean finalizando) {
		this.finalizando = finalizando;
	}
	public Calendar getDataFinalizacao() {
		return dataFinalizacao;
	}
	public void setDataFinalizacao(Calendar dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}
	
}
