package br.com.projeto.projetotcc.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.projetotcc.api.entities.AvaliacaoAntropometricaPrega;

@Transactional(readOnly = true)
public interface AvaliacaoAntropometricaPregaRepository extends JpaRepository<AvaliacaoAntropometricaPrega, Long> {

	Optional<AvaliacaoAntropometricaPrega> findById(Long id);
	
	List<AvaliacaoAntropometricaPrega> findByAvaliacaoAntropometricaId(Long avaliacaoAntropometricaId);
	
}
