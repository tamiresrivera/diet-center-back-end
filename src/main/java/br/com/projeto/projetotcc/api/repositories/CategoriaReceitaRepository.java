package br.com.projeto.projetotcc.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.projetotcc.api.entities.CategoriaReceita;

@Transactional(readOnly = true)
public interface CategoriaReceitaRepository extends JpaRepository<CategoriaReceita, Long> {

	Optional<CategoriaReceita> findById(Long id);
	
	List<CategoriaReceita> findByProfissionalId(@Param("profissionalId") Long profissionalId);


}