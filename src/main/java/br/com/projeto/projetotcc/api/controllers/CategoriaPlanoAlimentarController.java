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

import br.com.projeto.projetotcc.api.dtos.CategoriaPlanoAlimentarDto;
import br.com.projeto.projetotcc.api.entities.CategoriaPlanoAlimentar;
import br.com.projeto.projetotcc.api.entities.Profissional;
import br.com.projeto.projetotcc.api.response.Response;
import br.com.projeto.projetotcc.api.services.CategoriaPlanoAlimentarService;
import br.com.projeto.projetotcc.api.services.ProfissionalService;

@RestController
@RequestMapping("/api/categoria-plano-alimentar")
@CrossOrigin(origins = "*")
public class CategoriaPlanoAlimentarController {
	
	private static final Logger log = LoggerFactory.getLogger(CategoriaPlanoAlimentarController.class);
	
	@Autowired
	private CategoriaPlanoAlimentarService categoriaPlanoAlimentarService;

	@Autowired
	private ProfissionalService profissionalService;
	
	public CategoriaPlanoAlimentarController() {
	}
	
	/**
	 * Retorna as categorias de planos de um profissional;
	 * 
	 * @param profissionalId
	 * @return ResponseEntity<Response<CategoriaPlanoAlimentarDto>>
	 */
	@GetMapping(value = "/profissional/{profissionalId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<List<CategoriaPlanoAlimentarDto>>> listarPorProfissionalId(
			@PathVariable("profissionalId") Long profissionalId) {
		log.info("Buscando todas as categorias de diâmetros por ID do profissional: {}", profissionalId);
		Response<List<CategoriaPlanoAlimentarDto>> response = new Response<List<CategoriaPlanoAlimentarDto>>();

		Profissional profissional = profissionalService.buscarPorUsuarioId(profissionalId).get();
		
		List<CategoriaPlanoAlimentar> categorias = this.categoriaPlanoAlimentarService.buscarPorProfissionalId(profissional.getId());
		List<CategoriaPlanoAlimentarDto> categoriasDto = categorias.stream()
				.map(categoria -> this.converterCategoriaDto(categoria))
				.collect(Collectors.toList());

		response.setData(categoriasDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna uma categoria de plano por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<CategoriaPlanoAlimentarDto>>
	 */
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<CategoriaPlanoAlimentarDto>> listarPorId(@PathVariable("id") Long id) {
		log.info("Buscando categoria por ID: {}", id);
		Response<CategoriaPlanoAlimentarDto> response = new Response<CategoriaPlanoAlimentarDto>();
		Optional<CategoriaPlanoAlimentar> categoria = this.categoriaPlanoAlimentarService.buscarPorId(id);

		if (!categoria.isPresent()) {
			log.info("Categoria não encontrada para o ID: {}", id);
			response.getErrors().add("Categoria não encontrada para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterCategoriaDto(categoria.get()));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Adiciona uma nova categoria de plano.
	 * 
	 * @param categoriaPlanoAlimentarDgto
	 * @param result
	 * @return ResponseEntity<Response<CategoriaPlanoAlimentar>>
	 * @throws ParseException 
	 */
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<CategoriaPlanoAlimentarDto>> adicionar(@Valid @RequestBody CategoriaPlanoAlimentarDto categoriaPlanoAlimentarDto,
			BindingResult result) throws ParseException {
		log.info("Adicionando categoria: {}", categoriaPlanoAlimentarDto.toString());
		Response<CategoriaPlanoAlimentarDto> response = new Response<CategoriaPlanoAlimentarDto>();
		validarProfissional(categoriaPlanoAlimentarDto, result);
		CategoriaPlanoAlimentar categoria = this.converterDtoParaCategoriaPlano(categoriaPlanoAlimentarDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando categoria: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		categoria = this.categoriaPlanoAlimentarService.persistir(categoria);
		response.setData(this.converterCategoriaDto(categoria));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Atualiza os dados de uma categoria de diãmetro.
	 * 
	 * @param id
	 * @param categoriaPlanoAlimentarDto
	 * @return ResponseEntity<Response<CategoriaPlanoAlimentar>>
	 * @throws ParseException 
	 */
	@PutMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<CategoriaPlanoAlimentarDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody CategoriaPlanoAlimentarDto categoriaPlanoAlimentarDto, BindingResult result) throws ParseException {
		log.info("Atualizando categoria: {}", categoriaPlanoAlimentarDto.toString());
		Response<CategoriaPlanoAlimentarDto> response = new Response<CategoriaPlanoAlimentarDto>();
		validarProfissional(categoriaPlanoAlimentarDto, result);
		categoriaPlanoAlimentarDto.setId(Optional.of(id));
		CategoriaPlanoAlimentar categoria = this.converterDtoParaCategoriaPlano(categoriaPlanoAlimentarDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando categoria: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		categoria = this.categoriaPlanoAlimentarService.persistir(categoria);
		response.setData(this.converterCategoriaDto(categoria));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Remove uma categoria de plano alimentar por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<CategoriaPlanoAlimentar>>
	 */
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo categoria: {}", id);
		Response<String> response = new Response<String>();
		Optional<CategoriaPlanoAlimentar> lancamento = this.categoriaPlanoAlimentarService.buscarPorId(id);

		if (!lancamento.isPresent()) {
			log.info("Erro ao remover devido a categoria ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover categoria. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.categoriaPlanoAlimentarService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	/**
	 * Valida um profissional, verificando se ele é existente e válido no
	 * sistema.
	 * 
	 * @param categoriaReceitaDto
	 * @param result
	 */
	private void validarProfissional(CategoriaPlanoAlimentarDto categoriaPlanoAlimentarDto, BindingResult result) {
		if (categoriaPlanoAlimentarDto.getProfissionalId() == null) {
			result.addError(new ObjectError("profissional", "Profissional não informado."));
			return;
		}

		log.info("Validando profissional id {}: ", categoriaPlanoAlimentarDto.getProfissionalId());
		Optional<Profissional> profissional = this.profissionalService.buscarPorUsuarioId(categoriaPlanoAlimentarDto.getProfissionalId());
		if (!profissional.isPresent()) {
			result.addError(new ObjectError("profissional", "Profissional não encontrado. ID inexistente."));
		}
	}
	
	/**
	 * Converte uma entidade CategoriaPlanoAlimentar para seu respectivo DTO.
	 * 
	 * @param categoriaPlanoAlimentar
	 * @return CategoriaPlanoAlimentarDto
	 */
	private CategoriaPlanoAlimentarDto converterCategoriaDto(CategoriaPlanoAlimentar categoriaPlanoAlimentar) {
		
		CategoriaPlanoAlimentarDto categoriaPlanoAlimentarDto = new CategoriaPlanoAlimentarDto();
		categoriaPlanoAlimentarDto.setId(Optional.of(categoriaPlanoAlimentar.getId()));
		categoriaPlanoAlimentarDto.setDescricao(categoriaPlanoAlimentar.getDescricao());
		
		Long profissionalId = profissionalService.buscarPorId(categoriaPlanoAlimentar.getProfissional().getId()).get().getId();
		categoriaPlanoAlimentarDto.setProfissionalId(profissionalId);

		return categoriaPlanoAlimentarDto;
	}
	
	/**
	 * Converte uma CategoriaAvaliacaoPregaDto para uma entidade CategoriaPlanoAlimentar.
	 * 
	 * @param CategoriaPlanoAlimentarDto
	 * @param result
	 * @return CategoriaPlanoAlimentar
	 * @throws ParseException 
	 */
	private CategoriaPlanoAlimentar converterDtoParaCategoriaPlano(CategoriaPlanoAlimentarDto categoriaPlanoAlimentarDto, BindingResult result) throws ParseException {
		CategoriaPlanoAlimentar categoria = new CategoriaPlanoAlimentar();

		if (categoriaPlanoAlimentarDto.getId().isPresent()) {
			Optional<CategoriaPlanoAlimentar> cat = this.categoriaPlanoAlimentarService.buscarPorId(categoriaPlanoAlimentarDto.getId().get());
			if (cat.isPresent()) {
				categoria = cat.get();
			} else {
				result.addError(new ObjectError("categoria", "Categoria não encontrada."));
			}
		} else {
			Profissional profissional = profissionalService.buscarPorUsuarioId(categoriaPlanoAlimentarDto.getProfissionalId()).get();
			categoria.setProfissional(profissional);
		}

		categoria.setDescricao(categoriaPlanoAlimentarDto.getDescricao());

		return categoria;
	}


}
