package br.com.projeto.projetotcc.api.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "paciente")
public class Paciente {
	
	private Long id;

	private Usuario usuario;
	
	private Profissional profissional;
	
	private List<Plano> planos = new ArrayList<>();
	
	private String observacao;
	
	private CategoriaReceita categoriaReceita;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToOne
    @JoinColumn(name = "usuario_id")
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@ManyToOne
    @JoinColumn(name = "profissional_id")
	public Profissional getProfissional() {
		return profissional;
	}

	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}

	@OneToMany(mappedBy="paciente")
	public List<Plano> getPlanos() {
		return planos;
	}

	public void setPlanos(List<Plano> planos) {
		this.planos = planos;
	}
	
	@OneToOne
    @JoinColumn(name = "categoria_receita_id")
	public CategoriaReceita getCategoriaReceita() {
		return categoriaReceita;
	}

	public void setCategoriaReceita(CategoriaReceita categoriaReceita) {
		this.categoriaReceita = categoriaReceita;
	}
	
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
}
