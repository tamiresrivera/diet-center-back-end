package br.com.projeto.projetotcc.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.projetotcc.api.entities.CategoriaPlanoAlimentar;

@Transactional(readOnly = true)
public interface CategoriaPlanoAlimentarRepository extends JpaRepository<CategoriaPlanoAlimentar, Long> {

	Optional<CategoriaPlanoAlimentar> findById(Long id);
	
	List<CategoriaPlanoAlimentar> findByProfissionalId(@Param("profissionalId") Long profissionalId);


}