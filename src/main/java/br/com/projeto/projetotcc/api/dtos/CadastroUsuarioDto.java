package br.com.projeto.projetotcc.api.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import br.com.projeto.projetotcc.api.enums.PerfilEnum;

public class CadastroUsuarioDto {
	
	private Long id;
	private String nome;
	private String email;
	private String cpf;
	private String nomeUsuario;
	private String senha;
	private Long telefone;
	private PerfilEnum perfil;

	public CadastroUsuarioDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty(message = "Nome não pode ser vazio.")
	@Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@NotEmpty(message = "CPF não pode ser vazio.")
	@CPF(message="CPF inválido")
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@NotEmpty(message = "Email não pode ser vazio.")
	@Length(min = 5, max = 200, message = "Email deve conter entre 5 e 200 caracteres.")
	@Email(message="Email inválido.")
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	@NotEmpty(message = "Nome de Usuário não pode ser vazio.")
	@Length(min = 5, max = 20, message = "Nome de Usuário deve conter entre 5 e 20 caracteres.")
	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	@NotEmpty(message = "Senha não pode ser vazia.")
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Long getTelefone() {
		return telefone;
	}

	public void setTelefone(Long telefone) {
		this.telefone = telefone;
	}
	
	@NotNull(message = "Perfil não pode ser vazio.")
	public PerfilEnum getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilEnum perfil) {
		this.perfil = perfil;
	}

	@Override
	public String toString() {
		return "CadastroUsuarioDto [id=" + id + ", nome=" + nome + ", nomeUsuario=" + nomeUsuario + ", senha=" + senha + ", cpf=" + cpf
				+ ", email=" + email + ", telefone=" + telefone + ", perfil=" + perfil + "]";
	}

}
