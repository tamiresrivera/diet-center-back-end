package br.com.projeto.projetotcc.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.projetotcc.api.entities.CategoriaReceita;
import br.com.projeto.projetotcc.api.repositories.CategoriaReceitaRepository;
import br.com.projeto.projetotcc.api.services.CategoriaReceitaService;

@Service
public class CategoriaReceitaServiceImpl implements CategoriaReceitaService {

	private static final Logger log = LoggerFactory.getLogger(CategoriaReceitaServiceImpl.class);

	@Autowired
	private CategoriaReceitaRepository categoriaReceitaRepository;

	public List<CategoriaReceita> buscarPorProfissionalId(Long profissionalId) {
		log.info("Buscando todos as categorias de receitas para o profissional ID {}", profissionalId);
		return this.categoriaReceitaRepository.findByProfissionalId(profissionalId);
	}
	
	public Optional<CategoriaReceita> buscarPorId(Long id) {
		log.info("Buscando uma cagtegoria de receita pelo ID {}", id);
		return this.categoriaReceitaRepository.findById(id);
	}
	
	public CategoriaReceita persistir(CategoriaReceita categoria) {
		log.info("Persistindo a categoria de receita: {}", categoria);
		return this.categoriaReceitaRepository.save(categoria);
	}
	
	public void remover(Long id) {
		log.info("Removendo a categoria ID {}", id);
		this.categoriaReceitaRepository.deleteById(id);
	}

}