package br.com.projeto.projetotcc.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.projetotcc.api.entities.CategoriaAvaliacaoCircunferencia;
import br.com.projeto.projetotcc.api.repositories.CategoriaAvaliacaoCircunferenciaRepository;
import br.com.projeto.projetotcc.api.services.CategoriaAvaliacaoCircunferenciaService;

@Service
public class CategoriaAvaliacaoCircunferenciaServiceImpl implements CategoriaAvaliacaoCircunferenciaService {

	private static final Logger log = LoggerFactory.getLogger(CategoriaAvaliacaoCircunferenciaServiceImpl.class);

	@Autowired
	private CategoriaAvaliacaoCircunferenciaRepository CategoriaAvaliacaoCircunferenciaRepository;

	public List<CategoriaAvaliacaoCircunferencia> buscarPorProfissionalId(Long profissionalId) {
		log.info("Buscando todos as categorias de circunfêrencias para o profissional ID {}", profissionalId);
		return this.CategoriaAvaliacaoCircunferenciaRepository.findByProfissionalId(profissionalId);
	}
	
	public Optional<CategoriaAvaliacaoCircunferencia> buscarPorId(Long id) {
		log.info("Buscando uma categoria de circunferência pelo ID {}", id);
		return this.CategoriaAvaliacaoCircunferenciaRepository.findById(id);
	}
	
	public CategoriaAvaliacaoCircunferencia persistir(CategoriaAvaliacaoCircunferencia categoria) {
		log.info("Persistindo a categoria de receita: {}", categoria);
		return this.CategoriaAvaliacaoCircunferenciaRepository.save(categoria);
	}
	
	public void remover(Long id) {
		log.info("Removendo a categoria ID {}", id);
		this.CategoriaAvaliacaoCircunferenciaRepository.deleteById(id);
	}

}