package br.com.projeto.projetotcc.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.projetotcc.api.entities.AvaliacaoAntropometricaCircunferencia;
import br.com.projeto.projetotcc.api.repositories.AvaliacaoAntropometricaCircunferenciaRepository;
import br.com.projeto.projetotcc.api.services.AvaliacaoAntropometricaCircunferenciaService;

@Service
public class AvaliacaoAntropometricaCircunferenciaServiceImpl implements AvaliacaoAntropometricaCircunferenciaService {
	
	private static final Logger log = LoggerFactory.getLogger(AvaliacaoAntropometricaCircunferenciaServiceImpl.class);
	
	@Autowired
	private AvaliacaoAntropometricaCircunferenciaRepository avaliacaoAntropometricaCircunferenciaRepository;
	
	public Optional<AvaliacaoAntropometricaCircunferencia> buscarPorId(Long id) {
		log.info("Buscando registro pelo ID {}", id);
		return this.avaliacaoAntropometricaCircunferenciaRepository.findById(id);
	}
	
	public List<AvaliacaoAntropometricaCircunferencia> buscarPorAvaliacaoAntropometricaId(Long avaliacaoAntropometricaId) {
		log.info("Buscando registros por avaliacao antropometrica ID {}", avaliacaoAntropometricaId);
		return this.avaliacaoAntropometricaCircunferenciaRepository.findByAvaliacaoAntropometricaId(avaliacaoAntropometricaId);
	}
	
	public AvaliacaoAntropometricaCircunferencia persistir(AvaliacaoAntropometricaCircunferencia avaliacaoAntropometricaCircunferencia) {
		log.info("Persistindo o registro: {}", avaliacaoAntropometricaCircunferencia);
		return this.avaliacaoAntropometricaCircunferenciaRepository.save(avaliacaoAntropometricaCircunferencia);
	}
	
	public void remover(Long id) {
		log.info("Removendo o registro ID {}", id);
		this.avaliacaoAntropometricaCircunferenciaRepository.deleteById(id);
	}

}
