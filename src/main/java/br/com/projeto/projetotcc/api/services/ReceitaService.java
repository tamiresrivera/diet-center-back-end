package br.com.projeto.projetotcc.api.services;

import java.util.List;
import java.util.Optional;

import br.com.projeto.projetotcc.api.entities.Receita;

public interface ReceitaService {
	
	/**
	 * Retorna uma receita por ID.
	 * 
	 * @param id
	 * @return Optional<Receita>
	 */
	Optional<Receita> buscarPorId(Long id);
	
	/**
	 * Persiste uma receita na base de dados.
	 * 
	 * @param receita
	 * @return Receita
	 */
	Receita persistir(Receita receita);
	
	/**
	 * Remove uma receita da base de dados.
	 * 
	 * @param id
	 */
	void remover(Long id);

	/**
	 * Retorna uma lista com todas as receitas de um determinado profissional.
	 *
	 * @param profissionalId
	 * @return List<Receita>
	 */
    List<Receita> buscarPorProfissionalId(Long profissionalId);
    
    /**
	 * Retorna uma lista com todas as receitas de uma determinada categoria.
	 *
	 * @param categoriaId
	 * @return List<Receita>
	 */
    public List<Receita> buscarPorCategoriaReceitaId(Long categoriaId);

}
