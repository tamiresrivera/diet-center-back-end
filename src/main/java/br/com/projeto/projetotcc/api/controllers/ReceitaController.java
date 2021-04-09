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

import br.com.projeto.projetotcc.api.services.ReceitaService;
import br.com.projeto.projetotcc.api.dtos.ReceitaDto;
import br.com.projeto.projetotcc.api.entities.CategoriaReceita;
import br.com.projeto.projetotcc.api.entities.Paciente;
import br.com.projeto.projetotcc.api.entities.Profissional;
import br.com.projeto.projetotcc.api.entities.Receita;
import br.com.projeto.projetotcc.api.response.Response;
import br.com.projeto.projetotcc.api.services.CategoriaReceitaService;
import br.com.projeto.projetotcc.api.services.PacienteService;
import br.com.projeto.projetotcc.api.services.ProfissionalService;

@RestController
@RequestMapping("/api/receita")
@CrossOrigin(origins = "*")
public class ReceitaController {
	
private static final Logger log = LoggerFactory.getLogger(ReceitaController.class);
	
	@Autowired
	private ReceitaService receitaService;

	@Autowired
	private ProfissionalService profissionalService;
	
	@Autowired
	private PacienteService pacienteService;
	
	@Autowired
	private CategoriaReceitaService categoriaReceitaService;
	
	public ReceitaController() {
	}
	
	/**
	 * Retorna as receitas de um profissional;
	 * 
	 * @param profissionalId
	 * @return ResponseEntity<Response<ReceitaDto>>
	 */
	@GetMapping(value = "/profissional/{profissionalId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<List<ReceitaDto>>> listarPorProfissionalId(
			@PathVariable("profissionalId") Long profissionalId) {
		log.info("Buscando todas as receitas por ID do profissional: {}", profissionalId);
		Response<List<ReceitaDto>> response = new Response<List<ReceitaDto>>();

		Profissional profissional = profissionalService.buscarPorUsuarioId(profissionalId).get();
		
		List<Receita> receitas = this.receitaService.buscarPorProfissionalId(profissional.getId());
		List<ReceitaDto> receitasDto = receitas.stream()
				.map(receita -> this.converterReceitaDto(receita))
				.collect(Collectors.toList());

		response.setData(receitasDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna as receitas de um paciente;
	 * 
	 * @param usuarioId
	 * @return ResponseEntity<Response<ReceitaDto>>
	 */
	@GetMapping(value = "/paciente/{usuarioId}")
	@PreAuthorize("hasAnyRole('USUARIO')")
	public ResponseEntity<Response<List<ReceitaDto>>> listarPorUsuarioId(
			@PathVariable("usuarioId") Long usuarioId) {
		log.info("Buscando todas as receitas por ID do paciente: {}", usuarioId);
		Response<List<ReceitaDto>> response = new Response<List<ReceitaDto>>();

		Paciente paciente = pacienteService.buscarPorUsuarioId(usuarioId).get();
		
		List<Receita> receitas = this.receitaService.buscarPorCategoriaReceitaId(paciente.getCategoriaReceita().getId());
		List<ReceitaDto> receitasDto = receitas.stream()
				.map(receita -> this.converterReceitaDto(receita))
				.collect(Collectors.toList());

		response.setData(receitasDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna uma receita por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<ReceitaDto>>
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<ReceitaDto>> listarPorId(@PathVariable("id") Long id) {
		log.info("Buscando receita por ID: {}", id);
		Response<ReceitaDto> response = new Response<ReceitaDto>();
		Optional<Receita> receita = this.receitaService.buscarPorId(id);

		if (!receita.isPresent()) {
			log.info("Receita não encontrada para o ID: {}", id);
			response.getErrors().add("Receita não encontrada para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterReceitaDto(receita.get()));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Adiciona uma nova receita.
	 * 
	 * @param receitaDto
	 * @param result
	 * @return ResponseEntity<Response<ReceitaDto>>
	 * @throws ParseException 
	 */
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<ReceitaDto>> adicionar(@Valid @RequestBody ReceitaDto receitaDto,
			BindingResult result) throws ParseException {
		log.info("Adicionando receita: {}", receitaDto.toString());
		Response<ReceitaDto> response = new Response<ReceitaDto>();
		validarProfissional(receitaDto, result);
		Receita receita = this.converterDtoParaReceita(receitaDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando receita: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		receita = this.receitaService.persistir(receita);
		response.setData(this.converterReceitaDto(receita));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Atualiza os dados de uma receita.
	 * 
	 * @param id
	 * @param receitaDto
	 * @return ResponseEntity<Response<Receita>>
	 * @throws ParseException 
	 */
	@PutMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<ReceitaDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody ReceitaDto receitaDto, BindingResult result) throws ParseException {
		log.info("Atualizando receita: {}", receitaDto.toString());
		Response<ReceitaDto> response = new Response<ReceitaDto>();
		validarProfissional(receitaDto, result);
		receitaDto.setId(Optional.of(id));
		Receita receita = this.converterDtoParaReceita(receitaDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando receita: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		receita = this.receitaService.persistir(receita);
		response.setData(this.converterReceitaDto(receita));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Remove uma receita por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<Receita>>
	 */
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo receita: {}", id);
		Response<String> response = new Response<String>();
		Optional<Receita> receita = this.receitaService.buscarPorId(id);

		if (!receita.isPresent()) {
			log.info("Erro ao remover devido a receita ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover receita. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.receitaService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	/**
	 * Valida um profissional, verificando se ele é existente e válido no
	 * sistema.
	 * 
	 * @param receitaDto
	 * @param result
	 */
	private void validarProfissional(ReceitaDto receitaDto, BindingResult result) {
		if (receitaDto.getProfissionalId() == null) {
			result.addError(new ObjectError("profissional", "Profissional não informado."));
			return;
		}

		log.info("Validando profissional id {}: ", receitaDto.getProfissionalId());
		Optional<Profissional> profissional = this.profissionalService.buscarPorUsuarioId(receitaDto.getProfissionalId());
		if (!profissional.isPresent()) {
			result.addError(new ObjectError("profissional", "Profissional não encontrado. ID inexistente."));
		}
	}
	
	/**
	 * Converte uma entidade Receita para seu respectivo DTO.
	 * 
	 * @param receita
	 * @return ReceitaDto
	 */
	private ReceitaDto converterReceitaDto(Receita receita) {
		
		ReceitaDto receitaDto = new ReceitaDto();
		receitaDto.setId(Optional.of(receita.getId()));
		receitaDto.setTitulo(receita.getTitulo());
		receitaDto.setDescricao(receita.getDescricao());
		receitaDto.setCategoriaReceitaId(receita.getCategoriaReceita().getId());
		receitaDto.setCategoriaReceitaDescricao(receita.getCategoriaReceita().getDescricao());
		
		Long profissionalId = profissionalService.buscarPorUsuarioId(receita.getProfissional().getId()).get().getId();
		receitaDto.setProfissionalId(profissionalId);

		return receitaDto;
	}
	
	/**
	 * Converte uma ReceitaDto para uma entidade Receita.
	 * 
	 * @param receitaDto
	 * @param result
	 * @return Receita
	 * @throws ParseException 
	 */
	private Receita converterDtoParaReceita(ReceitaDto receitaDto, BindingResult result) throws ParseException {
		Receita receita = new Receita();

		if (receitaDto.getId().isPresent()) {
			Optional<Receita> rec = this.receitaService.buscarPorId(receitaDto.getId().get());
			if (rec.isPresent()) {
				receita = rec.get();
			} else {
				result.addError(new ObjectError("categoria", "Receita não encontrada."));
			}
		} else {
			Profissional profissional = profissionalService.buscarPorUsuarioId(receitaDto.getProfissionalId()).get();
			receita.setProfissional(profissional);
		}

		receita.setTitulo(receitaDto.getTitulo());
		receita.setDescricao(receitaDto.getDescricao());
		
		CategoriaReceita categoria = categoriaReceitaService.buscarPorId(receitaDto.getCategoriaReceitaId()).get();
		receita.setCategoriaReceita(categoria);

		return receita;
	}

}
