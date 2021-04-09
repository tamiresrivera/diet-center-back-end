package br.com.projeto.projetotcc.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.projetotcc.api.entities.Profissional;

@Transactional(readOnly = true)
public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {
	
	Optional<Profissional> findById(Long id);
	
	Optional<Profissional> findByUsuarioId(Long usuarioId);
	
	List<Profissional> findAll();
	
}
