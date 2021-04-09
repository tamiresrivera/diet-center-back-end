package br.com.projeto.projetotcc.api.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "avaliacao_antropometrica_prega")
public class AvaliacaoAntropometricaPrega {
	
	private Long id;
	
	private double valor;
	
	private AvaliacaoAntropometrica avaliacaoAntropometrica;
	
	private CategoriaAvaliacaoPrega categoriaAvaliacaoPrega;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
	
	@ManyToOne
    @JoinColumn(name = "avaliacao_antropometrica_id")
	public AvaliacaoAntropometrica getAvaliacaoAntropometrica() {
		return avaliacaoAntropometrica;
	}

	public void setAvaliacaoAntropometrica(AvaliacaoAntropometrica avaliacaoAntropometrica) {
		this.avaliacaoAntropometrica = avaliacaoAntropometrica;
	}
	
	@ManyToOne
    @JoinColumn(name = "categoria_avaliacao_prega_id")
	public CategoriaAvaliacaoPrega getCategoriaAvaliacaoPrega() {
		return categoriaAvaliacaoPrega;
	}

	public void setCategoriaAvaliacaoPrega(CategoriaAvaliacaoPrega categoriaAvaliacaoPrega) {
		this.categoriaAvaliacaoPrega = categoriaAvaliacaoPrega;
	}

}
