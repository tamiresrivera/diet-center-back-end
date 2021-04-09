package br.com.projeto.projetotcc.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.projetotcc.api.entities.AvaliacaoAntropometrica;
import br.com.projeto.projetotcc.api.repositories.AvaliacaoAntropometricaRepository;
import br.com.projeto.projetotcc.api.services.AvaliacaoAntropometricaService;

@Service
public class AvaliacaoAntropometricaServiceImpl implements AvaliacaoAntropometricaService {
	
	private static final Logger log = LoggerFactory.getLogger(AvaliacaoAntropometricaServiceImpl.class);

	@Autowired
	private AvaliacaoAntropometricaRepository avaliacaoAntropometricaRepository;

	public List<AvaliacaoAntropometrica> buscarPorPacienteId(Long pacienteId) {
		log.info("Buscando todos as avaliacoes para o paciente ID {}", pacienteId);
		return this.avaliacaoAntropometricaRepository.findByPacienteId(pacienteId);
	}
	
	public List<AvaliacaoAntropometrica> buscarUltimasPorUsuarioId(Long usuarioId) {
		log.info("Buscando as 12 últimas avaliacoes para o usuario ID {}", usuarioId);
		return this.avaliacaoAntropometricaRepository.buscarUltimasPorUsuarioId(usuarioId);
	}
	
	public Optional<AvaliacaoAntropometrica> buscarPorId(Long id) {
		log.info("Buscando uma avaliacao pelo ID {}", id);
		return this.avaliacaoAntropometricaRepository.findById(id);
	}
	
	public AvaliacaoAntropometrica persistir(AvaliacaoAntropometrica avaliacaoAntropometrica) {
		log.info("Persistindo a avaliacao: {}", avaliacaoAntropometrica);
		return this.avaliacaoAntropometricaRepository.save(avaliacaoAntropometrica);
	}
	
	public void remover(Long id) {
		log.info("Removendo a avaliacao ID {}", id);
		this.avaliacaoAntropometricaRepository.deleteById(id);
	}

}
