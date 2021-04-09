package br.com.projeto.projetotcc.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.projetotcc.api.entities.AvaliacaoAntropometricaDiametroOsseo;
import br.com.projeto.projetotcc.api.repositories.AvaliacaoAntropometricaDiametroOsseoRepository;
import br.com.projeto.projetotcc.api.services.AvaliacaoAntropometricaDiametroOsseoService;

@Service
public class AvaliacaoAntropometricaDiametroOsseoServiceImpl implements AvaliacaoAntropometricaDiametroOsseoService {
	
	private static final Logger log = LoggerFactory.getLogger(AvaliacaoAntropometricaDiametroOsseoServiceImpl.class);
	
	@Autowired
	private AvaliacaoAntropometricaDiametroOsseoRepository avaliacaoAntropometricaDiametroOsseoRepository;
	
	public Optional<AvaliacaoAntropometricaDiametroOsseo> buscarPorId(Long id) {
		log.info("Buscando registro pelo ID {}", id);
		return this.avaliacaoAntropometricaDiametroOsseoRepository.findById(id);
	}
	
	public List<AvaliacaoAntropometricaDiametroOsseo> buscarPorAvaliacaoAntropometricaId(Long avaliacaoAntropometricaId) {
		log.info("Buscando registros por avaliacao antropometrica ID {}", avaliacaoAntropometricaId);
		return this.avaliacaoAntropometricaDiametroOsseoRepository.findByAvaliacaoAntropometricaId(avaliacaoAntropometricaId);
	}
	
	public AvaliacaoAntropometricaDiametroOsseo persistir(AvaliacaoAntropometricaDiametroOsseo avaliacaoAntropometricaDiametroOsseo) {
		log.info("Persistindo o registro: {}", avaliacaoAntropometricaDiametroOsseo);
		return this.avaliacaoAntropometricaDiametroOsseoRepository.save(avaliacaoAntropometricaDiametroOsseo);
	}
	
	public void remover(Long id) {
		log.info("Removendo o registro ID {}", id);
		this.avaliacaoAntropometricaDiametroOsseoRepository.deleteById(id);
	}
}
