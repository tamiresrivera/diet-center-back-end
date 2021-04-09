package br.com.projeto.projetotcc.api.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

public class ProfissionalDto {
	
	private Long profissionalId;
	private Long usuarioId;
	private String descricao;
	private String nome;
	private String email;
	private Long telefone;
	private String cpf;
	private int crn;
	private String endereco;
	private Long numero;
	private Long cep;
	private String complemento;
	private String cidade;
	private String estado;
	private String midiaSocial;
	//private Usuario usuario;
	private String senha;

	public ProfissionalDto() {
	}

	public Long getProfissionalId() {
		return profissionalId;
	}

	public void setProfissionalId(Long profissionalId) {
		this.profissionalId = profissionalId;
	}
	
	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
	
	@Length(max = 200, message = "Nome deve conter até 200 caracteres.")
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public int getCrn() {
		return crn;
	}

	public void setCrn(int crn) {
		this.crn = crn;
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

	public void setMidiaSocial(String midiaSocial) {
		this.midiaSocial = midiaSocial;
	}

	//public Usuario getUsuario() {
		//return usuario;
	//}

	//public void setUsuario(Usuario usuario) {
		//this.usuario = usuario;
	//}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "ProfissionalDto [profissionalId=" + profissionalId + ", usuarioId=" + usuarioId + ", nome=" + nome +
				", cpf=" + cpf + ", telefone=" + telefone + ", email" + email + ", descricao=" + descricao + ", crn=" + crn + ", endereco=" + 
				endereco + ", numero=" + numero + ", cep=" + cep + ", complemento=" + complemento + ", cidade=" + cidade +
				", estado=" + estado + ", midiaSocial=" + midiaSocial + "]";
	}

}
