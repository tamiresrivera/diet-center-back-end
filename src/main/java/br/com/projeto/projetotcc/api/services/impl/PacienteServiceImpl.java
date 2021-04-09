package br.com.projeto.projetotcc.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.projetotcc.api.entities.Paciente;
import br.com.projeto.projetotcc.api.repositories.PacienteRepository;
import br.com.projeto.projetotcc.api.services.PacienteService;

@Service
public class PacienteServiceImpl implements PacienteService {
	
	private static final Logger log = LoggerFactory.getLogger(PacienteServiceImpl.class);
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	public Optional<Paciente> buscarPorId(Long id) {
		log.info("Buscando Paciente pelo ID {}", id);
		return this.pacienteRepository.findById(id);
	}
	
	public Optional<Paciente> buscarPorUsuarioId(Long id) {
		log.info("Buscando Paciente pelo usuarioId {}", id);
		return this.pacienteRepository.findByUsuarioId(id);
	}
	
	public List<Paciente> buscarPorProfissionalId(Long profissionalId) {
		log.info("Buscando Paciente pelo profissionalId {}", profissionalId);
		return this.pacienteRepository.findByProfissionalId(profissionalId);
	}
	
	public Paciente persistir(Paciente paciente) {
		log.info("Persistindo o Paciente: {}", paciente);
		return this.pacienteRepository.save(paciente);
	}

}
