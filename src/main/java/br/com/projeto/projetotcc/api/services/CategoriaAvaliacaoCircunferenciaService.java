package br.com.projeto.projetotcc.api.services;

import java.util.List;
import java.util.Optional;

import br.com.projeto.projetotcc.api.entities.CategoriaAvaliacaoCircunferencia;

public interface CategoriaAvaliacaoCircunferenciaService {
	
	/**
	 * Retorna uma categoria de avaliacao por ID.
	 * 
	 * @param id
	 * @return Optional<CategoriaAvaliacaoCircunferencia>
	 */
	Optional<CategoriaAvaliacaoCircunferencia> buscarPorId(Long id);
	
	/**
	 * Persiste uma categoria de plano alimentar na base de dados.
	 * 
	 * @param categoriaAvaliacaoCircunferencia
	 * @return CategoriaAvaliacaoCircunferencia
	 */
	CategoriaAvaliacaoCircunferencia persistir(CategoriaAvaliacaoCircunferencia categoriaReceita);
	
	/**
	 * Remove uma categoria de circunferencia da base de dados.
	 * 
	 * @param id
	 */
	void remover(Long id);

	/**
	 * Retorna uma lista com todas as categorias de circunferencia de um determinado profissional.
	 *
	 * @param profissionalId
	 * @return List<CategoriaPlanoAlimentar>
	 */
    List<CategoriaAvaliacaoCircunferencia> buscarPorProfissionalId(Long profissionalId);
}
