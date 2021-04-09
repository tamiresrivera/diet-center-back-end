package br.com.projeto.projetotcc.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.projetotcc.api.entities.CategoriaAvaliacaoPrega;
import br.com.projeto.projetotcc.api.repositories.CategoriaAvaliacaoPregaRepository;
import br.com.projeto.projetotcc.api.services.CategoriaAvaliacaoPregaService;

@Service
public class CategoriaAvaliacaoPregaServiceImpl implements CategoriaAvaliacaoPregaService {

	private static final Logger log = LoggerFactory.getLogger(CategoriaAvaliacaoPregaServiceImpl.class);

	@Autowired
	private CategoriaAvaliacaoPregaRepository CategoriaAvaliacaoPregaRepository;

	public List<CategoriaAvaliacaoPrega> buscarPorProfissionalId(Long profissionalId) {
		log.info("Buscando todos as categorias de pregas para o profissional ID {}", profissionalId);
		return this.CategoriaAvaliacaoPregaRepository.findByProfissionalId(profissionalId);
	}
	
	public Optional<CategoriaAvaliacaoPrega> buscarPorId(Long id) {
		log.info("Buscando uma categoria de pregas pelo ID {}", id);
		return this.CategoriaAvaliacaoPregaRepository.findById(id);
	}
	
	public CategoriaAvaliacaoPrega persistir(CategoriaAvaliacaoPrega categoria) {
		log.info("Persistindo a categoria de pregas: {}", categoria);
		return this.CategoriaAvaliacaoPregaRepository.save(categoria);
	}
	
	public void remover(Long id) {
		log.info("Removendo a categoria ID {}", id);
		this.CategoriaAvaliacaoPregaRepository.deleteById(id);
	}

}