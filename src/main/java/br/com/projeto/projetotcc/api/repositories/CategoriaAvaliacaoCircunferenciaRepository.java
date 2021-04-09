package br.com.projeto.projetotcc.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.projetotcc.api.entities.CategoriaAvaliacaoCircunferencia;

@Transactional(readOnly = true)
public interface CategoriaAvaliacaoCircunferenciaRepository extends JpaRepository<CategoriaAvaliacaoCircunferencia, Long> {

	Optional<CategoriaAvaliacaoCircunferencia> findById(Long id);
	
	List<CategoriaAvaliacaoCircunferencia> findByProfissionalId(@Param("profissionalId") Long profissionalId);


}