package br.com.projeto.projetotcc.api.services;

import java.util.List;
import java.util.Optional;

import br.com.projeto.projetotcc.api.entities.AvaliacaoAntropometrica;

public interface AvaliacaoAntropometricaService {
	
	/**
	 * Retorna uma avaliacao por ID.
	 * 
	 * @param id
	 * @return Optional<AvaliacaoAntropometrica>
	 */
	Optional<AvaliacaoAntropometrica> buscarPorId(Long id);
	
	/**
	 * Persiste uma avaliacao na base de dados.
	 * 
	 * @param avaliacaoAntropometrica
	 * @return AvaliacaoAntropometrica
	 */
	AvaliacaoAntropometrica persistir(AvaliacaoAntropometrica avaliacaoAntropometrica);
	
	/**
	 * Remove uma avaliacao da base de dados.
	 * 
	 * @param id
	 */
	void remover(Long id);

	/**
	 * Retorna uma lista com todas as avaliacoes de um determinado paciente.
	 *
	 * @param pacienteId
	 * @return List<AvaliacaoAntropometrica>
	 */
    List<AvaliacaoAntropometrica> buscarPorPacienteId(Long pacienteId);
    
    /**
	 * Retorna uma lista com as 12 últimas avaliacoes de um determinado paciente.
	 *
	 * @param pacienteId
	 * @return List<AvaliacaoAntropometrica>
	 */
    List<AvaliacaoAntropometrica> buscarUltimasPorUsuarioId(Long usuarioId);

}
