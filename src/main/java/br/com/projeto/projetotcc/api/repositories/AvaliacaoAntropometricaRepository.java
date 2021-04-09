package br.com.projeto.projetotcc.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.projetotcc.api.entities.AvaliacaoAntropometrica;

@Transactional(readOnly = true)
public interface AvaliacaoAntropometricaRepository extends JpaRepository<AvaliacaoAntropometrica, Long>{
	
	Optional<AvaliacaoAntropometrica> findById(Long id);
	
	List<AvaliacaoAntropometrica> findByPacienteId(@Param("pacienteId") Long pacienteId);
	
	@Query(value = "SELECT b.* from " +
				   "(SELECT a.* FROM avaliacao_antropometrica a " +
		           "WHERE a.paciente_id = :pacienteId " +
		           "ORDER BY a.data_hora DESC, a.id DESC " +
		           "LIMIT 10) b " +
		           "ORDER BY b.data_hora " , nativeQuery = true)
	List<AvaliacaoAntropometrica> buscarUltimasPorUsuarioId(@Param("pacienteId") Long pacienteId);

}
