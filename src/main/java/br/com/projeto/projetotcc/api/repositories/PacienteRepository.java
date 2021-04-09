package br.com.projeto.projetotcc.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.projeto.projetotcc.api.entities.Paciente;

@Transactional(readOnly = true)
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
	
	Optional<Paciente> findById(Long id);
	
	Optional<Paciente> findByUsuarioId(Long usuarioId);
	
	List<Paciente> findByProfissionalId(Long profissionalId);
	
}
