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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.projetotcc.api.dtos.PacienteDto;
import br.com.projeto.projetotcc.api.entities.CategoriaReceita;
import br.com.projeto.projetotcc.api.entities.Paciente;
import br.com.projeto.projetotcc.api.entities.Usuario;
import br.com.projeto.projetotcc.api.response.Response;
import br.com.projeto.projetotcc.api.services.CategoriaReceitaService;
import br.com.projeto.projetotcc.api.services.PacienteService;
import br.com.projeto.projetotcc.api.services.UsuarioService;
import br.com.projeto.projetotcc.api.utils.PasswordUtils;

@RestController
@RequestMapping("/api/paciente")
@CrossOrigin(origins = "*")
public class PacienteController {
	
	private static final Logger log = LoggerFactory.getLogger(CadastroUsuarioController.class);
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PacienteService pacienteService;
	
	@Autowired
	private CategoriaReceitaService categoriaReceitaService;
	
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
	public PacienteController() {
	}
	
	/**
	 * Retorna a listagem de todos os pacientes de um profissional.
	 *
	 * @param profissionalId
	 * @return ResponseEntity<Response<PacienteDto>>
	 */
	@GetMapping(value = "/profissional/{profissionalId}/todos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<List<PacienteDto>>> listarTodosPorProfissionalId(
			@PathVariable("profissionalId") Long profissionalId) {
		log.info("Buscando todos os pacientes por ID do profissional: {}", profissionalId);
		Response<List<PacienteDto>> response = new Response<List<PacienteDto>>();

		Usuario u = usuarioService.buscarPorId(profissionalId).get();
		
		List<Paciente> pacientes = this.pacienteService.buscarPorProfissionalId(u.getProfissional().getId());
		List<PacienteDto> pacientesDto = pacientes.stream()
				.map(paciente -> this.converterPacienteDto(paciente, u))
				.collect(Collectors.toList());

		response.setData(pacientesDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna os dados complementares de um paciente.
	 * 
	 * @param pacienteId
	 * @return ResponseEntity<Response<PacidenteDto>>
	 */
	@GetMapping(value = "/{pacienteId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<PacienteDto>> buscarDadosPorPacienteId(
			@PathVariable("pacienteId") Long pacienteId) {
		log.info("Buscando dados do paciente por ID do paciente: {}", pacienteId);
		Response<PacienteDto> response = new Response<PacienteDto>();

		Optional<Paciente> paciente = this.pacienteService.buscarPorId(pacienteId);
		Optional<Usuario> usuario = this.usuarioService.buscarPorId(paciente.get().getUsuario().getId());
		PacienteDto pacienteDto = this.converterPacienteDto(paciente.get(), usuario.get());

		response.setData(pacienteDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna os dados complementares de um usuario paciente.
	 * 
	 * @param usuarioId
	 * @return ResponseEntity<Response<PacidenteDto>>
	 */
	@GetMapping(value = "/usuario/{usuarioId}")
	@PreAuthorize("hasAnyRole('USUARIO')")
	public ResponseEntity<Response<PacienteDto>> buscarDadosPorUsuarioId(
			@PathVariable("usuarioId") Long usuarioId) {
		log.info("Buscando dados do paciente por ID do usuario: {}", usuarioId);
		Response<PacienteDto> response = new Response<PacienteDto>();

		Optional<Paciente> paciente = this.pacienteService.buscarPorUsuarioId(usuarioId);
		Optional<Usuario> usuario = this.usuarioService.buscarPorId(usuarioId);
		PacienteDto pacienteDto = this.converterPacienteDto(paciente.get(), usuario.get());

		response.setData(pacienteDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Atualiza os dados de um paciente.
	 * 
	 * @param pacienteId
	 * @param pacienteDto
	 * @return ResponseEntity<Response<PacienteDto>>
	 * @throws ParseException 
	 */
	@PatchMapping(value = "/{pacienteId}")
	public ResponseEntity<Response<PacienteDto>> atualizar(@PathVariable("pacienteId") Long pacienteId,
			@Valid @RequestBody PacienteDto pacienteDto, BindingResult result) throws ParseException {
		log.info("Atualizando paciente: {}", pacienteDto.toString());
		Response<PacienteDto> response = new Response<PacienteDto>();
		validarPaciente(pacienteDto, result);
		pacienteDto.setId(Optional.of(pacienteId).get());
		Usuario usuario = this.converterDtoParaUsuario(pacienteDto, result);
		Paciente paciente = this.converterDtoParaPaciente(pacienteDto, result);
		
		if (result.hasErrors()) {
			log.error("Erro validando paciente: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		paciente = this.pacienteService.persistir(paciente);
		usuario = this.usuarioService.persistir(usuario);
		response.setData(this.converterPacienteDto(paciente, usuario));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Valida um profissional, verificando se ele é existente e válido no
	 * sistema.
	 * 
	 * @param profissionalDto
	 * @param result
	 */
	private void validarPaciente(PacienteDto pacienteDto, BindingResult result) {
		if (pacienteDto.getId() == null) {
			result.addError(new ObjectError("paciente", "Paciente não informado."));
			return;
		}

		log.info("Validando paciente id {}: ", pacienteDto.getId());
		Optional<Paciente> paciente = this.pacienteService.buscarPorId(pacienteDto.getId());
		if (!paciente.isPresent()) {
			result.addError(new ObjectError("paciente", "Paciente não encontrado. ID inexistente."));
		}
	}
	
	 /* Converte uma entidade paciente para seu respectivo DTO.
	 * 
	 * @param paciente
	 * @return PacienteDto
	 */
	private PacienteDto converterPacienteDto(Paciente paciente, Usuario usuario) {
		PacienteDto pacienteDto = new PacienteDto();
		pacienteDto.setId(paciente.getId());
		pacienteDto.setUsuarioId(usuario.getId());
		pacienteDto.setEmail(paciente.getUsuario().getEmail());
		pacienteDto.setTelefone(paciente.getUsuario().getTelefone());
		pacienteDto.setNome(paciente.getUsuario().getNome());
		pacienteDto.setCpf(paciente.getUsuario().getCpf());
		pacienteDto.setObservacao(paciente.getObservacao());
		if (paciente.getCategoriaReceita() != null) {
			pacienteDto.setCategoriaReceitaId(paciente.getCategoriaReceita().getId());
		}

		return pacienteDto;
	}

	/**
	 * Converte um PacienteDto para uma entidade Usuario.
	 * 
	 * @param pacienteDto
	 * @param result
	 * @return Usuario
	 * @throws ParseException 
	 */
	private Usuario converterDtoParaUsuario(PacienteDto pacienteDto, BindingResult result) throws ParseException {
		Paciente paciente = pacienteService.buscarPorId(pacienteDto.getId()).get();
		Usuario usuario = usuarioService.buscarPorId(paciente.getUsuario().getId()).get();
		
		usuario.setNome(pacienteDto.getNome());
		usuario.setEmail(pacienteDto.getEmail());
		usuario.setTelefone(pacienteDto.getTelefone());
		
		if (pacienteDto.getSenha() != null || pacienteDto.getSenha() != "") {
			usuario.setSenha(PasswordUtils.gerarBCrypt(pacienteDto.getSenha()));
		}

		return usuario;
	}
	
	/**
	 * Converte um PacienteDto para uma entidade Paciente.
	 * 
	 * @param pacienteDto
	 * @param result
	 * @return Paciente
	 * @throws ParseException 
	 */
	private Paciente converterDtoParaPaciente(PacienteDto pacienteDto, BindingResult result) throws ParseException {
		Paciente paciente = pacienteService.buscarPorId(pacienteDto.getId()).get();
		if (pacienteDto.getCategoriaReceitaId() != null) {
			CategoriaReceita categoriaReceita = categoriaReceitaService.buscarPorId(pacienteDto.getCategoriaReceitaId()).get();
			paciente.setCategoriaReceita(categoriaReceita);
		}
		paciente.setObservacao(pacienteDto.getObservacao());

		return paciente;
	}

}
