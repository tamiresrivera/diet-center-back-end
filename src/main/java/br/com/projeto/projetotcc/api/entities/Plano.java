package br.com.projeto.projetotcc.api.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "plano")
public class Plano {
	
	private Long id;
	
	private String hora;
	
	private String descricao;
	
	private String observacao;
	
	private Profissional profissional;
	
	private Paciente paciente;
	
	private CategoriaPlanoAlimentar categoriaPlanoAlimentar;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "hora", nullable = false)
	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	@Column(name = "descricao", nullable = false)
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

	@ManyToOne
    @JoinColumn(name = "profissional_id")
	public Profissional getProfissional() {
		return profissional;
	}

	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}

	@ManyToOne
    @JoinColumn(name = "paciente_id")
	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	@ManyToOne
    @JoinColumn(name = "categoria_plano_alimentar_id")
	public CategoriaPlanoAlimentar getCategoriaPlanoAlimentar() {
		return categoriaPlanoAlimentar;
	}

	public void setCategoriaPlanoAlimentar(CategoriaPlanoAlimentar categoriaPlanoAlimentar) {
		this.categoriaPlanoAlimentar = categoriaPlanoAlimentar;
	}

}
