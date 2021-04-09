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

import br.com.projeto.projetotcc.api.dtos.AvaliacaoAntropometricaPregaDto;
import br.com.projeto.projetotcc.api.entities.AvaliacaoAntropometrica;
import br.com.projeto.projetotcc.api.entities.AvaliacaoAntropometricaPrega;
import br.com.projeto.projetotcc.api.response.Response;
import br.com.projeto.projetotcc.api.services.AvaliacaoAntropometricaPregaService;
import br.com.projeto.projetotcc.api.services.AvaliacaoAntropometricaService;

@RestController
@RequestMapping("/api/avaliacao-prega")
@CrossOrigin(origins = "*")
public class AvaliacaoAntropometricaPregaController {
	
	private static final Logger log = LoggerFactory.getLogger(AvaliacaoAntropometricaPregaController.class);
	
	@Autowired
	private AvaliacaoAntropometricaService avaliacaoAntropometricaService;
	
	@Autowired
	private AvaliacaoAntropometricaPregaService avaliacaoAntropometricaPregaService;
	
	/**
	 * Retorna as pregas de uma avaliacao;
	 * 
	 * @param avaliacaoId
	 * @return ResponseEntity<Response<AvaliacaoAntropometricaPregaDto>>
	 */
	@GetMapping(value = "/avaliacao/{avaliacaoId}")
	public ResponseEntity<Response<List<AvaliacaoAntropometricaPregaDto>>> listarPorAvaliacaoId(
			@PathVariable("avaliacaoId") Long avaliacaoId) {
		log.info("Buscando todas as pregas cutâneas por ID da avaliação: {}", avaliacaoId);
		Response<List<AvaliacaoAntropometricaPregaDto>> response = new Response<List<AvaliacaoAntropometricaPregaDto>>();

		AvaliacaoAntropometrica avaliacao = avaliacaoAntropometricaService.buscarPorId(avaliacaoId).get();
		
		List<AvaliacaoAntropometricaPrega> pregas = this.avaliacaoAntropometricaPregaService.buscarPorAvaliacaoAntropometricaId(avaliacao.getId());
		List<AvaliacaoAntropometricaPregaDto> pregasDto = pregas.stream()
				.map(prega -> this.converterAvaliacaoAntropometricaPregaDto(prega))
				.collect(Collectors.toList());

		response.setData(pregasDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Converte uma entidade AvaliacaoAntropometricaPrega para seu respectivo DTO.
	 * 
	 * @param avaliacaoAntropometricaPrega
	 * @return AvaliacaoAntropometricaPregaDto
	 */
	private AvaliacaoAntropometricaPregaDto converterAvaliacaoAntropometricaPregaDto(AvaliacaoAntropometricaPrega avaliacaoAntropometricaPrega) {
		
		AvaliacaoAntropometricaPregaDto pregaDto = new AvaliacaoAntropometricaPregaDto();
		pregaDto.setId(Optional.of(avaliacaoAntropometricaPrega.getId()));
		pregaDto.setCategoriaAvaliacaoPregaId(avaliacaoAntropometricaPrega.getCategoriaAvaliacaoPrega().getId());
		pregaDto.setDescricaoCategoria(avaliacaoAntropometricaPrega.getCategoriaAvaliacaoPrega().getDescricao());
		pregaDto.setValor(String.valueOf(avaliacaoAntropometricaPrega.getValor()));
		
		Long avaliacaoId = avaliacaoAntropometricaService.buscarPorId(avaliacaoAntropometricaPrega.getAvaliacaoAntropometrica().getId()).get().getId();
		pregaDto.setAvaliacaoAntropometricaId(avaliacaoId);

		return pregaDto;
	}

}
