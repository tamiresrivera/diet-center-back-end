package br.com.projeto.projetotcc.api.dtos;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PlanoDto {
	
	private Optional<Long> id = Optional.empty();
	private String hora;
	private String descricao;
	private String observacao;
	private Long profissionalId;
	private Long pacienteId;
	private Long categoriaPlanoAlimentarId;
	private String descricaoCategoria;
	private Long usuarioPacienteId;
	
	public PlanoDto() {
	}

	public Optional<Long> getId() {
		return id;
	}

	public void setId(Optional<Long> id) {
		this.id = id;
	}

	@NotEmpty(message = "Hora não pode ser vazia.")
	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	@NotEmpty(message = "Descrição não pode ser vazia.")
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@NotNull(message = "Profissional não pode ser vazio.")
	public Long getProfissionalId() {
		return profissionalId;
	}

	public void setProfissionalId(Long profissionalId) {
		this.profissionalId = profissionalId;
	}

	@NotNull(message = "Paciente não pode ser vazio.")
	public Long getPacienteId() {
		return pacienteId;
	}

	public void setPacienteId(Long pacienteId) {
		this.pacienteId = pacienteId;
	}
	
	@NotNull(message = "Categoria não pode ser vazio.")
	public Long getCategoriaPlanoAlimentarId() {
		return categoriaPlanoAlimentarId;
	}

	public void setCategoriaPlanoAlimentarId(Long categoriaPlanoAlimentarId) {
		this.categoriaPlanoAlimentarId = categoriaPlanoAlimentarId;
	}
	
	public String getDescricaoCategoria() {
		return descricaoCategoria;
	}

	public void setDescricaoCategoria(String descricaoCategoria) {
		this.descricaoCategoria = descricaoCategoria;
	}
	
	public Long getUsuarioPacienteId() {
		return usuarioPacienteId;
	}

	public void setUsuarioPacienteId(Long usuarioPacienteId) {
		this.usuarioPacienteId = usuarioPacienteId;
	}
	
	@Override
	public String toString() {
		return "PlanoDto [id=" + id + ", hora=" + hora + ", descricao=" + descricao
				+ ", observacao=" + observacao + ", profissionalId=" + profissionalId + ", pacienteId=" + pacienteId 
				+ ",categoriaPlanoAlimentarId=" + categoriaPlanoAlimentarId + ", descricaoCategoria=" + descricaoCategoria
				+ ",usuarioPacienteId" + usuarioPacienteId + "]";
	}

}
