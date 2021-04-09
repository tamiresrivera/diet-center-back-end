package br.com.projeto.projetotcc.api.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.projetotcc.api.dtos.AvaliacaoAntropometricaDiametroOsseoDto;
import br.com.projeto.projetotcc.api.entities.AvaliacaoAntropometrica;
import br.com.projeto.projetotcc.api.entities.AvaliacaoAntropometricaDiametroOsseo;
import br.com.projeto.projetotcc.api.response.Response;
import br.com.projeto.projetotcc.api.services.AvaliacaoAntropometricaDiametroOsseoService;
import br.com.projeto.projetotcc.api.services.AvaliacaoAntropometricaService;

@RestController
@RequestMapping("/api/avaliacao-diametro")
@CrossOrigin(origins = "*")
public class AvaliacaoAntropometricaDiametroOsseoController {
	
	private static final Logger log = LoggerFactory.getLogger(AvaliacaoAntropometricaDiametroOsseoController.class);
	
	@Autowired
	private AvaliacaoAntropometricaService avaliacaoAntropometricaService;
	
	@Autowired
	private AvaliacaoAntropometricaDiametroOsseoService avaliacaoAntropometricaDiametroOsseoService;
	
	/**
	 * Retorna as pregas de uma avaliacao;
	 * 
	 * @param avaliacaoId
	 * @return ResponseEntity<Response<AvaliacaoAntropometricaDiametroOsseoDto>>
	 */
	@GetMapping(value = "/avaliacao/{avaliacaoId}")
	public ResponseEntity<Response<List<AvaliacaoAntropometricaDiametroOsseoDto>>> listarPorAvaliacaoId(
			@PathVariable("avaliacaoId") Long avaliacaoId) {
		log.info("Buscando todos os diâmetros osseos por ID da avaliação: {}", avaliacaoId);
		Response<List<AvaliacaoAntropometricaDiametroOsseoDto>> response = new Response<List<AvaliacaoAntropometricaDiametroOsseoDto>>();

		AvaliacaoAntropometrica avaliacao = avaliacaoAntropometricaService.buscarPorId(avaliacaoId).get();
		
		List<AvaliacaoAntropometricaDiametroOsseo> diametros = this.avaliacaoAntropometricaDiametroOsseoService.buscarPorAvaliacaoAntropometricaId(avaliacao.getId());
		List<AvaliacaoAntropometricaDiametroOsseoDto> diametrosDto = diametros.stream()
				.map(diametro -> this.converterAvaliacaoAntropometricaDiametroOsseoDto(diametro))
				.collect(Collectors.toList());

		response.setData(diametrosDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Converte uma entidade AvaliacaoAntropometricaDiametroOsseo para seu respectivo DTO.
	 * 
	 * @param avaliacaoAntropometricaDiametroOsseo
	 * @return AvaliacaoAntropometricaDiametroOsseoDto
	 */
	private AvaliacaoAntropometricaDiametroOsseoDto converterAvaliacaoAntropometricaDiametroOsseoDto(AvaliacaoAntropometricaDiametroOsseo avaliacaoAntropometricaDiametroOsseo) {
		
		AvaliacaoAntropometricaDiametroOsseoDto diametroDto = new AvaliacaoAntropometricaDiametroOsseoDto();
		diametroDto.setId(Optional.of(avaliacaoAntropometricaDiametroOsseo.getId()));
		diametroDto.setCategoriaAvaliacaoDiametroOsseoId(avaliacaoAntropometricaDiametroOsseo.getCategoriaAvaliacaoDiametroOsseo().getId());
		diametroDto.setDescricaoCategoria(avaliacaoAntropometricaDiametroOsseo.getCategoriaAvaliacaoDiametroOsseo().getDescricao());
		diametroDto.setValor(String.valueOf(avaliacaoAntropometricaDiametroOsseo.getValor()));
		
		Long avaliacaoId = avaliacaoAntropometricaService.buscarPorId(avaliacaoAntropometricaDiametroOsseo.getAvaliacaoAntropometrica().getId()).get().getId();
		diametroDto.setAvaliacaoAntropometricaId(avaliacaoId);

		return diametroDto;
	}

}
