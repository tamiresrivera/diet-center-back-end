package br.com.projeto.projetotcc.api.services;

import java.util.List;
import java.util.Optional;

import br.com.projeto.projetotcc.api.entities.AvaliacaoAntropometricaDiametroOsseo;

public interface AvaliacaoAntropometricaDiametroOsseoService {
	
	/**
	 * Busca e retorna um registro por ID.
	 * 
	 * @param id
	 * @return Optional<AvaliacaoAntropometricaDiametroOsseo>
	 */
	Optional<AvaliacaoAntropometricaDiametroOsseo> buscarPorId(Long id);
	
	/**
	 * Busca e retorna todos os registros de uma avaliacao ID.
	 * 
	 * @param id
	 * @return Optional<AvaliacaoAntropometricaCircunferencia>
	 */
	List<AvaliacaoAntropometricaDiametroOsseo> buscarPorAvaliacaoAntropometricaId(Long avaliacaoAntropometricaId);
	
	/**
	 * Persiste um registro na base de dados.
	 * 
	 * @param avaliacaoAntropometricaCircunferencia
	 * @return AvaliacaoAntropometricaCircunferencia
	 */
	AvaliacaoAntropometricaDiametroOsseo persistir(AvaliacaoAntropometricaDiametroOsseo avaliacaoAntropometricaDiametroOsseo);
	
	/**
	 * Remove um registro da base de dados.
	 * 
	 * @param id
	 */
	void remover(Long id);

}
