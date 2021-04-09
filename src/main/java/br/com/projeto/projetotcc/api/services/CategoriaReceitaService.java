package br.com.projeto.projetotcc.api.services;

import java.util.List;
import java.util.Optional;

import br.com.projeto.projetotcc.api.entities.CategoriaReceita;

public interface CategoriaReceitaService {
	
	/**
	 * Retorna uma categoria de receita por ID.
	 * 
	 * @param id
	 * @return Optional<CategoriaReceita>
	 */
	Optional<CategoriaReceita> buscarPorId(Long id);
	
	/**
	 * Persiste uma categoria de receita na base de dados.
	 * 
	 * @param categoriaReceita
	 * @return CategoriaReceita
	 */
	CategoriaReceita persistir(CategoriaReceita categoriaReceita);
	
	/**
	 * Remove uma categoria de receita da base de dados.
	 * 
	 * @param id
	 */
	void remover(Long id);

	/**
	 * Retorna uma lista com todas as categorias de receitas de um determinado profissional.
	 *
	 * @param profissionalId
	 * @return List<CategoriaReceita>
	 */
    List<CategoriaReceita> buscarPorProfissionalId(Long profissionalId);
}
