package br.com.projeto.projetotcc.api.controllers;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.projetotcc.api.response.Response;

import br.com.projeto.projetotcc.api.dtos.AgendaDto;
import br.com.projeto.projetotcc.api.entities.Agenda;
import br.com.projeto.projetotcc.api.entities.Paciente;
import br.com.projeto.projetotcc.api.entities.Profissional;
import br.com.projeto.projetotcc.api.entities.Usuario;
import br.com.projeto.projetotcc.api.services.AgendaService;
import br.com.projeto.projetotcc.api.services.PacienteService;
import br.com.projeto.projetotcc.api.services.ProfissionalService;
import br.com.projeto.projetotcc.api.services.UsuarioService;

@RestController
@RequestMapping("/api/agenda")
@CrossOrigin(origins = "*")
public class AgendaController {

	private static final Logger log = LoggerFactory.getLogger(CadastroUsuarioController.class);
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private AgendaService agendaService;
	
	@Autowired
	private PacienteService pacienteService;
	
	@Autowired
	private ProfissionalService profissionalService;
	
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
	/**
	 * Retorna a agenda de um profissional.
	 * 
	 * @param usuarioId
	 * @return ResponseEntity<Response<AgendaDto>>
	 */
	@GetMapping(value = "/profissional/{usuarioId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Page<AgendaDto>>> listarPorProfissionalId(
			@PathVariable("usuarioId") Long usuarioId,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "data_hora") String ord,
			@RequestParam(value = "dir", defaultValue = "ASC") String dir) {
		log.info("Buscando agenda por ID do profissional: {}, página: {}", usuarioId, pag);
		Response<Page<AgendaDto>> response = new Response<Page<AgendaDto>>();
		
		Optional<Usuario> usuario = this.usuarioService.buscarPorId(usuarioId);

		PageRequest pageRequest = PageRequest.of(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<Agenda> agenda = this.agendaService.buscarPorProfissionalId(usuario.get().getProfissional().getId(), pageRequest);
		Page<AgendaDto> agendaDto = agenda.map(a -> this.converterAgendaDto(a));

		response.setData(agendaDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna a agenda de horarios disponíveis de um profissional.
	 * 
	 * @param profissionalId
	 * @return ResponseEntity<Response<AgendaDto>>
	 */
	@GetMapping(value = "/profissional/{profissionalId}/disponivel")
	@PreAuthorize("hasAnyRole('USUARIO')")
	public ResponseEntity<Response<List<AgendaDto>>> listarDisponiveisPorProfissionalId(
			@PathVariable("profissionalId") Long profissionalId) {
		log.info("Buscando agenda disponível por ID do profissional: {}", profissionalId);
		Response<List<AgendaDto>> response = new Response<List<AgendaDto>>();
		
		List<Agenda> agenda = this.agendaService.buscarDisponiveisPorProfissionalId(profissionalId);
		List<AgendaDto> agendaDto = agenda.stream()
				.map(a -> this.converterAgendaDto(a))
				.collect(Collectors.toList());

		response.setData(agendaDto);
		return ResponseEntity.ok(response);
	}	
	
	/**
	 * Retorna toda a agenda de um profissional.
	 *
	 * @param profissionalId
	 * @return ResponseEntity<Response<AgendaDto>>
	 */
	@GetMapping(value = "/profissional/{usuarioId}/todos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<List<AgendaDto>>> listarTodosPorProfissionalId(
			@PathVariable("usuarioId") Long usuarioId) {
		log.info("Buscando toda agenda por ID do profissional: {}", usuarioId);
		Response<List<AgendaDto>> response = new Response<List<AgendaDto>>();

		Optional<Usuario> usuario = this.usuarioService.buscarPorId(usuarioId);
		
		List<Agenda> agenda = this.agendaService.buscarTodosPorProfissionalId(usuario.get().getProfissional().getId());
		List<AgendaDto> agendaDto = agenda.stream()
				.map(a -> this.converterAgendaDto(a))
				.collect(Collectors.toList());

		response.setData(agendaDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Atualiza o paciente de um registro da agenda.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<Agenda>>
	 */
	@GetMapping(value = "/desmarcar/{id}")
	public ResponseEntity<Response<AgendaDto>> removerPaciente(@PathVariable("id") Long id) {
		log.info("Atualizando agenda: {}", id);
		Response<AgendaDto> response = new Response<AgendaDto>();
		Optional<Agenda> a = this.agendaService.buscarPorId(id);

		if (!a.isPresent()) {
			log.info("Erro ao atualizar agenda devido a agenda ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao atualizar agenda. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		Agenda agenda = a.get();
		agenda.setPaciente(null);
		agenda = this.agendaService.persistir(agenda);
		response.setData(converterAgendaDto(agenda));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Adiciona um novo horário na agenda.
	 * 
	 * @param agendaDto
	 * @param result
	 * @return ResponseEntity<Response<AgendaDto>>
	 * @throws ParseException 
	 */
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<AgendaDto>> adicionar(@Valid @RequestBody AgendaDto agendaDto,
			BindingResult result) throws ParseException {
		log.info("Adicionando horário na agenda: {}", agendaDto.toString());
		Response<AgendaDto> response = new Response<AgendaDto>();
		validarAgenda(agendaDto, result);
		Agenda agenda = this.converterDtoParaAgenda(agendaDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando horário da agenda: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		agenda = this.agendaService.persistir(agenda);
		response.setData(this.converterAgendaDto(agenda));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Remove uma agenda por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<Agenda>>
	 */
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo agenda: {}", id);
		Response<String> response = new Response<String>();
		Optional<Agenda> agenda = this.agendaService.buscarPorId(id);

		if (!agenda.isPresent()) {
			log.info("Erro ao remover devido a agenda ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover agenda. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.agendaService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	/**
	 * Retorna um registro da agenda por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<AgendaDto>>
	 */
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<AgendaDto>> listarPorId(@PathVariable("id") Long id) {
		log.info("Buscando agenda por ID: {}", id);
		Response<AgendaDto> response = new Response<AgendaDto>();
		Optional<Agenda> agenda = this.agendaService.buscarPorId(id);

		if (!agenda.isPresent()) {
			log.info("Agenda não encontrado para o ID: {}", id);
			response.getErrors().add("Agenda não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterAgendaDto(agenda.get()));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna a proxima consulta agendada de um usuario paciente
	 * 
	 * @param usuarioId
	 * @return ResponseEntity<Response<AgendaDto>>
	 */
	@GetMapping(value = "usuario/{usuarioId}/proxima")
	@PreAuthorize("hasAnyRole('USUARIO')")
	public ResponseEntity<Response<AgendaDto>> listarProximaPorUsuarioId(@PathVariable("usuarioId") Long usuarioId) {
		log.info("Buscando agenda por usuario ID: {}", usuarioId);
		Response<AgendaDto> response = new Response<AgendaDto>();

		Optional<Agenda> agenda = this.agendaService.buscarUltimaPorUsuarioId(usuarioId);

		if (agenda.isPresent()) {
			response.setData(this.converterAgendaDto(agenda.get()));
		}
		else {
			AgendaDto agendaDto = new AgendaDto();
			response.setData(agendaDto);
		}
		return ResponseEntity.ok(response);
	}
	
	
	/**
	 * Atualiza os dados de um registro na agenda.
	 * 
	 * @param id
	 * @param agendaDto
	 * @return ResponseEntity<Response<Agenda>>
	 * @throws ParseException 
	 */
	@PutMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<AgendaDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody AgendaDto agendaDto, BindingResult result) throws ParseException {
		log.info("Atualizando agenda: {}", agendaDto.toString());
		Response<AgendaDto> response = new Response<AgendaDto>();
		validarAgenda(agendaDto, result);
		agendaDto.setId(id);
		Agenda agenda = this.converterDtoParaAgenda(agendaDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando agenda: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		agenda = this.agendaService.persistir(agenda);
		response.setData(this.converterAgendaDto(agenda));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Atualiza os dados de um registro na agenda.
	 * 
	 * @param id
	 * @param agendaDto
	 * @return ResponseEntity<Response<Agenda>>
	 * @throws ParseException 
	 */
	@PutMapping(value = "/usuario/{usuarioId}")
	@PreAuthorize("hasAnyRole('USUARIO')")
	public ResponseEntity<Response<AgendaDto>> agendar(@PathVariable("usuarioId") Long usuarioId,
			@Valid @RequestBody AgendaDto agendaDto, BindingResult result) throws ParseException {
		log.info("Atualizando agenda: {}", agendaDto.toString());
		Response<AgendaDto> response = new Response<AgendaDto>();
		validarAgenda(agendaDto, result);
		
		Paciente paciente = pacienteService.buscarPorUsuarioId(usuarioId).get();
		Profissional profissional = profissionalService.buscarPorId(agendaDto.getProfissionalId()).get();
		
		agendaDto.setPacienteId(paciente.getId());
		Agenda agenda = this.converterDtoParaAgenda(agendaDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando agenda: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		paciente.setProfissional(profissional);
		this.pacienteService.persistir(paciente);
		agenda = this.agendaService.persistir(agenda);
		response.setData(this.converterAgendaDto(agenda));
		return ResponseEntity.ok(response);
	}
	
	 /* Converte uma entidade agenda para seu respectivo DTO.
	 * 
	 * @param agenda
	 * @return AgendaDto
	 */
	private AgendaDto converterAgendaDto(Agenda agenda) {
		AgendaDto agendaDto = new AgendaDto();
		agendaDto.setId(agenda.getId());
		agendaDto.setDataHora(agenda.getDataHora());
		agendaDto.setProfissionalId(agenda.getProfissional().getId());
		if (agenda.getPaciente() != null) {
			agendaDto.setPacienteId(agenda.getPaciente().getId());
			agendaDto.setNomePaciente(agenda.getPaciente().getUsuario().getNome());
		}

		return agendaDto;
	}
	
	/**
	 * Converte uma AgendaDto para uma entidade Agenda.
	 * 
	 * @param agendaDto
	 * @param result
	 * @return Agenda
	 * @throws ParseException 
	 */
	private Agenda converterDtoParaAgenda(AgendaDto agendaDto, BindingResult result) throws ParseException {
		Agenda agenda = new Agenda();
		
		if (agendaDto.getId() != null) {
			Optional<Agenda> a = this.agendaService.buscarPorId(agendaDto.getId());
			if (a.isPresent()) {
				agenda = a.get();
				if (agendaDto.getPacienteId() != null) {
					Paciente paciente = pacienteService.buscarPorId(agendaDto.getPacienteId()).get();
					agenda.setPaciente(paciente);
				}
			} else {
				result.addError(new ObjectError("agenda", "Agenda não encontrada."));
			}
		} else {
			Profissional profissional = profissionalService.buscarPorUsuarioId(agendaDto.getProfissionalId()).get();
			agenda.setProfissional(profissional);
		}

		if (agendaDto.getDataHora() != null) {
			agenda.setDataHora(agendaDto.getDataHora());
		}
		
		return agenda;
	}
	
	/**
	 * Valida um profissional, verificando se ele é existente e válido no
	 * sistema.
	 * 
	 * @param agendaDto
	 * @param result
	 */
	private void validarAgenda(AgendaDto agendaDto, BindingResult result) {
		if (agendaDto.getProfissionalId() == null) {
			result.addError(new ObjectError("profissional", "Profissional não informado."));
			return;
		}

		Long profissionalId = profissionalService.buscarPorUsuarioId(agendaDto.getProfissionalId()).get().getId();
		log.info("Validando profissional id {}: ", profissionalId);
		Optional<Profissional> profissional = this.profissionalService.buscarPorId(profissionalId);
		if (!profissional.isPresent()) {
			result.addError(new ObjectError("profissional", "Profissional não encontrado. ID inexistente."));
		}
	}
	
}
