package br.com.projeto.projetotcc.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.projeto.projetotcc.api.entities.Agenda;
import br.com.projeto.projetotcc.api.repositories.AgendaRepository;
import br.com.projeto.projetotcc.api.services.AgendaService;

@Service
public class AgendaServiceImpl implements AgendaService {
	
	private static final Logger log = LoggerFactory.getLogger(AgendaServiceImpl.class);
	
	@Autowired
	private AgendaRepository agendaRepository;
	
	public Optional<Agenda> buscarPorId(Long id) {
		log.info("Buscando Agenda pelo ID {}", id);
		return this.agendaRepository.findById(id);
	}
	
	public Optional<Agenda> buscarUltimaPorUsuarioId(Long usuarioId) {
		log.info("Buscando Agenda pelo usuario ID {}", usuarioId);
		return this.agendaRepository.findByProximaUsuarioId(usuarioId);
	}
	
	public Agenda persistir(Agenda agenda) {
		log.info("Persistindo a Agenda: {}", agenda);
		return this.agendaRepository.save(agenda);
	}
	
	public Page<Agenda> buscarPorProfissionalId(Long profissionalId, PageRequest pageRequest) {
		log.info("Buscando agenda para o profissional ID {}", profissionalId);
		return this.agendaRepository.buscarPorProfissionalId(profissionalId, pageRequest);
	}
	
	public List<Agenda> buscarTodosPorProfissionalId(Long profissionalId) {
		log.info("Buscando agenda para o profissional ID {}", profissionalId);
		return this.agendaRepository.findByProfissionalId(profissionalId);
	}
	
	public List<Agenda> buscarDisponiveisPorProfissionalId(Long profissionalId) {
		log.info("Buscando agenda disponível para o profissional ID {}", profissionalId);
		return this.agendaRepository.findDisponiveisByProfissionalId(profissionalId);
	}
	
	public void remover(Long id) {
		log.info("Removendo a agenda ID {}", id);
		this.agendaRepository.deleteById(id);
	}

}
