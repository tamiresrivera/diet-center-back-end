package br.com.projeto.projetotcc.api.services;

import java.util.List;
import java.util.Optional;

import br.com.projeto.projetotcc.api.entities.CategoriaAvaliacaoPrega;

public interface CategoriaAvaliacaoPregaService {
	
	/**
	 * Retorna uma categoria de prega por ID.
	 * 
	 * @param id
	 * @return Optional<CategoriaAvaliacaoPrega>
	 */
	Optional<CategoriaAvaliacaoPrega> buscarPorId(Long id);
	
	/**
	 * Persiste uma categoria de prega na base de dados.
	 * 
	 * @param categoriaAvaliacaoPrega
	 * @return CategoriaAvaliacaoPrega
	 */
	CategoriaAvaliacaoPrega persistir(CategoriaAvaliacaoPrega categoriaPrega);
	
	/**
	 * Remove uma categoria de prega da base de dados.
	 * 
	 * @param id
	 */
	void remover(Long id);

	/**
	 * Retorna uma lista com todas as categorias de prega de um determinado profissional.
	 *
	 * @param profissionalId
	 * @return List<CategoriaAvaliacaoPrega>
	 */
    List<CategoriaAvaliacaoPrega> buscarPorProfissionalId(Long profissionalId);
}
