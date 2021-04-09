package br.com.projeto.projetotcc.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.projetotcc.api.entities.CategoriaAvaliacaoDiametroOsseo;
import br.com.projeto.projetotcc.api.repositories.CategoriaAvaliacaoDiametroOsseoRepository;
import br.com.projeto.projetotcc.api.services.CategoriaAvaliacaoDiametroOsseoService;

@Service
public class CategoriaAvaliacaoDiametroOsseoServiceImpl implements CategoriaAvaliacaoDiametroOsseoService {

	private static final Logger log = LoggerFactory.getLogger(CategoriaAvaliacaoDiametroOsseoServiceImpl.class);

	@Autowired
	private CategoriaAvaliacaoDiametroOsseoRepository CategoriaAvaliacaoDiametroOsseoRepository;

	public List<CategoriaAvaliacaoDiametroOsseo> buscarPorProfissionalId(Long profissionalId) {
		log.info("Buscando todos as categorias de diâmetros para o profissional ID {}", profissionalId);
		return this.CategoriaAvaliacaoDiametroOsseoRepository.findByProfissionalId(profissionalId);
	}
	
	public Optional<CategoriaAvaliacaoDiametroOsseo> buscarPorId(Long id) {
		log.info("Buscando uma categoria de diâmetro pelo ID {}", id);
		return this.CategoriaAvaliacaoDiametroOsseoRepository.findById(id);
	}
	
	public CategoriaAvaliacaoDiametroOsseo persistir(CategoriaAvaliacaoDiametroOsseo categoria) {
		log.info("Persistindo a categoria de diâmetro: {}", categoria);
		return this.CategoriaAvaliacaoDiametroOsseoRepository.save(categoria);
	}
	
	public void remover(Long id) {
		log.info("Removendo a categoria ID {}", id);
		this.CategoriaAvaliacaoDiametroOsseoRepository.deleteById(id);
	}

}