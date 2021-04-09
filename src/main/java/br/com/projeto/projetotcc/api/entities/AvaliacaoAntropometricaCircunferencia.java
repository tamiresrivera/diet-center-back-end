package br.com.projeto.projetotcc.api.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "avaliacao_antropometrica_circunferencia")
public class AvaliacaoAntropometricaCircunferencia {
	
	private Long id;
	
	private double valor;
	
	private AvaliacaoAntropometrica avaliacaoAntropometrica;
	
	private CategoriaAvaliacaoCircunferencia categoriaAvaliacaoCircunferencia;
	
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
    @JoinColumn(name = "categoria_avaliacao_circunferencia_id")
	public CategoriaAvaliacaoCircunferencia getCategoriaAvaliacaoCircunferencia() {
		return categoriaAvaliacaoCircunferencia;
	}

	public void setCategoriaAvaliacaoCircunferencia(CategoriaAvaliacaoCircunferencia categoriaAvaliacaoCircunferencia) {
		this.categoriaAvaliacaoCircunferencia = categoriaAvaliacaoCircunferencia;
	}

}
