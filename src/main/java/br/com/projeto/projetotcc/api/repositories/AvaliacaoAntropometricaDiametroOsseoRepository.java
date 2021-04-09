package br.com.projeto.projetotcc.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.projetotcc.api.entities.AvaliacaoAntropometricaDiametroOsseo;

@Transactional(readOnly = true)
public interface AvaliacaoAntropometricaDiametroOsseoRepository extends JpaRepository<AvaliacaoAntropometricaDiametroOsseo, Long> {


	Optional<AvaliacaoAntropometricaDiametroOsseo> findById(Long id);
	
	List<AvaliacaoAntropometricaDiametroOsseo> findByAvaliacaoAntropometricaId(Long avaliacaoAntropometricaId);
	
}
