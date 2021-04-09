package br.com.projeto.projetotcc.api.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "profissional")
public class Profissional {
	
	private Long id;
	
	private String descricao;
	
	private int crn;
	
	private String endereco;
	
	private Long numero;
	
	private Long cep;
	
	private String complemento;
	
	private String cidade;
	
	private String estado;
	
	@Column(name="midia_social")
	private String midiaSocial;
	
	private Usuario usuario;
	
    private List<Paciente> pacientes = new ArrayList<>();

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setCrn(int crn) {
		this.crn = crn;
	}
	
	@Column(name = "crn")
	public int getCrn() {
		return crn;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Long getCep() {
		return cep;
	}

	public void setCep(Long cep) {
		this.cep = cep;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getMidiaSocial() {
		return midiaSocial;
	}

	public void setMidiaSocial(String midia_social) {
		this.midiaSocial = midia_social;
	}

	@OneToOne
    @JoinColumn(name = "usuario_id")
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@OneToMany(mappedBy="profissional")
	public List<Paciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

}
