package br.com.projeto.projetotcc.api.controllers;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.projetotcc.api.dtos.AvaliacaoAntropometricaDto;
import br.com.projeto.projetotcc.api.dtos.AvaliacaoAntropometricaPregaDto;
import br.com.projeto.projetotcc.api.dtos.AvaliacaoAntropometricaCircunferenciaDto;
import br.com.projeto.projetotcc.api.dtos.AvaliacaoAntropometricaDiametroOsseoDto;
import br.com.projeto.projetotcc.api.entities.AvaliacaoAntropometrica;
import br.com.projeto.projetotcc.api.entities.AvaliacaoAntropometricaCircunferencia;
import br.com.projeto.projetotcc.api.entities.AvaliacaoAntropometricaDiametroOsseo;
import br.com.projeto.projetotcc.api.entities.AvaliacaoAntropometricaPrega;
import br.com.projeto.projetotcc.api.entities.CategoriaAvaliacaoCircunferencia;
import br.com.projeto.projetotcc.api.entities.CategoriaAvaliacaoDiametroOsseo;
import br.com.projeto.projetotcc.api.entities.CategoriaAvaliacaoPrega;
import br.com.projeto.projetotcc.api.entities.Paciente;
import br.com.projeto.projetotcc.api.response.Response;
import br.com.projeto.projetotcc.api.services.AvaliacaoAntropometricaCircunferenciaService;
import br.com.projeto.projetotcc.api.services.AvaliacaoAntropometricaDiametroOsseoService;
import br.com.projeto.projetotcc.api.services.AvaliacaoAntropometricaPregaService;
import br.com.projeto.projetotcc.api.services.AvaliacaoAntropometricaService;
import br.com.projeto.projetotcc.api.services.CategoriaAvaliacaoCircunferenciaService;
import br.com.projeto.projetotcc.api.services.CategoriaAvaliacaoDiametroOsseoService;
import br.com.projeto.projetotcc.api.services.CategoriaAvaliacaoPregaService;
import br.com.projeto.projetotcc.api.services.PacienteService;

@RestController
@RequestMapping("/api/avaliacao-antropometrica")
@CrossOrigin(origins = "*")
public class AvaliacaoAntropometricaController {
	
	private static final Logger log = LoggerFactory.getLogger(AvaliacaoAntropometricaController.class);
	
	@Autowired
	private AvaliacaoAntropometricaService avaliacaoAntropometricaService;
	
	@Autowired
	private AvaliacaoAntropometricaCircunferenciaService circunferenciaService;
	
	@Autowired
	private AvaliacaoAntropometricaPregaService pregaService;
	
	@Autowired
	private AvaliacaoAntropometricaDiametroOsseoService diametroService;
	
	@Autowired
	private CategoriaAvaliacaoCircunferenciaService categoriaCircunferenciaService;
	
	@Autowired
	private CategoriaAvaliacaoPregaService categoriaPregaService;
	
	@Autowired
	private CategoriaAvaliacaoDiametroOsseoService categoriaDiametroService;
	

	@Autowired
	private PacienteService pacienteService;
	
	public AvaliacaoAntropometricaController() {
	}
	
	/**
	 * Retorna as avaliações de um paciente;
	 * 
	 * @param pacienteId
	 * @return ResponseEntity<Response<AvaliacaoAntropometricaDto>>
	 */
	@GetMapping(value = "/paciente/{pacienteId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<List<AvaliacaoAntropometricaDto>>> listarPorPacienteId(
			@PathVariable("pacienteId") Long pacienteId) {
		log.info("Buscando todas as avaliações por ID do paciente: {}", pacienteId);
		Response<List<AvaliacaoAntropometricaDto>> response = new Response<List<AvaliacaoAntropometricaDto>>();

		Paciente paciente = pacienteService.buscarPorId(pacienteId).get();
		
		List<AvaliacaoAntropometrica> avaliacoes = this.avaliacaoAntropometricaService.buscarPorPacienteId(paciente.getId());
		List<AvaliacaoAntropometricaDto> avaliacoesDto = avaliacoes.stream()
				.map(avaliacao -> this.converterAvaliacaoAntropometricaDto(avaliacao))
				.collect(Collectors.toList());

		response.setData(avaliacoesDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna as avaliações de um usuário;
	 * 
	 * @param usuarioId
	 * @return ResponseEntity<Response<AvaliacaoAntropometricaDto>>
	 */
	@GetMapping(value = "/usuario/{usuarioId}")
	@PreAuthorize("hasAnyRole('USUARIO')")
	public ResponseEntity<Response<List<AvaliacaoAntropometricaDto>>> listarPorUsuarioId(
			@PathVariable("usuarioId") Long usuarioId) {
		log.info("Buscando todas as avaliações por ID do usuario: {}", usuarioId);
		Response<List<AvaliacaoAntropometricaDto>> response = new Response<List<AvaliacaoAntropometricaDto>>();

		Paciente paciente = pacienteService.buscarPorUsuarioId(usuarioId).get();
		
		List<AvaliacaoAntropometrica> avaliacoes = this.avaliacaoAntropometricaService.buscarPorPacienteId(paciente.getId());
		List<AvaliacaoAntropometricaDto> avaliacoesDto = avaliacoes.stream()
				.map(avaliacao -> this.converterAvaliacaoAntropometricaDto(avaliacao))
				.collect(Collectors.toList());

		response.setData(avaliacoesDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna as 12 últimas avaliações de um usuário;
	 * 
	 * @param usuarioId
	 * @return ResponseEntity<Response<AvaliacaoAntropometricaDto>>
	 */
	@GetMapping(value = "/usuario/{usuarioId}/ultimas")
	@PreAuthorize("hasAnyRole('USUARIO')")
	public ResponseEntity<Response<List<AvaliacaoAntropometricaDto>>> listarUltimasPorUsuarioId(
			@PathVariable("usuarioId") Long usuarioId) {
		log.info("Buscando todas as avaliações por ID do usuario: {}", usuarioId);
		Response<List<AvaliacaoAntropometricaDto>> response = new Response<List<AvaliacaoAntropometricaDto>>();

		Paciente paciente = pacienteService.buscarPorUsuarioId(usuarioId).get();
		
		List<AvaliacaoAntropometrica> avaliacoes = this.avaliacaoAntropometricaService.buscarUltimasPorUsuarioId(paciente.getId());
		List<AvaliacaoAntropometricaDto> avaliacoesDto = avaliacoes.stream()
				.map(avaliacao -> this.converterAvaliacaoAntropometricaDto(avaliacao))
				.collect(Collectors.toList());

		response.setData(avaliacoesDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna as 12 últimas avaliações de um usuário por paciente;
	 * 
	 * @param pacienteId
	 * @return ResponseEntity<Response<AvaliacaoAntropometricaDto>>
	 */
	@GetMapping(value = "/paciente/{pacienteId}/ultimas")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<List<AvaliacaoAntropometricaDto>>> listarUltimasPorPacienteId(
			@PathVariable("pacienteId") Long pacienteId) {
		log.info("Buscando todas as avaliações por ID do paciente: {}", pacienteId);
		Response<List<AvaliacaoAntropometricaDto>> response = new Response<List<AvaliacaoAntropometricaDto>>();

		Paciente paciente = pacienteService.buscarPorId(pacienteId).get();
		
		List<AvaliacaoAntropometrica> avaliacoes = this.avaliacaoAntropometricaService.buscarUltimasPorUsuarioId(paciente.getId());
		List<AvaliacaoAntropometricaDto> avaliacoesDto = avaliacoes.stream()
				.map(avaliacao -> this.converterAvaliacaoAntropometricaDto(avaliacao))
				.collect(Collectors.toList());

		response.setData(avaliacoesDto);
		return ResponseEntity.ok(response);
	}	
	
	/**
	 * Retorna uma avaliação por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<AvaliacaoAntropometricaDto>>
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<AvaliacaoAntropometricaDto>> listarPorId(@PathVariable("id") Long id) {
		log.info("Buscando avaliação por ID: {}", id);
		Response<AvaliacaoAntropometricaDto> response = new Response<AvaliacaoAntropometricaDto>();
		Optional<AvaliacaoAntropometrica> avaliacao = this.avaliacaoAntropometricaService.buscarPorId(id);

		if (!avaliacao.isPresent()) {
			log.info("Avaliação não encontrada para o ID: {}", id);
			response.getErrors().add("Avaliação não encontrada para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterAvaliacaoAntropometricaDto(avaliacao.get()));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Adiciona uma nova avaliação.
	 * 
	 * @param avaliacaoAntropometricaDto
	 * @param result
	 * @return ResponseEntity<Response<AvaliacaoAntropometricaDto>>
	 * @throws ParseException 
	 */
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<AvaliacaoAntropometricaDto>> adicionar(@Valid @RequestBody AvaliacaoAntropometricaDto avaliacaoAntropometricaDto,
			BindingResult result) throws ParseException {
		log.info("Adicionando avaliação: {}", avaliacaoAntropometricaDto.toString());
		Response<AvaliacaoAntropometricaDto> response = new Response<AvaliacaoAntropometricaDto>();
		validarPaciente(avaliacaoAntropometricaDto, result);
		AvaliacaoAntropometrica avaliacao = this.converterDtoParaAvaliacaoAntropometrica(avaliacaoAntropometricaDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando avaliação: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		avaliacao = this.avaliacaoAntropometricaService.persistir(avaliacao);
		avaliacaoAntropometricaDto.setId(avaliacao.getId());
		
		converterDtoParaAvaliacaoAntropometricaCircunferencia(avaliacaoAntropometricaDto);
		converterDtoParaAvaliacaoAntropometricaDiametroOsseo(avaliacaoAntropometricaDto);
		converterDtoParaAvaliacaoAntropometricaPrega(avaliacaoAntropometricaDto);
		
		response.setData(this.converterAvaliacaoAntropometricaDto(avaliacao));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Atualiza os dados de uma avaliação.
	 * 
	 * @param id
	 * @param avaliacaoAntropometricaDto
	 * @return ResponseEntity<Response<AvaliacaoAntropometrica>>
	 * @throws ParseException 
	 */
	@PutMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<AvaliacaoAntropometricaDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody AvaliacaoAntropometricaDto avaliacaoAntropometricaDto, BindingResult result) throws ParseException {
		log.info("Atualizando avaliação: {}", avaliacaoAntropometricaDto.toString());
		Response<AvaliacaoAntropometricaDto> response = new Response<AvaliacaoAntropometricaDto>();
		validarPaciente(avaliacaoAntropometricaDto, result);
		avaliacaoAntropometricaDto.setId(id);
		AvaliacaoAntropometrica avaliacao = this.converterDtoParaAvaliacaoAntropometrica(avaliacaoAntropometricaDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando avaliação: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		avaliacao = this.avaliacaoAntropometricaService.persistir(avaliacao);
		response.setData(this.converterAvaliacaoAntropometricaDto(avaliacao));
		return ResponseEntity.ok(response);
	}	
	
	/**
	 * Remove uma avaliação por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<AvaliacaoAntropometrica>>
	 */
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo avalicao: {}", id);
		Response<String> response = new Response<String>();
		Optional<AvaliacaoAntropometrica> avaliacao = this.avaliacaoAntropometricaService.buscarPorId(id);

		if (!avaliacao.isPresent()) {
			log.info("Erro ao remover devido a avaliação ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover avaliação. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.avaliacaoAntropometricaService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	/**
	 * Valida um paciente, verificando se ele é existente e válido no
	 * sistema.
	 * 
	 * @param avaliacaoAntropometricaDto
	 * @param result
	 */
	private void validarPaciente(AvaliacaoAntropometricaDto avaliacaoAntropometricaDto, BindingResult result) {
		if (avaliacaoAntropometricaDto.getPacienteId() == null) {
			result.addError(new ObjectError("paciente", "Paciente não informado."));
			return;
		}

		log.info("Validando paciente id {}: ", avaliacaoAntropometricaDto.getPacienteId());
		Optional<Paciente> paciente = this.pacienteService.buscarPorId(avaliacaoAntropometricaDto.getPacienteId());
		if (!paciente.isPresent()) {
			result.addError(new ObjectError("paciente", "Paciente não encontrado. ID inexistente."));
		}
	}
	
	/**
	 * Converte uma entidade AvaliacaoAntropometrica para seu respectivo DTO.
	 * 
	 * @param avaliacaoAntropometrica
	 * @return AvaliacaoAntropometricaDto
	 */
	private AvaliacaoAntropometricaDto converterAvaliacaoAntropometricaDto(AvaliacaoAntropometrica avaliacaoAntropometrica) {
		
		AvaliacaoAntropometricaDto avaliacaoAntropometricaDto = new AvaliacaoAntropometricaDto();
		avaliacaoAntropometricaDto.setId(avaliacaoAntropometrica.getId());
		avaliacaoAntropometricaDto.setDataHora(avaliacaoAntropometrica.getDataHora());
		avaliacaoAntropometricaDto.setAltura(String.valueOf(avaliacaoAntropometrica.getAltura()));
		avaliacaoAntropometricaDto.setPeso(String.valueOf(avaliacaoAntropometrica.getPeso()));
		avaliacaoAntropometricaDto.setImc(String.valueOf(avaliacaoAntropometrica.getImc()));
		avaliacaoAntropometricaDto.setPercentualMassaGorda(String.valueOf(avaliacaoAntropometrica.getPercentualMassaGorda()));
		avaliacaoAntropometricaDto.setPercentualMassaMagra(String.valueOf(avaliacaoAntropometrica.getPercentualMassaMagra()));
		avaliacaoAntropometricaDto.setMassaGorda(String.valueOf(avaliacaoAntropometrica.getMassaGorda()));
		avaliacaoAntropometricaDto.setMassaMagra(String.valueOf(avaliacaoAntropometrica.getMassaMagra()));
		avaliacaoAntropometricaDto.setPesoOsseo(String.valueOf(avaliacaoAntropometrica.getPesoOsseo()));
		avaliacaoAntropometricaDto.setPesoResidual(String.valueOf(avaliacaoAntropometrica.getPesoResidual()));
		avaliacaoAntropometricaDto.setPesoMuscular(String.valueOf(avaliacaoAntropometrica.getPesoMuscular()));
		avaliacaoAntropometricaDto.setAreaMuscularBraco(String.valueOf(avaliacaoAntropometrica.getAreaMuscularBraco()));
		avaliacaoAntropometricaDto.setAreaGorduraBraco(String.valueOf(avaliacaoAntropometrica.getAreaGorduraBraco()));
		
		Long pacienteId = pacienteService.buscarPorId(avaliacaoAntropometrica.getPaciente().getId()).get().getId();
		avaliacaoAntropometricaDto.setPacienteId(pacienteId);

		return avaliacaoAntropometricaDto;
	}
	
	/**
	 * Converte uma AvaliacaoAntropometricaDto para uma entidade AvaliacaoAntropometrica.
	 * 
	 * @param avaliacaoAntropometricaDto
	 * @param result
	 * @return AvaliacaoAntropometrica
	 * @throws ParseException 
	 */
	private AvaliacaoAntropometrica converterDtoParaAvaliacaoAntropometrica(AvaliacaoAntropometricaDto avaliacaoAntropometricaDto, BindingResult result) throws ParseException {
		AvaliacaoAntropometrica avaliacaoAntropometrica = new AvaliacaoAntropometrica();

		if (avaliacaoAntropometricaDto.getId() != null) {
			Optional<AvaliacaoAntropometrica> a = this.avaliacaoAntropometricaService.buscarPorId(avaliacaoAntropometricaDto.getId());
			if (a.isPresent()) {
				avaliacaoAntropometrica = a.get();
			} else {
				result.addError(new ObjectError("avaliacaoAntropometrica", "Avaliação não encontrada."));
			}
		} else {
			Paciente paciente = pacienteService.buscarPorId(avaliacaoAntropometricaDto.getPacienteId()).get();
			avaliacaoAntropometrica.setPaciente(paciente);
		}

		avaliacaoAntropometrica.setDataHora(avaliacaoAntropometricaDto.getDataHora());
		avaliacaoAntropometrica.setAltura(Double.parseDouble(avaliacaoAntropometricaDto.getAltura().replace(",",".")));
		avaliacaoAntropometrica.setPeso(Double.parseDouble(avaliacaoAntropometricaDto.getPeso().replace(",",".")));
		avaliacaoAntropometrica.setImc(Double.parseDouble(avaliacaoAntropometricaDto.getImc().replace(",",".")));
		avaliacaoAntropometrica.setPercentualMassaGorda(Double.parseDouble(avaliacaoAntropometricaDto.getPercentualMassaGorda().replace(",",".")));
		avaliacaoAntropometrica.setPercentualMassaMagra(Double.parseDouble(avaliacaoAntropometricaDto.getPercentualMassaMagra().replace(",",".")));
		avaliacaoAntropometrica.setMassaGorda(Double.parseDouble(avaliacaoAntropometricaDto.getMassaGorda().replace(",",".")));
		avaliacaoAntropometrica.setMassaMagra(Double.parseDouble(avaliacaoAntropometricaDto.getMassaMagra().replace(",",".")));
		avaliacaoAntropometrica.setPesoOsseo(Double.parseDouble(avaliacaoAntropometricaDto.getPesoOsseo().replace(",",".")));
		avaliacaoAntropometrica.setPesoResidual(Double.parseDouble(avaliacaoAntropometricaDto.getPesoResidual().replace(",",".")));
		avaliacaoAntropometrica.setPesoMuscular(Double.parseDouble(avaliacaoAntropometricaDto.getPesoMuscular().replace(",",".")));
		avaliacaoAntropometrica.setAreaMuscularBraco(Double.parseDouble(avaliacaoAntropometricaDto.getAreaMuscularBraco().replace(",",".")));
		avaliacaoAntropometrica.setAreaGorduraBraco(Double.parseDouble(avaliacaoAntropometricaDto.getAreaGorduraBraco().replace(",",".")));
		
		return avaliacaoAntropometrica;
	}
	
	/**
	 * Converte uma AvaliacaoAntropometricaCircunferenciaDto para uma entidade AvaliacaoAntropometricaCircunferencia e persiste na base de dados.
	 * 
	 * @param avaliacaoAntropometricaDto
	 */
	private void converterDtoParaAvaliacaoAntropometricaCircunferencia(AvaliacaoAntropometricaDto avaliacaoAntropometricaDto) {
		if (avaliacaoAntropometricaDto.getCircunferencias() != null) {
			List<AvaliacaoAntropometricaCircunferenciaDto> circs = avaliacaoAntropometricaDto.getCircunferencias();
			AvaliacaoAntropometrica avaliacao = avaliacaoAntropometricaService.buscarPorId(avaliacaoAntropometricaDto.getId()).get();
			
			for (AvaliacaoAntropometricaCircunferenciaDto c : circs) {
				CategoriaAvaliacaoCircunferencia categoria = categoriaCircunferenciaService.buscarPorId(c.getCategoriaAvaliacaoCircunferenciaId()).get();
				AvaliacaoAntropometricaCircunferencia circunferencia = new AvaliacaoAntropometricaCircunferencia();
				circunferencia.setValor(Double.parseDouble(c.getValor().replace(",",".")));
				circunferencia.setCategoriaAvaliacaoCircunferencia(categoria);
				circunferencia.setAvaliacaoAntropometrica(avaliacao);
				circunferenciaService.persistir(circunferencia);
		      }
		}
	}
	
	/**
	 * Converte uma AvaliacaoAntropometricaDiametroOsseoDto para uma entidade AvaliacaoAntropometricaDiametroOsseo e persiste na base de dados.
	 * 
	 * @param avaliacaoAntropometricaDto
	 */
	private void converterDtoParaAvaliacaoAntropometricaDiametroOsseo(AvaliacaoAntropometricaDto avaliacaoAntropometricaDto) {
		if (avaliacaoAntropometricaDto.getDiametros() != null) {
			List<AvaliacaoAntropometricaDiametroOsseoDto> diams = avaliacaoAntropometricaDto.getDiametros();
			AvaliacaoAntropometrica avaliacao = avaliacaoAntropometricaService.buscarPorId(avaliacaoAntropometricaDto.getId()).get();
			
			for (AvaliacaoAntropometricaDiametroOsseoDto c : diams) {
				CategoriaAvaliacaoDiametroOsseo categoria = categoriaDiametroService.buscarPorId(c.getCategoriaAvaliacaoDiametroOsseoId()).get();
				AvaliacaoAntropometricaDiametroOsseo diametro = new AvaliacaoAntropometricaDiametroOsseo();
				diametro.setValor(Double.parseDouble(c.getValor().replace(",",".")));
				diametro.setCategoriaAvaliacaoDiametroOsseo(categoria);
				diametro.setAvaliacaoAntropometrica(avaliacao);
				diametroService.persistir(diametro);
		      }
		}
	}
	
	/**
	 * Converte uma AvaliacaoAntropometricaPregaDto para uma entidade AvaliacaoAntropometricaPrega e persiste na base de dados.
	 * 
	 * @param avaliacaoAntropometricaDto
	 */
	private void converterDtoParaAvaliacaoAntropometricaPrega(AvaliacaoAntropometricaDto avaliacaoAntropometricaDto) {
		if (avaliacaoAntropometricaDto.getPregas() != null) {
			List<AvaliacaoAntropometricaPregaDto> pregas = avaliacaoAntropometricaDto.getPregas();
			AvaliacaoAntropometrica avaliacao = avaliacaoAntropometricaService.buscarPorId(avaliacaoAntropometricaDto.getId()).get();
			
			for (AvaliacaoAntropometricaPregaDto c : pregas) {
				CategoriaAvaliacaoPrega categoria = categoriaPregaService.buscarPorId(c.getCategoriaAvaliacaoPregaId()).get();
				AvaliacaoAntropometricaPrega prega = new AvaliacaoAntropometricaPrega();
				prega.setValor(Double.parseDouble(c.getValor().replace(",",".")));
				prega.setCategoriaAvaliacaoPrega(categoria);
				prega.setAvaliacaoAntropometrica(avaliacao);
				pregaService.persistir(prega);
		      }
		}
	}

}
