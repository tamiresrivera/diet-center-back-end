package br.com.projeto.projetotcc.api.dtos;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ReceitaDto {
	
	private Optional<Long> id = Optional.empty();
	private String titulo;
	private String descricao;
	private Long profissionalId;
	private Long categoriaReceitaId;
	private String categoriaReceitaDescricao;
	
	public ReceitaDto() {
	}
	
	public Optional<Long> getId() {
		return id;
	}

	public void setId(Optional<Long> id) {
		this.id = id;
	}
	
	@NotEmpty(message = "Título não pode ser vazia.")
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	@NotEmpty(message = "Descrição não pode ser vazia.")
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@NotNull(message = "Categoria não pode ser vazio.")
	public Long getCategoriaReceitaId() {
		return categoriaReceitaId;
	}

	public void setCategoriaReceitaId(Long categoriaReceitaId) {
		this.categoriaReceitaId = categoriaReceitaId;
	}
	
	public String getCategoriaReceitaDescricao() {
		return categoriaReceitaDescricao;
	}

	public void setCategoriaReceitaDescricao(String categoriaReceitaDescricao) {
		this.categoriaReceitaDescricao = categoriaReceitaDescricao;
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
		return "CategoriaReceitaDto [id=" + id + ", titulo=" + titulo + ", descricao=" + 
	            descricao + ", profissionalId=" + profissionalId + ", categoriaReceitaId=" +
				categoriaReceitaId + ", categoriaReceitaDescricao=" + categoriaReceitaDescricao + "]";
	}

}
