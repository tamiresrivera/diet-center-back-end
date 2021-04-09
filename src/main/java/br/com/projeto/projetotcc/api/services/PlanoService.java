package br.com.projeto.projetotcc.api.services;

import java.util.List;
import java.util.Optional;

import br.com.projeto.projetotcc.api.entities.Plano;

public interface PlanoService {
	
	/**
	 * Busca e retorna um Plano alimentar por ID.
	 * 
	 * @param id
	 * @return Optional<Plano>
	 */
	Optional<Plano> buscarPorId(Long id);
	
	/**
	 * Busca e retorna todos os Planos alimentares de um paciente ID.
	 * 
	 * @param id
	 * @return Optional<Plano>
	 */
	List<Plano> buscarPorPacienteId(Long pacientId);
	
	/**
	 * Persiste um plano alimentar na base de dados.
	 * 
	 * @param plano
	 * @return Plano
	 */
	Plano persistir(Plano plano);
	
	/**
	 * Remove um plano alimentar da base de dados.
	 * 
	 * @param id
	 */
	void remover(Long id);
}
