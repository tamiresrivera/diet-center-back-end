package br.com.projeto.projetotcc.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.projetotcc.api.entities.CategoriaPlanoAlimentar;
import br.com.projeto.projetotcc.api.repositories.CategoriaPlanoAlimentarRepository;
import br.com.projeto.projetotcc.api.services.CategoriaPlanoAlimentarService;

@Service
public class CategoriaPlanoAlimentarServiceImpl implements CategoriaPlanoAlimentarService {

	private static final Logger log = LoggerFactory.getLogger(CategoriaPlanoAlimentarServiceImpl.class);

	@Autowired
	private CategoriaPlanoAlimentarRepository CategoriaPlanoAlimentarRepository;

	public List<CategoriaPlanoAlimentar> buscarPorProfissionalId(Long profissionalId) {
		log.info("Buscando todos as categorias de plano alimentar para o profissional ID {}", profissionalId);
		return this.CategoriaPlanoAlimentarRepository.findByProfissionalId(profissionalId);
	}
	
	public Optional<CategoriaPlanoAlimentar> buscarPorId(Long id) {
		log.info("Buscando uma categoria de plamo alimentar pelo ID {}", id);
		return this.CategoriaPlanoAlimentarRepository.findById(id);
	}
	
	public CategoriaPlanoAlimentar persistir(CategoriaPlanoAlimentar categoria) {
		log.info("Persistindo a categoria de plano alimentar: {}", categoria);
		return this.CategoriaPlanoAlimentarRepository.save(categoria);
	}
	
	public void remover(Long id) {
		log.info("Removendo a categoria ID {}", id);
		this.CategoriaPlanoAlimentarRepository.deleteById(id);
	}

}