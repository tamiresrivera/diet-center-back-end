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

import br.com.projeto.projetotcc.api.dtos.AvaliacaoAntropometricaCircunferenciaDto;
import br.com.projeto.projetotcc.api.entities.AvaliacaoAntropometrica;
import br.com.projeto.projetotcc.api.entities.AvaliacaoAntropometricaCircunferencia;
import br.com.projeto.projetotcc.api.response.Response;
import br.com.projeto.projetotcc.api.services.AvaliacaoAntropometricaCircunferenciaService;
import br.com.projeto.projetotcc.api.services.AvaliacaoAntropometricaService;

@RestController
@RequestMapping("/api/avaliacao-circunferencia")
@CrossOrigin(origins = "*")
public class AvaliacaoAntropometricaCircunferenciaController {
	
	private static final Logger log = LoggerFactory.getLogger(AvaliacaoAntropometricaCircunferenciaController.class);
	
	@Autowired
	private AvaliacaoAntropometricaService avaliacaoAntropometricaService;
	
	@Autowired
	private AvaliacaoAntropometricaCircunferenciaService avaliacaoAntropometricaCircunferenciaService;
	
	/**
	 * Retorna as cirunferencias de uma avaliacao;
	 * 
	 * @param avaliacaoId
	 * @return ResponseEntity<Response<AvaliacaoAntropometricaCircunferenciaDto>>
	 */
	@GetMapping(value = "/avaliacao/{avaliacaoId}")
	public ResponseEntity<Response<List<AvaliacaoAntropometricaCircunferenciaDto>>> listarPorAvaliacaoId(
			@PathVariable("avaliacaoId") Long avaliacaoId) {
		log.info("Buscando todas as circunferências por ID da avaliação: {}", avaliacaoId);
		Response<List<AvaliacaoAntropometricaCircunferenciaDto>> response = new Response<List<AvaliacaoAntropometricaCircunferenciaDto>>();

		AvaliacaoAntropometrica avaliacao = avaliacaoAntropometricaService.buscarPorId(avaliacaoId).get();
		
		List<AvaliacaoAntropometricaCircunferencia> circunferencias = this.avaliacaoAntropometricaCircunferenciaService.buscarPorAvaliacaoAntropometricaId(avaliacao.getId());
		List<AvaliacaoAntropometricaCircunferenciaDto> circunferenciasDto = circunferencias.stream()
				.map(circunferencia -> this.converterAvaliacaoAntropometricaCircunferenciaDto(circunferencia))
				.collect(Collectors.toList());

		response.setData(circunferenciasDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Converte uma entidade AvaliacaoAntropometrica para seu respectivo DTO.
	 * 
	 * @param avaliacaoAntropometrica
	 * @return AvaliacaoAntropometricaDto
	 */
	private AvaliacaoAntropometricaCircunferenciaDto converterAvaliacaoAntropometricaCircunferenciaDto(AvaliacaoAntropometricaCircunferencia avaliacaoAntropometricaCircunferencia) {
		
		AvaliacaoAntropometricaCircunferenciaDto circunferenciaDto = new AvaliacaoAntropometricaCircunferenciaDto();
		circunferenciaDto.setId(Optional.of(avaliacaoAntropometricaCircunferencia.getId()));
		circunferenciaDto.setCategoriaAvaliacaoCircunferenciaId(avaliacaoAntropometricaCircunferencia.getCategoriaAvaliacaoCircunferencia().getId());
		circunferenciaDto.setDescricaoCategoria(avaliacaoAntropometricaCircunferencia.getCategoriaAvaliacaoCircunferencia().getDescricao());
		circunferenciaDto.setValor(String.valueOf(avaliacaoAntropometricaCircunferencia.getValor()));
		
		Long avaliacaoId = avaliacaoAntropometricaService.buscarPorId(avaliacaoAntropometricaCircunferencia.getAvaliacaoAntropometrica().getId()).get().getId();
		circunferenciaDto.setAvaliacaoAntropometricaId(avaliacaoId);

		return circunferenciaDto;
	}

}
