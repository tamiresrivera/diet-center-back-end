package br.com.projeto.projetotcc.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.projetotcc.api.entities.Profissional;
import br.com.projeto.projetotcc.api.repositories.ProfissionalRepository;
import br.com.projeto.projetotcc.api.services.ProfissionalService;

@Service
public class ProfissionalServiceImpl implements ProfissionalService {
	
	private static final Logger log = LoggerFactory.getLogger(ProfissionalServiceImpl.class);
	
	@Autowired
	private ProfissionalRepository profissionalRepository;
	
	public Optional<Profissional> buscarPorId(Long id) {
		log.info("Buscando Profissional pelo ID {}", id);
		return this.profissionalRepository.findById(id);
	}
	
	public List<Profissional> buscarTodos() {
		log.info("Buscando todos os profissionais do sistema");
		return this.profissionalRepository.findAll();
	}
	
	public Optional<Profissional> buscarPorUsuarioId(Long id) {
		log.info("Buscando Profissional pelo usuarioId {}", id);
		return this.profissionalRepository.findByUsuarioId(id);
	}
	
	public Profissional persistir(Profissional profissional) {
		log.info("Persistindo o Profissional: {}", profissional);
		return this.profissionalRepository.save(profissional);
	}

}
