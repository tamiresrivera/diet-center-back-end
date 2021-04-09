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
@Table(name = "receita")
public class Receita {
	
	private Long id;
	
	private String titulo;

	private String descricao;
	
	private Profissional profissional;
	
	private CategoriaReceita categoriaReceita;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "titulo", nullable = false)
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	@Column(name = "descricao", nullable = false)
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
    @JoinColumn(name = "categoria_receita_id")
	public CategoriaReceita getCategoriaReceita() {
		return categoriaReceita;
	}

	public void setCategoriaReceita(CategoriaReceita categoriaReceita) {
		this.categoriaReceita = categoriaReceita;
	}

}
