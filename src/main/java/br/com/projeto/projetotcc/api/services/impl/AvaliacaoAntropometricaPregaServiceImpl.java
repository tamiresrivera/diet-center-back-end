package br.com.projeto.projetotcc.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.projetotcc.api.entities.AvaliacaoAntropometricaPrega;
import br.com.projeto.projetotcc.api.repositories.AvaliacaoAntropometricaPregaRepository;
import br.com.projeto.projetotcc.api.services.AvaliacaoAntropometricaPregaService;

@Service
public class AvaliacaoAntropometricaPregaServiceImpl implements AvaliacaoAntropometricaPregaService {
	
	private static final Logger log = LoggerFactory.getLogger(AvaliacaoAntropometricaPregaServiceImpl.class);
	
	@Autowired
	private AvaliacaoAntropometricaPregaRepository avaliacaoAntropometricaPregaRepository;
	
	public Optional<AvaliacaoAntropometricaPrega> buscarPorId(Long id) {
		log.info("Buscando registro pelo ID {}", id);
		return this.avaliacaoAntropometricaPregaRepository.findById(id);
	}
	
	public List<AvaliacaoAntropometricaPrega> buscarPorAvaliacaoAntropometricaId(Long avaliacaoAntropometricaId) {
		log.info("Buscando registros por avaliacao antropometrica ID {}", avaliacaoAntropometricaId);
		return this.avaliacaoAntropometricaPregaRepository.findByAvaliacaoAntropometricaId(avaliacaoAntropometricaId);
	}
	
	public AvaliacaoAntropometricaPrega persistir(AvaliacaoAntropometricaPrega avaliacaoAntropometricaPrega) {
		log.info("Persistindo o registro: {}", avaliacaoAntropometricaPrega);
		return this.avaliacaoAntropometricaPregaRepository.save(avaliacaoAntropometricaPrega);
	}
	
	public void remover(Long id) {
		log.info("Removendo o registro ID {}", id);
		this.avaliacaoAntropometricaPregaRepository.deleteById(id);
	}

}
