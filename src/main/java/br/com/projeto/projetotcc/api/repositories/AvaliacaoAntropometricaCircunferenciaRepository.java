package br.com.projeto.projetotcc.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.projetotcc.api.entities.AvaliacaoAntropometricaCircunferencia;

@Transactional(readOnly = true)
public interface AvaliacaoAntropometricaCircunferenciaRepository extends JpaRepository<AvaliacaoAntropometricaCircunferencia, Long> {

	Optional<AvaliacaoAntropometricaCircunferencia> findById(Long id);
	
	List<AvaliacaoAntropometricaCircunferencia> findByAvaliacaoAntropometricaId(Long avaliacaoAntropometricaId);
	
}
