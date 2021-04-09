package br.com.projeto.projetotcc.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.projetotcc.api.entities.Receita;
import br.com.projeto.projetotcc.api.repositories.ReceitaRepository;
import br.com.projeto.projetotcc.api.services.ReceitaService;

@Service
public class ReceitaServiceImpl implements ReceitaService {

	private static final Logger log = LoggerFactory.getLogger(ReceitaServiceImpl.class);

	@Autowired
	private ReceitaRepository receitaRepository;

	public List<Receita> buscarPorProfissionalId(Long profissionalId) {
		log.info("Buscando todos as receitas para o profissional ID {}", profissionalId);
		return this.receitaRepository.findByProfissionalId(profissionalId);
	}
	
	public List<Receita> buscarPorCategoriaReceitaId(Long categoriaId) {
		log.info("Buscando todos as receitas para a categoria ID {}", categoriaId);
		return this.receitaRepository.findByCategoriaReceitaId(categoriaId);
	}
	
	public Optional<Receita> buscarPorId(Long id) {
		log.info("Buscando uma receita pelo ID {}", id);
		return this.receitaRepository.findById(id);
	}
	
	public Receita persistir(Receita receita) {
		log.info("Persistindo a receita: {}", receita);
		return this.receitaRepository.save(receita);
	}
	
	public void remover(Long id) {
		log.info("Removendo a receita ID {}", id);
		this.receitaRepository.deleteById(id);
	}

}