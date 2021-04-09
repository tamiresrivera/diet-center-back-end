package br.com.projeto.projetotcc.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.projetotcc.api.entities.Receita;

@Transactional(readOnly = true)
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

	Optional<Receita> findById(Long id);
	
	List<Receita> findByProfissionalId(@Param("profissionalId") Long profissionalId);
	
	List<Receita> findByCategoriaReceitaId(@Param("categoriaId") Long categoriaId);

}