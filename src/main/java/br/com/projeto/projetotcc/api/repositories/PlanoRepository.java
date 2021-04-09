package br.com.projeto.projetotcc.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.projetotcc.api.entities.Plano;

@Transactional(readOnly = true)
public interface PlanoRepository extends JpaRepository<Plano, Long> {
	
	Optional<Plano> findById(Long id);
	
	List<Plano> findByPacienteId(Long pacienteId);
	
}

