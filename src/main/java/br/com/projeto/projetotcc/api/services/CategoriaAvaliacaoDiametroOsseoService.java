package br.com.projeto.projetotcc.api.services;

import java.util.List;
import java.util.Optional;

import br.com.projeto.projetotcc.api.entities.CategoriaAvaliacaoDiametroOsseo;

public interface CategoriaAvaliacaoDiametroOsseoService {
	
	/**
	 * Retorna uma categoria de diametro por ID.
	 * 
	 * @param id
	 * @return Optional<CategoriaAvaliacaoDiametroOsseo>
	 */
	Optional<CategoriaAvaliacaoDiametroOsseo> buscarPorId(Long id);
	
	/**
	 * Persiste uma categoria de diametro na base de dados.
	 * 
	 * @param categoriaAvaliacaoDiametroOsseo
	 * @return CategoriaAvaliacaoDiametroOsseo
	 */
	CategoriaAvaliacaoDiametroOsseo persistir(CategoriaAvaliacaoDiametroOsseo categoriaDiametro);
	
	/**
	 * Remove uma categoria de diametro da base de dados.
	 * 
	 * @param id
	 */
	void remover(Long id);

	/**
	 * Retorna uma lista com todas as categorias de diametro de um determinado profissional.
	 *
	 * @param profissionalId
	 * @return List<CategoriaAvaliacaoDiametroOsseo>
	 */
    List<CategoriaAvaliacaoDiametroOsseo> buscarPorProfissionalId(Long profissionalId);
}
