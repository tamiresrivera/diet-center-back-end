package br.com.projeto.projetotcc.api.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;


public class PacienteDto {
	
	private Long id;
	private Long usuarioId;
	private String observacao;
	private String nome;
	private String email;
	private Long telefone;
	private String cpf;
	private String senha;
	private Long categoriaReceitaId;

	public PacienteDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
	
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@NotEmpty(message = "Nome não pode ser vazio.")
	@Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
	public String getNome() {
		return nome;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@NotEmpty(message = "Email não pode ser vazio.")
	@Length(min = 5, max = 200, message = "Email deve conter entre 5 e 200 caracteres.")
	@Email(message="Email inválido.")
	public String getEmail() {
		return email;
	}
	
	public void setTelefone(Long telefone) {
		this.telefone = telefone;
	}
	
	public Long getTelefone() {
		return telefone;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	@NotEmpty(message = "CPF não pode ser vazio.")
	@CPF(message="CPF inválido")
	public String getCpf() {
		return cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Long getCategoriaReceitaId() {
		return categoriaReceitaId;
	}

	public void setCategoriaReceitaId(Long categoriaReceitaId) {
		this.categoriaReceitaId = categoriaReceitaId;
	}

	@Override
	public String toString() {
		return "PacienteDto [pacienteId=" + id + ", usuarioId=" + usuarioId + ", nome=" + nome +
				", cpf=" + cpf + ", telefone=" + telefone + ", email" + email + ", observacao=" + observacao + 
				", categoriaReceitaId=" + categoriaReceitaId + "]";
	}

}
