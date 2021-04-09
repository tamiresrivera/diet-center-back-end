package br.com.projeto.projetotcc.api.services;

import java.util.List;
import java.util.Optional;

import br.com.projeto.projetotcc.api.entities.Paciente;

public interface PacienteService {
	
	/**
	 * Busca e retorna um paciente por ID.
	 * 
	 * @param id
	 * @return Optional<Paciente>
	 */
	Optional<Paciente> buscarPorId(Long id);
	
	/**
	 * Busca e retorna um paciente por usuarioId.
	 * 
	 * @param id
	 * @return Optional<Paciente>
	 */
	Optional<Paciente> buscarPorUsuarioId(Long usuarioId);
	
	/**
	 * Busca e retorna todos os pacientes por profissionalId.
	 * 
	 * @param profissionalId
	 * @return List<Paciente>
	 */
	List<Paciente> buscarPorProfissionalId(Long profissionalId);
	
	/**
	 * Persiste um paciente na base de dados.
	 * 
	 * @param paciente
	 * @return Paciente
	 */
	Paciente persistir(Paciente paciente);

}
