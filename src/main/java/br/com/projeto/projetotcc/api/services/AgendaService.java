package br.com.projeto.projetotcc.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.com.projeto.projetotcc.api.entities.Agenda;

public interface AgendaService {
	
	/**
	 * Busca e retorna uma Agenda por usuario ID.
	 * 
	 * @param usuarioId
	 * @return Optional<Agenda>
	 */
	Optional<Agenda> buscarUltimaPorUsuarioId(Long usuarioId);
	
	/**
	 * Busca e retorna uma Agenda por ID.
	 * 
	 * @param id
	 * @return Optional<Agenda>
	 */
	Optional<Agenda> buscarPorId(Long id);
	
	/**
	 * Persiste uma agenda na base de dados.
	 * 
	 * @param agenda
	 * @return Agenda
	 */
	Agenda persistir(Agenda agenda);
	
	/**
	 * Retorna uma lista paginada de agendas de um determinado profissional.
	 * 
	 * @param profissionalId
	 * @param pageRequest
	 * @return Page<Agenda>
	 */
	Page<Agenda> buscarPorProfissionalId(Long profissionalId, PageRequest pageRequest);
	
	/**
	 * Retorna uma lista paginada de agendas disponíveis de um determinado profissional.
	 * 
	 * @param profissionalId
	 * @return List<Agenda>
	 */
	List<Agenda> buscarDisponiveisPorProfissionalId(Long profissionalId);
	
	/**
	 * Retorna uma lista paginada de agendas de um determinado profissional.
	 *
	 * @param profissionalId
	 * @return List<Agenda>
	 */
    List<Agenda> buscarTodosPorProfissionalId(Long profissionalId);
	
	/**
	 * Remove uma agenda da base de dados.
	 * 
	 * @param id
	 */
	void remover(Long id);

}
