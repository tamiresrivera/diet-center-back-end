package br.com.projeto.projetotcc.api.services;

import java.util.List;
import java.util.Optional;

import br.com.projeto.projetotcc.api.entities.CategoriaPlanoAlimentar;

public interface CategoriaPlanoAlimentarService {
	
	/**
	 * Retorna uma categoria de plano alimentar por ID.
	 * 
	 * @param id
	 * @return Optional<CategoriaPlanoAlimentar>
	 */
	Optional<CategoriaPlanoAlimentar> buscarPorId(Long id);
	
	/**
	 * Persiste uma categoria de plano alimentar na base de dados.
	 * 
	 * @param CategoriaPlanoAlimentar
	 * @return CategoriaPlanoAlimentar
	 */
	CategoriaPlanoAlimentar persistir(CategoriaPlanoAlimentar categoriaPlanoAlimentar);
	
	/**
	 * Remove uma categoria de plano alimentar da base de dados.
	 * 
	 * @param id
	 */
	void remover(Long id);

	/**
	 * Retorna uma lista com todas as categorias de plano alimentar de um determinado profissional.
	 *
	 * @param profissionalId
	 * @return List<CategoriaPlanoAlimentar>
	 */
    List<CategoriaPlanoAlimentar> buscarPorProfissionalId(Long profissionalId);
}
