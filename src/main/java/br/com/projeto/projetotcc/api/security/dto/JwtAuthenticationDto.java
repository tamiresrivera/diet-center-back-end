package br.com.projeto.projetotcc.api.security.dto;

import javax.validation.constraints.NotEmpty;

public class JwtAuthenticationDto {
	
	private String nomeUsuario;
	private String senha;

	public JwtAuthenticationDto() {
	}

	@NotEmpty(message = "Nome de Usuário não pode ser vazio.")
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

	@Override
	public String toString() {
		return "JwtAuthenticationRequestDto [nomeUsuario=" + nomeUsuario + ", senha=" + senha + "]";
	}

}

