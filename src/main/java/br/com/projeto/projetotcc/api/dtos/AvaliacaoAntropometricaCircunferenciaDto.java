package br.com.projeto.projetotcc.api.dtos;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AvaliacaoAntropometricaCircunferenciaDto {
	
	private Optional<Long> id = Optional.empty();
	private String valor;
	private Long categoriaAvaliacaoCircunferenciaId;
	private String descricaoCategoria;
	private Long avaliacaoAntropometricaId;
	
	public AvaliacaoAntropometricaCircunferenciaDto() {
	}

	public Optional<Long> getId() {
		return id;
	}

	public void setId(Optional<Long> id) {
		this.id = id;
	}

	@NotEmpty(message = "Valor não pode ser vazio.")
	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Long getAvaliacaoAntropometricaId() {
		return avaliacaoAntropometricaId;
	}

	public void setAvaliacaoAntropometricaId(Long avaliacaoAntropometricaId) {
		this.avaliacaoAntropometricaId = avaliacaoAntropometricaId;
	}
	
	@NotNull(message = "Categoria não pode ser vazia.")
	public Long getCategoriaAvaliacaoCircunferenciaId() {
		return categoriaAvaliacaoCircunferenciaId;
	}

	public void setCategoriaAvaliacaoCircunferenciaId(Long categoriaAvaliacaoCircunferenciaId) {
		this.categoriaAvaliacaoCircunferenciaId = categoriaAvaliacaoCircunferenciaId;
	}
	
	public String getDescricaoCategoria() {
		return descricaoCategoria;
	}

	public void setDescricaoCategoria(String descricaoCategoria) {
		this.descricaoCategoria = descricaoCategoria;
	}
	
	@Override
	public String toString() {
		return "AvaliacaoAntropometricaCircunferenciaDto [id=" + id + ", valor=" + valor
				+  ", avaliacaoAntropometricaId=" + avaliacaoAntropometricaId 
				+ ",categoriaAvaliacaoCircunferenciaId=" + categoriaAvaliacaoCircunferenciaId + "]";
	}

}
