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

import br.com.projeto.projetotcc.api.dtos.PlanoDto;
import br.com.projeto.projetotcc.api.entities.CategoriaPlanoAlimentar;
import br.com.projeto.projetotcc.api.entities.Paciente;
import br.com.projeto.projetotcc.api.entities.Plano;
import br.com.projeto.projetotcc.api.entities.Profissional;
import br.com.projeto.projetotcc.api.entities.Usuario;
import br.com.projeto.projetotcc.api.response.Response;
import br.com.projeto.projetotcc.api.services.CategoriaPlanoAlimentarService;
import br.com.projeto.projetotcc.api.services.PacienteService;
import br.com.projeto.projetotcc.api.services.PlanoService;
import br.com.projeto.projetotcc.api.services.ProfissionalService;
import br.com.projeto.projetotcc.api.services.UsuarioService;

@RestController
@RequestMapping("/api/plano-alimentar")
@CrossOrigin(origins = "*")
public class PlanoAlimentarController {
	
	private static final Logger log = LoggerFactory.getLogger(CadastroUsuarioController.class);
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ProfissionalService profissionalService;
	
	@Autowired
	private PacienteService pacienteService;
	
	@Autowired
	private PlanoService planoService;
	
	@Autowired
	private CategoriaPlanoAlimentarService categoriaPlanoAlimentarService;
	
	/**
	 * Adiciona um novo Plano Alimentar associado a um paciente.
	 * 
	 * @param PlanoDto
	 * @param result
	 * @return ResponseEntity<Response<PlanoDto>>
	 * @throws ParseException 
	 */
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<PlanoDto>> adicionar(@Valid @RequestBody PlanoDto planoDto,
			BindingResult result) throws ParseException {
		log.info("Adicionando plano alimentar: {}", planoDto.toString());
		Response<PlanoDto> response = new Response<PlanoDto>();
		validarUsuario(planoDto, result);
		Plano plano = this.converterDtoParaPlano(planoDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando plano alimentar: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		plano = this.planoService.persistir(plano);
		response.setData(this.converterPlanoDto(plano));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna os planos de um paciente;
	 * 
	 * @param pacienteId
	 * @return ResponseEntity<Response<PlanoDto>>
	 */
	@GetMapping(value = "/paciente/{pacienteId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<List<PlanoDto>>> listarPorPacienteId(
			@PathVariable("pacienteId") Long pacienteId) {
		log.info("Buscando todos os planos por ID do paciente: {}", pacienteId);
		Response<List<PlanoDto>> response = new Response<List<PlanoDto>>();

		Paciente paciente = pacienteService.buscarPorId(pacienteId).get();
		
		List<Plano> planos = this.planoService.buscarPorPacienteId(paciente.getId());
		List<PlanoDto> planosDto = planos.stream()
				.map(plano -> this.converterPlanoDto(plano))
				.collect(Collectors.toList());

		response.setData(planosDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna os planos de um usuario;
	 * 
	 * @param usuarioId
	 * @return ResponseEntity<Response<PlanoDto>>
	 */
	@GetMapping(value = "/usuario/{usuarioId}")
	@PreAuthorize("hasAnyRole('USUARIO')")
	public ResponseEntity<Response<List<PlanoDto>>> listarPorUsuarioId(
			@PathVariable("usuarioId") Long usuarioId) {
		log.info("Buscando todos os planos por ID do usuario: {}", usuarioId);
		Response<List<PlanoDto>> response = new Response<List<PlanoDto>>();

		Paciente paciente = pacienteService.buscarPorUsuarioId(usuarioId).get();
		
		List<Plano> planos = this.planoService.buscarPorPacienteId(paciente.getId());
		List<PlanoDto> planosDto = planos.stream()
				.map(plano -> this.converterPlanoDto(plano))
				.collect(Collectors.toList());

		response.setData(planosDto);
		return ResponseEntity.ok(response);
	}	
	
	/**
	 * Retorna um plano alimentar por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<PlanoDto>>
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<PlanoDto>> listarPorId(@PathVariable("id") Long id) {
		log.info("Buscando plano alimentar por ID: {}", id);
		Response<PlanoDto> response = new Response<PlanoDto>();
		Optional<Plano> plano = this.planoService.buscarPorId(id);

		if (!plano.isPresent()) {
			log.info("Plano alimentar não encontrado para o ID: {}", id);
			response.getErrors().add("Plano alimentar não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterPlanoDto(plano.get()));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Atualiza os dados de um plano alimentar.
	 * 
	 * @param id
	 * @param planoDto
	 * @return ResponseEntity<Response<Plano>>
	 * @throws ParseException 
	 */
	@PutMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<PlanoDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody PlanoDto planoDto, BindingResult result) throws ParseException {
		log.info("Atualizando plano alimentar: {}", planoDto.toString());
		Response<PlanoDto> response = new Response<PlanoDto>();
		validarUsuario(planoDto, result);
		planoDto.setId(Optional.of(id));
		Plano plano = this.converterDtoParaPlano(planoDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando plano alimentar: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		plano = this.planoService.persistir(plano);
		response.setData(this.converterPlanoDto(plano));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Remove um plano alimentar por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<Plano>>
	 */
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo plano alimentar: {}", id);
		Response<String> response = new Response<String>();
		Optional<Plano> plano = this.planoService.buscarPorId(id);

		if (!plano.isPresent()) {
			log.info("Erro ao remover devido a plano ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover plano. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.planoService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}
		
	
	/**
	 * Valida um usuário, verificando se ele é existente e válido no
	 * sistema.
	 * 
	 * @param planoDto
	 * @param result
	 */
	private void validarUsuario(PlanoDto planoDto, BindingResult result) {
		if (planoDto.getProfissionalId() == null || planoDto.getPacienteId() == null) {
			result.addError(new ObjectError("usuario", "Profissional ou Paciente não informado."));
			return;
		}

		log.info("Validando usuário id {}: ", planoDto.getProfissionalId());
		Optional<Usuario> profissional = this.usuarioService.buscarPorId(planoDto.getProfissionalId());
		if (!profissional.isPresent()) {
			result.addError(new ObjectError("usuario", "Profissional não encontrado. ID inexistente."));
		}
		Optional<Usuario> paciente = this.usuarioService.buscarPorId(planoDto.getPacienteId());
		if (!paciente.isPresent()) {
			result.addError(new ObjectError("usuario", "Paciente não encontrado. ID inexistente."));
		}
	}
	
	/**
	 * Converte uma entidade plano para seu respectivo DTO.
	 * 
	 * @param plano
	 * @return planoDto
	 */
	private PlanoDto converterPlanoDto(Plano plano) {
		PlanoDto planoDto = new PlanoDto();
		planoDto.setId(Optional.of(plano.getId()));
		planoDto.setHora(plano.getHora());
		planoDto.setDescricao(plano.getDescricao());
		planoDto.setCategoriaPlanoAlimentarId(plano.getCategoriaPlanoAlimentar().getId());
		planoDto.setDescricaoCategoria(plano.getCategoriaPlanoAlimentar().getDescricao());
		planoDto.setObservacao(plano.getObservacao());
		planoDto.setProfissionalId(plano.getProfissional().getId());
		planoDto.setPacienteId(plano.getPaciente().getId());
		planoDto.setUsuarioPacienteId(plano.getPaciente().getUsuario().getId());

		return planoDto;
	}
	
	/**
	 * Converte um PlanoDto para uma entidade Plano.
	 * 
	 * @param planoDto
	 * @param result
	 * @return Plano
	 * @throws ParseException 
	 */
	private Plano converterDtoParaPlano(PlanoDto planoDto, BindingResult result) throws ParseException {
		Plano plano = new Plano();

		if (planoDto.getId().isPresent()) {
			Optional<Plano> pl = this.planoService.buscarPorId(planoDto.getId().get());
			if (pl.isPresent()) {
				plano = pl.get();
			} else {
				result.addError(new ObjectError("plano", "Plano alimentar não encontrado."));
			}
		} else {
			plano.setProfissional(new Profissional());
			Long profissionalId = profissionalService.buscarPorUsuarioId(planoDto.getProfissionalId()).get().getId();
			plano.getProfissional().setId(profissionalId);
			
			plano.setPaciente(new Paciente());
			Long pacienteId = pacienteService.buscarPorId(planoDto.getPacienteId()).get().getId();
			plano.getPaciente().setId(pacienteId);
		}
		
		CategoriaPlanoAlimentar categoria = categoriaPlanoAlimentarService.buscarPorId(planoDto.getCategoriaPlanoAlimentarId()).get();

		plano.setHora(planoDto.getHora());
		plano.setCategoriaPlanoAlimentar(categoria);
		plano.setDescricao(planoDto.getDescricao());
		plano.setObservacao(planoDto.getObservacao());

		return plano;
	}

}
