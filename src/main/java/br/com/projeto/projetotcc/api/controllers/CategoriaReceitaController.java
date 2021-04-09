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

import br.com.projeto.projetotcc.api.dtos.CategoriaReceitaDto;
import br.com.projeto.projetotcc.api.entities.CategoriaReceita;
import br.com.projeto.projetotcc.api.entities.Profissional;
import br.com.projeto.projetotcc.api.response.Response;
import br.com.projeto.projetotcc.api.services.CategoriaReceitaService;
import br.com.projeto.projetotcc.api.services.ProfissionalService;

@RestController
@RequestMapping("/api/categoria-receita")
@CrossOrigin(origins = "*")
public class CategoriaReceitaController {
	
	private static final Logger log = LoggerFactory.getLogger(CategoriaReceitaController.class);
	
	@Autowired
	private CategoriaReceitaService categoriaReceitaService;

	@Autowired
	private ProfissionalService profissionalService;
	
	public CategoriaReceitaController() {
	}
	
	/**
	 * Retorna as categorias de receitas de um profissional;
	 * 
	 * @param profissionalId
	 * @return ResponseEntity<Response<CategoriaReceitaDto>>
	 */
	@GetMapping(value = "/profissional/{profissionalId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<List<CategoriaReceitaDto>>> listarPorProfissionalId(
			@PathVariable("profissionalId") Long profissionalId) {
		log.info("Buscando todas as categorias de receitas por ID do profissional: {}", profissionalId);
		Response<List<CategoriaReceitaDto>> response = new Response<List<CategoriaReceitaDto>>();

		Profissional profissional = profissionalService.buscarPorUsuarioId(profissionalId).get();
		
		List<CategoriaReceita> categorias = this.categoriaReceitaService.buscarPorProfissionalId(profissional.getId());
		List<CategoriaReceitaDto> categoriasDto = categorias.stream()
				.map(categoria -> this.converterCategoriaDto(categoria))
				.collect(Collectors.toList());

		response.setData(categoriasDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna uma categoria de receita por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<CategoriaReceitaDto>>
	 */
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<CategoriaReceitaDto>> listarPorId(@PathVariable("id") Long id) {
		log.info("Buscando categoria por ID: {}", id);
		Response<CategoriaReceitaDto> response = new Response<CategoriaReceitaDto>();
		Optional<CategoriaReceita> categoria = this.categoriaReceitaService.buscarPorId(id);

		if (!categoria.isPresent()) {
			log.info("Categoria não encontrada para o ID: {}", id);
			response.getErrors().add("Categoria não encontrada para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterCategoriaDto(categoria.get()));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Adiciona uma nova categoria de receita.
	 * 
	 * @param categoriaReceitaDto
	 * @param result
	 * @return ResponseEntity<Response<CategoriaReceitaDto>>
	 * @throws ParseException 
	 */
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<CategoriaReceitaDto>> adicionar(@Valid @RequestBody CategoriaReceitaDto categoriaReceitaDto,
			BindingResult result) throws ParseException {
		log.info("Adicionando categoria: {}", categoriaReceitaDto.toString());
		Response<CategoriaReceitaDto> response = new Response<CategoriaReceitaDto>();
		validarProfissional(categoriaReceitaDto, result);
		CategoriaReceita categoria = this.converterDtoParaCategoriaReceita(categoriaReceitaDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando categoria: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		categoria = this.categoriaReceitaService.persistir(categoria);
		response.setData(this.converterCategoriaDto(categoria));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Atualiza os dados de uma categoria de receita.
	 * 
	 * @param id
	 * @param categoriaReceitaDto
	 * @return ResponseEntity<Response<CategoriaReceita>>
	 * @throws ParseException 
	 */
	@PutMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<CategoriaReceitaDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody CategoriaReceitaDto categoriaReceitaDto, BindingResult result) throws ParseException {
		log.info("Atualizando categoria: {}", categoriaReceitaDto.toString());
		Response<CategoriaReceitaDto> response = new Response<CategoriaReceitaDto>();
		validarProfissional(categoriaReceitaDto, result);
		categoriaReceitaDto.setId(Optional.of(id));
		CategoriaReceita categoria = this.converterDtoParaCategoriaReceita(categoriaReceitaDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando categoria: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		categoria = this.categoriaReceitaService.persistir(categoria);
		response.setData(this.converterCategoriaDto(categoria));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Remove uma categoria de receita por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<CategoriaReceita>>
	 */
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo categoria: {}", id);
		Response<String> response = new Response<String>();
		Optional<CategoriaReceita> categoria = this.categoriaReceitaService.buscarPorId(id);

		if (!categoria.isPresent()) {
			log.info("Erro ao remover devido a categoria ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover categoria. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.categoriaReceitaService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	/**
	 * Valida um profissional, verificando se ele é existente e válido no
	 * sistema.
	 * 
	 * @param categoriaReceitaDto
	 * @param result
	 */
	private void validarProfissional(CategoriaReceitaDto categoriaReceitaDto, BindingResult result) {
		if (categoriaReceitaDto.getProfissionalId() == null) {
			result.addError(new ObjectError("profissional", "Profissional não informado."));
			return;
		}

		log.info("Validando profissional id {}: ", categoriaReceitaDto.getProfissionalId());
		Optional<Profissional> profissional = this.profissionalService.buscarPorUsuarioId(categoriaReceitaDto.getProfissionalId());
		if (!profissional.isPresent()) {
			result.addError(new ObjectError("profissional", "Profissional não encontrado. ID inexistente."));
		}
	}
	
	/**
	 * Converte uma entidade categoriaReceita para seu respectivo DTO.
	 * 
	 * @param categoriaReceita
	 * @return CategoriaReceitaDto
	 */
	private CategoriaReceitaDto converterCategoriaDto(CategoriaReceita categoriaReceita) {
		
		CategoriaReceitaDto categoriaReceitaDto = new CategoriaReceitaDto();
		categoriaReceitaDto.setId(Optional.of(categoriaReceita.getId()));
		categoriaReceitaDto.setDescricao(categoriaReceita.getDescricao());
		
		Long profissionalId = profissionalService.buscarPorUsuarioId(categoriaReceita.getProfissional().getId()).get().getId();
		categoriaReceitaDto.setProfissionalId(profissionalId);

		return categoriaReceitaDto;
	}
	
	/**
	 * Converte uma CategoriaReceitaDto para uma entidade CategoriaReceita.
	 * 
	 * @param categoriaReceitaDto
	 * @param result
	 * @return CategoriaReceita
	 * @throws ParseException 
	 */
	private CategoriaReceita converterDtoParaCategoriaReceita(CategoriaReceitaDto categoriaReceitaDto, BindingResult result) throws ParseException {
		CategoriaReceita categoria = new CategoriaReceita();

		if (categoriaReceitaDto.getId().isPresent()) {
			Optional<CategoriaReceita> cat = this.categoriaReceitaService.buscarPorId(categoriaReceitaDto.getId().get());
			if (cat.isPresent()) {
				categoria = cat.get();
			} else {
				result.addError(new ObjectError("categoria", "Categoria não encontrada."));
			}
		} else {
			Profissional profissional = profissionalService.buscarPorUsuarioId(categoriaReceitaDto.getProfissionalId()).get();
			categoria.setProfissional(profissional);
		}

		categoria.setDescricao(categoriaReceitaDto.getDescricao());

		return categoria;
	}


}
