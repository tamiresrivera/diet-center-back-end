package br.com.projeto.projetotcc.api.services;

import java.util.List;
import java.util.Optional;

import br.com.projeto.projetotcc.api.entities.Profissional;

public interface ProfissionalService {
	
	/**
	 * Busca e retorna um profissional por ID.
	 * 
	 * @param id
	 * @return Optional<Profissional>
	 */
	Optional<Profissional> buscarPorId(Long id);
	
	/**
	 * Busca e retorna todos os profissionais cadastrados no sistema.
	 * 
	 * @return List<Profissional>
	 */
	List<Profissional> buscarTodos();
	
	/**
	 * Busca e retorna um profissional por usuarioId.
	 * 
	 * @param id
	 * @return Optional<Profissional>
	 */
	Optional<Profissional> buscarPorUsuarioId(Long usuarioId);

	/**
	 * Persiste um profissional na base de dados.
	 * 
	 * @param profissional
	 * @return Profissional
	 */
	Profissional persistir(Profissional profissional);
}
