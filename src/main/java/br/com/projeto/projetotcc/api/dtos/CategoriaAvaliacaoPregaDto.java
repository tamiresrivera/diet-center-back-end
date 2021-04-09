package br.com.projeto.projetotcc.api.dtos;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CategoriaAvaliacaoPregaDto {
	
	private Optional<Long> id = Optional.empty();
	private String descricao;
	private Long profissionalId;
	
	public CategoriaAvaliacaoPregaDto() {
	}
	
	public Optional<Long> getId() {
		return id;
	}

	public void setId(Optional<Long> id) {
		this.id = id;
	}
	
	@NotEmpty(message = "Descrição não pode ser vazia.")
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@NotNull(message = "Profissional não pode ser vazio.")
	public Long getProfissionalId() {
		return profissionalId;
	}

	public void setProfissionalId(Long profissionalId) {
		this.profissionalId = profissionalId;
	}
	
	@Override
	public String toString() {
		return "CategoriaAvaliacaoPregaDto [id=" + id + ", descricao=" + descricao + ", profissionalId=" + profissionalId + "]";
	}

}
