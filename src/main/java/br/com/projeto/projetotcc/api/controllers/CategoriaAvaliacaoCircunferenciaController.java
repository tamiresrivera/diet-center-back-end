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

import br.com.projeto.projetotcc.api.dtos.CategoriaAvaliacaoCircunferenciaDto;
import br.com.projeto.projetotcc.api.entities.CategoriaAvaliacaoCircunferencia;
import br.com.projeto.projetotcc.api.entities.Profissional;
import br.com.projeto.projetotcc.api.response.Response;
import br.com.projeto.projetotcc.api.services.CategoriaAvaliacaoCircunferenciaService;
import br.com.projeto.projetotcc.api.services.ProfissionalService;

@RestController
@RequestMapping("/api/categoria-avaliacao-circunferencia")
@CrossOrigin(origins = "*")
public class CategoriaAvaliacaoCircunferenciaController {
	
	private static final Logger log = LoggerFactory.getLogger(CategoriaAvaliacaoCircunferenciaController.class);
	
	@Autowired
	private CategoriaAvaliacaoCircunferenciaService categoriaAvaliacaoCircunferenciaService;

	@Autowired
	private ProfissionalService profissionalService;
	
	public CategoriaAvaliacaoCircunferenciaController() {
	}
	
	/**
	 * Retorna as categorias de circunferência de um profissional;
	 * 
	 * @param profissionalId
	 * @return ResponseEntity<Response<CategoriaAvaliacaoCircunferenciaDto>>
	 */
	@GetMapping(value = "/profissional/{profissionalId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<List<CategoriaAvaliacaoCircunferenciaDto>>> listarPorProfissionalId(
			@PathVariable("profissionalId") Long profissionalId) {
		log.info("Buscando todas as categorias de circunferências por ID do profissional: {}", profissionalId);
		Response<List<CategoriaAvaliacaoCircunferenciaDto>> response = new Response<List<CategoriaAvaliacaoCircunferenciaDto>>();

		Profissional profissional = profissionalService.buscarPorUsuarioId(profissionalId).get();
		
		List<CategoriaAvaliacaoCircunferencia> categorias = this.categoriaAvaliacaoCircunferenciaService.buscarPorProfissionalId(profissional.getId());
		List<CategoriaAvaliacaoCircunferenciaDto> categoriasDto = categorias.stream()
				.map(categoria -> this.converterCategoriaDto(categoria))
				.collect(Collectors.toList());

		response.setData(categoriasDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna uma categoria de circunferência por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<CategoriaAvaliacaoCircunferenciaDto>>
	 */
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<CategoriaAvaliacaoCircunferenciaDto>> listarPorId(@PathVariable("id") Long id) {
		log.info("Buscando categoria por ID: {}", id);
		Response<CategoriaAvaliacaoCircunferenciaDto> response = new Response<CategoriaAvaliacaoCircunferenciaDto>();
		Optional<CategoriaAvaliacaoCircunferencia> categoria = this.categoriaAvaliacaoCircunferenciaService.buscarPorId(id);

		if (!categoria.isPresent()) {
			log.info("Categoria não encontrada para o ID: {}", id);
			response.getErrors().add("Categoria não encontrada para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterCategoriaDto(categoria.get()));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Adiciona uma nova categoria de circunferência.
	 * 
	 * @param categoriaAvaliacaoCircunferenciaDto
	 * @param result
	 * @return ResponseEntity<Response<CategoriaAvaliacaoCircunferencia>>
	 * @throws ParseException 
	 */
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<CategoriaAvaliacaoCircunferenciaDto>> adicionar(@Valid @RequestBody CategoriaAvaliacaoCircunferenciaDto categoriaAvaliacaoCircunferenciaDto,
			BindingResult result) throws ParseException {
		log.info("Adicionando categoria: {}", categoriaAvaliacaoCircunferenciaDto.toString());
		Response<CategoriaAvaliacaoCircunferenciaDto> response = new Response<CategoriaAvaliacaoCircunferenciaDto>();
		validarProfissional(categoriaAvaliacaoCircunferenciaDto, result);
		CategoriaAvaliacaoCircunferencia categoria = this.converterDtoParaCategoriaCircunferencia(categoriaAvaliacaoCircunferenciaDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando categoria: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		categoria = this.categoriaAvaliacaoCircunferenciaService.persistir(categoria);
		response.setData(this.converterCategoriaDto(categoria));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Atualiza os dados de uma categoria de circunferência.
	 * 
	 * @param id
	 * @param categoriaAvaliacaoCircunferenciaDto
	 * @return ResponseEntity<Response<CategoriaAvaliacaoCircunferencia>>
	 * @throws ParseException 
	 */
	@PutMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<CategoriaAvaliacaoCircunferenciaDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody CategoriaAvaliacaoCircunferenciaDto categoriaAvaliacaoCircunferenciaDto, BindingResult result) throws ParseException {
		log.info("Atualizando categoria: {}", categoriaAvaliacaoCircunferenciaDto.toString());
		Response<CategoriaAvaliacaoCircunferenciaDto> response = new Response<CategoriaAvaliacaoCircunferenciaDto>();
		validarProfissional(categoriaAvaliacaoCircunferenciaDto, result);
		categoriaAvaliacaoCircunferenciaDto.setId(Optional.of(id));
		CategoriaAvaliacaoCircunferencia categoria = this.converterDtoParaCategoriaCircunferencia(categoriaAvaliacaoCircunferenciaDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando categoria: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		categoria = this.categoriaAvaliacaoCircunferenciaService.persistir(categoria);
		response.setData(this.converterCategoriaDto(categoria));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Remove uma categoria de circunferência por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<CategoriaAvaliacaoCircunferencia>>
	 */
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo categoria: {}", id);
		Response<String> response = new Response<String>();
		Optional<CategoriaAvaliacaoCircunferencia> lancamento = this.categoriaAvaliacaoCircunferenciaService.buscarPorId(id);

		if (!lancamento.isPresent()) {
			log.info("Erro ao remover devido a categoria ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover categoria. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.categoriaAvaliacaoCircunferenciaService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	/**
	 * Valida um profissional, verificando se ele é existente e válido no
	 * sistema.
	 * 
	 * @param categoriaReceitaDto
	 * @param result
	 */
	private void validarProfissional(CategoriaAvaliacaoCircunferenciaDto categoriaAvaliacaoCircunferenciaDto, BindingResult result) {
		if (categoriaAvaliacaoCircunferenciaDto.getProfissionalId() == null) {
			result.addError(new ObjectError("profissional", "Profissional não informado."));
			return;
		}

		log.info("Validando profissional id {}: ", categoriaAvaliacaoCircunferenciaDto.getProfissionalId());
		Optional<Profissional> profissional = this.profissionalService.buscarPorUsuarioId(categoriaAvaliacaoCircunferenciaDto.getProfissionalId());
		if (!profissional.isPresent()) {
			result.addError(new ObjectError("profissional", "Profissional não encontrado. ID inexistente."));
		}
	}
	
	/**
	 * Converte uma entidade categoriaAvaliacaoCircunferencia para seu respectivo DTO.
	 * 
	 * @param categoriaAvaliacaoCircunferencia
	 * @return CategoriaAvaliacaoCircunferenciaDto
	 */
	private CategoriaAvaliacaoCircunferenciaDto converterCategoriaDto(CategoriaAvaliacaoCircunferencia categoriaCircunferencia) {
		
		CategoriaAvaliacaoCircunferenciaDto categoriaAvaliacaoCircunferenciaDto = new CategoriaAvaliacaoCircunferenciaDto();
		categoriaAvaliacaoCircunferenciaDto.setId(Optional.of(categoriaCircunferencia.getId()));
		categoriaAvaliacaoCircunferenciaDto.setDescricao(categoriaCircunferencia.getDescricao());
		
		Long profissionalId = profissionalService.buscarPorUsuarioId(categoriaCircunferencia.getProfissional().getId()).get().getId();
		categoriaAvaliacaoCircunferenciaDto.setProfissionalId(profissionalId);

		return categoriaAvaliacaoCircunferenciaDto;
	}
	
	/**
	 * Converte uma CategoriaAvaliacaoCircunferenciaDto para uma entidade CategoriaAvaliacaoCircunferencia.
	 * 
	 * @param categoriaAvaliacaoCircunferenciaDto
	 * @param result
	 * @return CategoriaAvaliacaoCircunferencia
	 * @throws ParseException 
	 */
	private CategoriaAvaliacaoCircunferencia converterDtoParaCategoriaCircunferencia(CategoriaAvaliacaoCircunferenciaDto categoriaAvaliacaoCircunferenciaDto, BindingResult result) throws ParseException {
		CategoriaAvaliacaoCircunferencia categoria = new CategoriaAvaliacaoCircunferencia();

		if (categoriaAvaliacaoCircunferenciaDto.getId().isPresent()) {
			Optional<CategoriaAvaliacaoCircunferencia> cat = this.categoriaAvaliacaoCircunferenciaService.buscarPorId(categoriaAvaliacaoCircunferenciaDto.getId().get());
			if (cat.isPresent()) {
				categoria = cat.get();
			} else {
				result.addError(new ObjectError("categoria", "Categoria não encontrada."));
			}
		} else {
			Profissional profissional = profissionalService.buscarPorUsuarioId(categoriaAvaliacaoCircunferenciaDto.getProfissionalId()).get();
			categoria.setProfissional(profissional);
		}

		categoria.setDescricao(categoriaAvaliacaoCircunferenciaDto.getDescricao());

		return categoria;
	}

}
