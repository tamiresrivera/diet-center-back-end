package br.com.projeto.projetotcc.api.dtos;

import java.util.Date;

public class AgendaDto {
	
	private Long id;
	private Date dataHora;
	private Long profissionalId;
	private Long pacienteId;
	private String nomePaciente;
	
	public AgendaDto() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}
	
	public Long getProfissionalId() {
		return profissionalId;
	}

	public void setProfissionalId(Long profissionalId) {
		this.profissionalId = profissionalId;
	}

	public Long getPacienteId() {
		return pacienteId;
	}

	public void setPacienteId(Long pacienteId) {
		this.pacienteId = pacienteId;
	}
	
	public String getNomePaciente() {
		return nomePaciente;
	}

	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}
	
	@Override
	public String toString() {
		return "AgendaDto [id=" + id + ", dataHora=" + dataHora +
				", profissionalId=" + profissionalId + ", pacienteId=" + pacienteId + ", nomePaciente=" + nomePaciente + "]";
	}

}
