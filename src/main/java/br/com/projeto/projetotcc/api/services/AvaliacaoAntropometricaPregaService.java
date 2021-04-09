package br.com.projeto.projetotcc.api.services;

import java.util.List;
import java.util.Optional;

import br.com.projeto.projetotcc.api.entities.AvaliacaoAntropometricaPrega;

public interface AvaliacaoAntropometricaPregaService {
	
	/**
	 * Busca e retorna um registro por ID.
	 * 
	 * @param id
	 * @return Optional<AvaliacaoAntropometricaPrega>
	 */
	Optional<AvaliacaoAntropometricaPrega> buscarPorId(Long id);
	
	/**
	 * Busca e retorna todos os registros de uma avaliacao ID.
	 * 
	 * @param id
	 * @return Optional<AvaliacaoAntropometricaPrega>
	 */
	List<AvaliacaoAntropometricaPrega> buscarPorAvaliacaoAntropometricaId(Long avaliacaoAntropometricaId);
	
	/**
	 * Persiste um registro na base de dados.
	 * 
	 * @param avaliacaoAntropometricaPrega
	 * @return AvaliacaoAntropometricaPrega
	 */
	AvaliacaoAntropometricaPrega persistir(AvaliacaoAntropometricaPrega avaliacaoAntropometricaPrega);
	
	/**
	 * Remove um registro da base de dados.
	 * 
	 * @param id
	 */
	void remover(Long id);

}
