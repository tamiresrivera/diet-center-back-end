package br.com.projeto.projetotcc.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.projetotcc.api.entities.CategoriaAvaliacaoDiametroOsseo;

@Transactional(readOnly = true)
public interface CategoriaAvaliacaoDiametroOsseoRepository extends JpaRepository<CategoriaAvaliacaoDiametroOsseo, Long> {

	Optional<CategoriaAvaliacaoDiametroOsseo> findById(Long id);
	
	List<CategoriaAvaliacaoDiametroOsseo> findByProfissionalId(@Param("profissionalId") Long profissionalId);


}