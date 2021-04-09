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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.projetotcc.api.dtos.ProfissionalDto;
import br.com.projeto.projetotcc.api.entities.Profissional;
import br.com.projeto.projetotcc.api.entities.Paciente;
import br.com.projeto.projetotcc.api.entities.Usuario;
import br.com.projeto.projetotcc.api.response.Response;
import br.com.projeto.projetotcc.api.services.PacienteService;
import br.com.projeto.projetotcc.api.services.ProfissionalService;
import br.com.projeto.projetotcc.api.services.UsuarioService;
import br.com.projeto.projetotcc.api.utils.PasswordUtils;

@RestController
@RequestMapping("/api/profissional")
@CrossOrigin(origins = "*")
public class ProfissionalController {
	
	private static final Logger log = LoggerFactory.getLogger(CadastroUsuarioController.class);
	
	@Autowired
	private ProfissionalService profissionalService;
	
	@Autowired PacienteService pacienteService;
	
	@Autowired
	UsuarioService usuarioService;
	
	/**
	 * Retorna a listagem de todos os profissionais do sistema.
	 *
	 * @return ResponseEntity<Response<ProfissionalDto>>
	 */
	@GetMapping()
	@PreAuthorize("hasAnyRole('USUARIO')")
	public ResponseEntity<Response<List<ProfissionalDto>>> listarTodos() {
		log.info("Buscando todos os profissionais cadastrados no sistema");
		Response<List<ProfissionalDto>> response = new Response<List<ProfissionalDto>>();
	
		List<Profissional> profissionais = this.profissionalService.buscarTodos();
		List<ProfissionalDto> profissionaisDto = profissionais.stream()
				.map(profissional -> this.converterProfissionalDto(profissional, usuarioService.buscarPorId(profissional.getUsuario().getId()).get()))
				.collect(Collectors.toList());

		response.setData(profissionaisDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna os dados complementares de um profissional.
	 * 
	 * @param usuarioId
	 * @return ResponseEntity<Response<ProfissionalDto>>
	 */
	@GetMapping(value = "/{usuarioId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<ProfissionalDto>> buscarDadosPorUsuarioId(
			@PathVariable("usuarioId") Long usuarioId) {
		log.info("Buscando dados do profissional por ID do usuario: {}", usuarioId);
		Response<ProfissionalDto> response = new Response<ProfissionalDto>();

		Optional<Profissional> profissional = this.profissionalService.buscarPorUsuarioId(usuarioId);
		Optional<Usuario> usuario = this.usuarioService.buscarPorId(usuarioId);
		ProfissionalDto profissionalDto = this.converterProfissionalDto(profissional.get(), usuario.get());

		response.setData(profissionalDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna os dados complementares de um profissional.
	 * 
	 * @param profissionalId
	 * @return ResponseEntity<Response<ProfissionalDto>>
	 */
	@GetMapping(value = "/profissional/{profissionalId}")
	@PreAuthorize("hasAnyRole('USUARIO')")
	public ResponseEntity<Response<ProfissionalDto>> buscarDadosPorProfissionalId(
			@PathVariable("profissionalId") Long profissionalId) {
		log.info("Buscando dados do profissional por ID do profissional: {}", profissionalId);
		Response<ProfissionalDto> response = new Response<ProfissionalDto>();

		Optional<Profissional> profissional = this.profissionalService.buscarPorId(profissionalId);
		Optional<Usuario> usuario = this.usuarioService.buscarPorId(profissional.get().getUsuario().getId());
		ProfissionalDto profissionalDto = this.converterProfissionalDto(profissional.get(), usuario.get());

		response.setData(profissionalDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna os dados complementares de um profissional por paciente.
	 * 
	 * @param pacienteId
	 * @return ResponseEntity<Response<ProfissionalDto>>
	 */
	@GetMapping(value = "/paciente/{pacienteId}")
	@PreAuthorize("hasAnyRole('USUARIO')")
	public ResponseEntity<Response<ProfissionalDto>> buscarDadosPorPacienteId(
			@PathVariable("pacienteId") Long pacienteId) {
		log.info("Buscando dados do profissional por ID do paciente: {}", pacienteId);
		Response<ProfissionalDto> response = new Response<ProfissionalDto>();

		Paciente paciente = this.pacienteService.buscarPorUsuarioId(pacienteId).get();
		
		Optional<Profissional> profissional = this.profissionalService.buscarPorId(paciente.getProfissional().getId());
		Optional<Usuario> usuario = this.usuarioService.buscarPorId(profissional.get().getUsuario().getId());
		ProfissionalDto profissionalDto = this.converterProfissionalDto(profissional.get(), usuario.get());

		response.setData(profissionalDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Atualiza os dados de um profissional.
	 * 
	 * @param usuarioId
	 * @param profissionalDto
	 * @return ResponseEntity<Response<ProfissionalDto>>
	 * @throws ParseException 
	 */
	@PatchMapping(value = "/{usuarioId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<ProfissionalDto>> atualizar(@PathVariable("usuarioId") Long usuarioId,
			@Valid @RequestBody ProfissionalDto profissionalDto, BindingResult result) throws ParseException {
		log.info("Atualizando profissional: {}", profissionalDto.toString());
		Response<ProfissionalDto> response = new Response<ProfissionalDto>();
		validarProfissional(profissionalDto, result);
		profissionalDto.setUsuarioId(Optional.of(usuarioId).get());
		Usuario usuario = this.converterDtoParaUsuario(profissionalDto, result);
		Profissional profissional = this.converterDtoParaProfissional(profissionalDto, result);
		
		if (result.hasErrors()) {
			log.error("Erro validando profissional: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		profissional = this.profissionalService.persistir(profissional);
		usuario = this.usuarioService.persistir(usuario);
		response.setData(this.converterProfissionalDto(profissional, usuario));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Valida um profissional, verificando se ele é existente e válido no
	 * sistema.
	 * 
	 * @param profissionalDto
	 * @param result
	 */
	private void validarProfissional(ProfissionalDto profissionalDto, BindingResult result) {
		if (profissionalDto.getUsuarioId() == null || profissionalDto.getProfissionalId() == null) {
			result.addError(new ObjectError("profissional", "Profissional não informado."));
			return;
		}

		log.info("Validando profissional id {}: ", profissionalDto.getProfissionalId());
		Optional<Profissional> profissional = this.profissionalService.buscarPorId(profissionalDto.getProfissionalId());
		if (!profissional.isPresent()) {
			result.addError(new ObjectError("profissional", "Profissional não encontrado. ID inexistente."));
		}
		log.info("Validando usuario id {}: ", profissionalDto.getProfissionalId());
		Optional<Usuario> usuario = this.usuarioService.buscarPorId(profissionalDto.getUsuarioId());
		if (!usuario.isPresent()) {
			result.addError(new ObjectError("usuario", "Usuário não encontrado. ID inexistente."));
		}
	}
	
	/**
	 * Converte um ProfissionalDto para uma entidade Profissional.
	 * 
	 * @param profissionalDto
	 * @param result
	 * @return Profissional
	 * @throws ParseException 
	 */
	private Profissional converterDtoParaProfissional(ProfissionalDto profissionalDto, BindingResult result) throws ParseException {
		Profissional profissional = profissionalService.buscarPorId(profissionalDto.getProfissionalId()).get();

		profissional.setDescricao(profissionalDto.getDescricao());
		profissional.setCrn(profissionalDto.getCrn());
		profissional.setEndereco(profissionalDto.getEndereco());
		profissional.setNumero(profissionalDto.getNumero());
		profissional.setCep(profissionalDto.getCep());
		profissional.setComplemento(profissionalDto.getComplemento());
		profissional.setCidade(profissionalDto.getCidade());
		profissional.setEstado(profissionalDto.getEstado());
		profissional.setMidiaSocial(profissionalDto.getMidiaSocial());

		return profissional;
	}
	
	/**
	 * Converte um ProfissionalDto para uma entidade Usuario.
	 * 
	 * @param profissionalDto
	 * @param result
	 * @return Usuario
	 * @throws ParseException 
	 */
	private Usuario converterDtoParaUsuario(ProfissionalDto profissionalDto, BindingResult result) throws ParseException {
		Usuario usuario = usuarioService.buscarPorId(profissionalDto.getUsuarioId()).get();

		usuario.setId(profissionalDto.getUsuarioId());
		usuario.setNome(profissionalDto.getNome());
		usuario.setEmail(profissionalDto.getEmail());
		usuario.setCpf(profissionalDto.getCpf());
		usuario.setTelefone(profissionalDto.getTelefone());
		
		if (profissionalDto.getSenha() != null || profissionalDto.getSenha() != "") {
			usuario.setSenha(PasswordUtils.gerarBCrypt(profissionalDto.getSenha()));
		}

		return usuario;
		
	}

	
	 /* Converte uma entidade profissional para seu respectivo DTO.
	 * 
	 * @param profissional
	 * @return ProfissionalDto
	 */
	private ProfissionalDto converterProfissionalDto(Profissional profissional, Usuario usuario) {
		ProfissionalDto profissionalDto = new ProfissionalDto();
		profissionalDto.setProfissionalId(profissional.getId());
		profissionalDto.setUsuarioId(usuario.getId());
		profissionalDto.setDescricao(profissional.getDescricao());
		profissionalDto.setNome(usuario.getNome());
		profissionalDto.setEmail(usuario.getEmail());
		profissionalDto.setTelefone(usuario.getTelefone());
		profissionalDto.setCpf(usuario.getCpf());
		profissionalDto.setNome(usuario.getNome());
		profissionalDto.setCrn(profissional.getCrn());
		profissionalDto.setEndereco(profissional.getEndereco());
		profissionalDto.setNumero(profissional.getNumero());
		profissionalDto.setCep(profissional.getCep());
		profissionalDto.setComplemento(profissional.getComplemento());
		profissionalDto.setCidade(profissional.getCidade());
		profissionalDto.setEstado(profissional.getEstado());
		profissionalDto.setMidiaSocial(profissional.getMidiaSocial());

		return profissionalDto;
	}

}
