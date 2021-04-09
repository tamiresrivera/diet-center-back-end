package br.com.projeto.projetotcc.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.projetotcc.api.entities.Plano;
import br.com.projeto.projetotcc.api.repositories.PlanoRepository;
import br.com.projeto.projetotcc.api.services.PlanoService;

@Service
public class PlanoServiceImpl implements PlanoService {
	
	private static final Logger log = LoggerFactory.getLogger(PlanoServiceImpl.class);
	
	@Autowired
	private PlanoRepository planoRepository;
	
	public Optional<Plano> buscarPorId(Long id) {
		log.info("Buscando Plano alimentar pelo ID {}", id);
		return this.planoRepository.findById(id);
	}
	
	public List<Plano> buscarPorPacienteId(Long pacienteId) {
		log.info("Buscando Planos alimentares por paciente ID {}", pacienteId);
		return this.planoRepository.findByPacienteId(pacienteId);
	}
	
	public Plano persistir(Plano plano) {
		log.info("Persistindo o Plano alimentar: {}", plano);
		return this.planoRepository.save(plano);
	}
	
	public void remover(Long id) {
		log.info("Removendo o plano alimentar ID {}", id);
		this.planoRepository.deleteById(id);
	}

}
