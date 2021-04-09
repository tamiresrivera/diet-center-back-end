package br.com.projeto.projetotcc.api.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "avaliacao_antropometrica")
public class AvaliacaoAntropometrica {
	
	private Long id;

	private Date dataHora;
	
    private double altura;
    
    private double peso;
    
    private double imc;
    
    private double percentualMassaGorda;
    
    private double percentualMassaMagra;
    
    private double massaGorda;
    
    private double massaMagra;
    
    private double pesoOsseo;
    
    private double pesoResidual;
    
    private double pesoMuscular;
    
    private double areaMuscularBraco;
    
    private double areaGorduraBraco;
    
    private Paciente paciente;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "data_hora", nullable = false)
	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}
	
	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}
	
	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}
	
	public double getImc() {
		return imc;
	}

	public void setImc(double imc) {
		this.imc = imc;
	}
	
	@Column(name = "percentual_massa_gorda")
	public double getPercentualMassaGorda() {
		return percentualMassaGorda;
	}

	public void setPercentualMassaGorda(double percentualMassaGorda) {
		this.percentualMassaGorda = percentualMassaGorda;
	}
	
	@Column(name = "percentual_massa_magra")
	public double getPercentualMassaMagra() {
		return percentualMassaMagra;
	}

	public void setPercentualMassaMagra(double percentualMassaMagra) {
		this.percentualMassaMagra = percentualMassaMagra;
	}
	
	@Column(name = "massa_gorda")
	public double getMassaGorda() {
		return massaGorda;
	}

	public void setMassaGorda(double massaGorda) {
		this.massaGorda = massaGorda;
	}
	
	@Column(name = "massa_magra")
	public double getMassaMagra() {
		return massaMagra;
	}

	public void setMassaMagra(double massaMagra) {
		this.massaMagra = massaMagra;
	}
	
	@Column(name = "peso_osseo")
	public double getPesoOsseo() {
		return pesoOsseo;
	}

	public void setPesoOsseo(double pesoOsseo) {
		this.pesoOsseo = pesoOsseo;
	}
	
	@Column(name = "peso_residual")
	public double getPesoResidual() {
		return pesoResidual;
	}

	public void setPesoResidual(double pesoResidual) {
		this.pesoResidual = pesoResidual;
	}
	
	@Column(name = "peso_muscular")
	public double getPesoMuscular() {
		return pesoMuscular;
	}

	public void setPesoMuscular(double pesoMuscular) {
		this.pesoMuscular = pesoMuscular;
	}
	
	@Column(name = "area_muscular_braco")
	public double getAreaMuscularBraco() {
		return areaMuscularBraco;
	}

	public void setAreaMuscularBraco(double areaMuscularBraco) {
		this.areaMuscularBraco = areaMuscularBraco;
	}
	
	@Column(name = "area_gordura_braco")
	public double getAreaGorduraBraco() {
		return areaGorduraBraco;
	}

	public void setAreaGorduraBraco(double areaGorduraBraco) {
		this.areaGorduraBraco = areaGorduraBraco;
	}
	
	@ManyToOne
    @JoinColumn(name = "paciente_id")
	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

}
