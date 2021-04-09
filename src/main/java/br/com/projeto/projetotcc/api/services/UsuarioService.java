package br.com.projeto.projetotcc.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.com.projeto.projetotcc.api.entities.Usuario;

public interface UsuarioService {

	/**
	 * Persiste um usuario na base de dados.
	 * 
	 * @param usuario
	 * @return Usuario
	 */
	Usuario persistir(Usuario usuario);
	
	/**
	 * Busca e retorna um usuario dado um nome de usuario.
	 * 
	 * @param nomeUsuario
	 * @return Optional<Usuario>
	 */
	Optional<Usuario> buscarPorNomeUsuario(String nomeUsuario);
	
	/**
	 * Busca e retorna um usuario dado um CPF.
	 * 
	 * @param cpf
	 * @return Optional<Usuario>
	 */
	Optional<Usuario> buscarPorCpf(String cpf);
	
	/**
	 * Busca e retorna um usuario dado um email.
	 * 
	 * @param email
	 * @return Optional<Usuario>
	 */
	Optional<Usuario> buscarPorEmail(String email);
	
	/**
	 * Busca e retorna um usuario por ID.
	 * 
	 * @param id
	 * @return Optional<Usuario>
	 */
	Optional<Usuario> buscarPorId(Long id);
	
	/**
	 * Retorna uma lista paginada de usuarios de um determinado profissional.
	 * 
	 * @param profissionalId
	 * @param pageRequest
	 * @return Page<Usuario>
	 */
	Page<Usuario> buscarPorProfissionalId(Long profissionalId, PageRequest pageRequest);
	
	/**
	 * Retorna uma lista com todos os pacientes de um determinado profissional.
	 *
	 * @param profissionalId
	 * @return List<Usuario>
	 */
    List<Usuario> buscarTodosPorProfissionalId(Long profissionalId);
    
    /**
	 * Retorna uma lista com todos os pacientes de uma determinada receita.
	 *
	 * @param receitaId
	 * @return List<Usuario>
	 */
    List<Usuario> buscarTodosPorReceitaId(Long receitaId);


}
