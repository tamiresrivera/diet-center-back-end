package br.com.projeto.projetotcc.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.projeto.projetotcc.api.entities.Agenda;

@Transactional(readOnly = true)
public interface AgendaRepository extends JpaRepository<Agenda, Long> {
	
	Optional<Agenda> findById(Long id);
	
	@Query(value = 	"SELECT a.* " +
			"FROM agenda a " +
			"JOIN (SELECT min(a.data_hora) AS data_hora, a.profissional_id, a.paciente_id " +
			"FROM agenda a " +
			"JOIN paciente b ON b.id = a.paciente_id " +
			"JOIN usuario c ON c.id = b.usuario_id " +
			"WHERE c.id = :usuarioId " +
			"AND a.data_hora >= CURDATE() " +
			"GROUP BY a.profissional_id, a.paciente_id " +
			") b ON b.paciente_id = a.paciente_id " +
			"AND b.profissional_id = a.profissional_id " +
			"AND b.data_hora = a.data_hora" ,
		       nativeQuery = true)
	Optional<Agenda> findByProximaUsuarioId(Long usuarioId);
	
	@Query(value = "SELECT a.* FROM agenda a " +
		       "WHERE a.profissional_id = :profissionalId " +
		       "AND a.data_hora >= CURDATE( ) " +
		       "AND a.paciente_id IS NULL " ,
		       nativeQuery = true)
	List<Agenda> findDisponiveisByProfissionalId(Long profissionalId);
	
	@Query(value = "SELECT a.* FROM agenda a " +
		       "WHERE a.profissional_id = :profissionalId " +
		       "AND a.data_hora >= CURDATE( ) " ,
		       nativeQuery = true)
	List<Agenda> findByProfissionalId(Long profissionalId);
	
	@Query(value = "SELECT a.* FROM agenda a " +
		       "WHERE a.profissional_id = :profissionalId " +
		       "AND a.data_hora >= CURDATE( ) " +
		       " #{#pageable} ",
	   countQuery = "SELECT count(a.*) FROM agenda a " +
			   "WHERE a.profissional_id = :profissionalId " +
		       "AND a.data_hora >= CURDATE( ) " ,
	   nativeQuery = true)
	Page<Agenda> buscarPorProfissionalId(
    		@Param("profissionalId") Long profissionalId, 
            Pageable pageable);

}
