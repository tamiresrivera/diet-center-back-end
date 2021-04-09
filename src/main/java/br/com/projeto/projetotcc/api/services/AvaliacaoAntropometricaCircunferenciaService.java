package br.com.projeto.projetotcc.api.services;

import java.util.List;
import java.util.Optional;

import br.com.projeto.projetotcc.api.entities.AvaliacaoAntropometricaCircunferencia;

public interface AvaliacaoAntropometricaCircunferenciaService {
	
	/**
	 * Busca e retorna um registro por ID.
	 * 
	 * @param id
	 * @return Optional<AvaliacaoAntropometricaCircunferencia>
	 */
	Optional<AvaliacaoAntropometricaCircunferencia> buscarPorId(Long id);
	
	/**
	 * Busca e retorna todos os registros de uma avaliacao ID.
	 * 
	 * @param id
	 * @return Optional<AvaliacaoAntropometricaCircunferencia>
	 */
	List<AvaliacaoAntropometricaCircunferencia> buscarPorAvaliacaoAntropometricaId(Long avaliacaoAntropometricaId);
	
	/**
	 * Persiste um registro na base de dados.
	 * 
	 * @param avaliacaoAntropometricaCircunferencia
	 * @return AvaliacaoAntropometricaCircunferencia
	 */
	AvaliacaoAntropometricaCircunferencia persistir(AvaliacaoAntropometricaCircunferencia avaliacaoAntropometricaCircunferencia);
	
	/**
	 * Remove um registro da base de dados.
	 * 
	 * @param id
	 */
	void remover(Long id);

}
