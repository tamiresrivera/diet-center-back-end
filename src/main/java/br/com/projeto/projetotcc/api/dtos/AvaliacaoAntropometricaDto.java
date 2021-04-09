package br.com.projeto.projetotcc.api.dtos;

import java.util.Date;
import java.util.List;

public class AvaliacaoAntropometricaDto {
	
	public AvaliacaoAntropometricaDto() {
	}
	
	private Long id;
	private Date dataHora;
    private String altura;
    private String peso;
    private String imc;
    private String percentualMassaGorda;  
    private String percentualMassaMagra;
    private String massaGorda;
    private String massaMagra;
    private String pesoOsseo;
    private String pesoResidual;
    private String pesoMuscular;
    private String areaMuscularBraco;
    private String areaGorduraBraco;
    private Long pacienteId;
    private List<AvaliacaoAntropometricaCircunferenciaDto> circunferencias;
    private List<AvaliacaoAntropometricaDiametroOsseoDto> diametros;
    private List<AvaliacaoAntropometricaPregaDto> pregas;
    
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
	
	public String getAltura() {
		return altura;
	}
	
	public void setAltura(String altura) {
		this.altura = altura;
	}
	
	public String getPeso() {
		return peso;
	}
	public void setPeso(String peso) {
		this.peso = peso;
	}
	
	public String getImc() {
		return imc;
	}
	
	public void setImc(String imc) {
		this.imc = imc;
	}
	
	public String getPercentualMassaGorda() {
		return percentualMassaGorda;
	}
	
	public void setPercentualMassaGorda(String percentualMassaGorda) {
		this.percentualMassaGorda = percentualMassaGorda;
	}
	
	public String getPercentualMassaMagra() {
		return percentualMassaMagra;
	}
	
	public void setPercentualMassaMagra(String percentualMassaMagra) {
		this.percentualMassaMagra = percentualMassaMagra;
	}
	
	public String getMassaGorda() {
		return massaGorda;
	}
	
	public void setMassaGorda(String massaGorda) {
		this.massaGorda = massaGorda;
	}
	
	public String getMassaMagra() {
		return massaMagra;
	}
	
	public void setMassaMagra(String massaMagra) {
		this.massaMagra = massaMagra;
	}
	
	public String getPesoOsseo() {
		return pesoOsseo;
	}
	
	public void setPesoOsseo(String pesoOsseo) {
		this.pesoOsseo = pesoOsseo;
	}
	
	public String getPesoResidual() {
		return pesoResidual;
	}
	
	public void setPesoResidual(String pesoResidual) {
		this.pesoResidual = pesoResidual;
	}
	
	public String getPesoMuscular() {
		return pesoMuscular;
	}
	
	public void setPesoMuscular(String pesoMuscular) {
		this.pesoMuscular = pesoMuscular;
	}
	
	public String getAreaMuscularBraco() {
		return areaMuscularBraco;
	}
	
	public void setAreaMuscularBraco(String areaMuscularBraco) {
		this.areaMuscularBraco = areaMuscularBraco;
	}
	
	public String getAreaGorduraBraco() {
		return areaGorduraBraco;
	}
	
	public void setAreaGorduraBraco(String areaGorduraBraco) {
		this.areaGorduraBraco = areaGorduraBraco;
	}
	
	public Long getPacienteId() {
		return pacienteId;
	}
	
	public void setPacienteId(Long pacienteId) {
		this.pacienteId = pacienteId;
	}
	
	public List<AvaliacaoAntropometricaCircunferenciaDto> getCircunferencias() {
		return circunferencias;
	}

	public void setCircunferencias(List<AvaliacaoAntropometricaCircunferenciaDto> circunferencias) {
		this.circunferencias = circunferencias;
	}

	public List<AvaliacaoAntropometricaDiametroOsseoDto> getDiametros() {
		return diametros;
	}

	public void setDiametros(List<AvaliacaoAntropometricaDiametroOsseoDto> diametros) {
		this.diametros = diametros;
	}

	public List<AvaliacaoAntropometricaPregaDto> getPregas() {
		return pregas;
	}

	public void setPregas(List<AvaliacaoAntropometricaPregaDto> pregas) {
		this.pregas = pregas;
	}

	@Override
	public String toString() {
		return "AvaliacaoAntropometricaDto [Id=" + id + ", dataHora=" + dataHora + ", altura=" + altura +
				", peso=" + peso + ", imc=" + imc + ", percentualMassaGorda" + percentualMassaGorda +
				", percentualMassaMagra=" + percentualMassaMagra + ", massaGorda=" + massaGorda +
				", massaMagra=" + massaMagra + ", pesoOsseo=" + pesoOsseo + ", pesoResidual=" + pesoResidual +
		        ", pesoMuscular=" + pesoMuscular + ", areaMuscularBraco=" + areaMuscularBraco +
		        ", areaGorduraBraco=" + areaGorduraBraco + ", pacienteId=" + pacienteId + "]";
	}
   
}
