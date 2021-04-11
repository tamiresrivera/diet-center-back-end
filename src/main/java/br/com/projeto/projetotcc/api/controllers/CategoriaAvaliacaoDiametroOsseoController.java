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

import br.com.projeto.projetotcc.api.dtos.CategoriaAvaliacaoDiametroOsseoDto;
import br.com.projeto.projetotcc.api.entities.CategoriaAvaliacaoDiametroOsseo;
import br.com.projeto.projetotcc.api.entities.Profissional;
import br.com.projeto.projetotcc.api.response.Response;
import br.com.projeto.projetotcc.api.services.CategoriaAvaliacaoDiametroOsseoService;
import br.com.projeto.projetotcc.api.services.ProfissionalService;

@RestController
@RequestMapping("/api/categoria-avaliacao-diametro")
@CrossOrigin(origins = "*")
public class CategoriaAvaliacaoDiametroOsseoController {
	
	private static final Logger log = LoggerFactory.getLogger(CategoriaAvaliacaoDiametroOsseoController.class);
	
	@Autowired
	private CategoriaAvaliacaoDiametroOsseoService categoriaAvaliacaoDiametroOsseoService;

	@Autowired
	private ProfissionalService profissionalService;
	
	public CategoriaAvaliacaoDiametroOsseoController() {
	}
	
	/**
	 * Retorna as categorias de diâmetros de um profissional;
	 * 
	 * @param profissionalId
	 * @return ResponseEntity<Response<CategoriaAvaliacaoDiametroOsseoDto>>
	 */
	@GetMapping(value = "/profissional/{profissionalId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<List<CategoriaAvaliacaoDiametroOsseoDto>>> listarPorProfissionalId(
			@PathVariable("profissionalId") Long profissionalId) {
		log.info("Buscando todas as categorias de diâmetros por ID do profissional: {}", profissionalId);
		Response<List<CategoriaAvaliacaoDiametroOsseoDto>> response = new Response<List<CategoriaAvaliacaoDiametroOsseoDto>>();

		Profissional profissional = profissionalService.buscarPorUsuarioId(profissionalId).get();
		
		List<CategoriaAvaliacaoDiametroOsseo> categorias = this.categoriaAvaliacaoDiametroOsseoService.buscarPorProfissionalId(profissional.getId());
		List<CategoriaAvaliacaoDiametroOsseoDto> categoriasDto = categorias.stream()
				.map(categoria -> this.converterCategoriaDto(categoria))
				.collect(Collectors.toList());

		response.setData(categoriasDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna uma categoria de diâmetros por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<CategoriaAvaliacaoDiametroOsseoDto>>
	 */
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<CategoriaAvaliacaoDiametroOsseoDto>> listarPorId(@PathVariable("id") Long id) {
		log.info("Buscando categoria por ID: {}", id);
		Response<CategoriaAvaliacaoDiametroOsseoDto> response = new Response<CategoriaAvaliacaoDiametroOsseoDto>();
		Optional<CategoriaAvaliacaoDiametroOsseo> categoria = this.categoriaAvaliacaoDiametroOsseoService.buscarPorId(id);

		if (!categoria.isPresent()) {
			log.info("Categoria não encontrada para o ID: {}", id);
			response.getErrors().add("Categoria não encontrada para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterCategoriaDto(categoria.get()));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Adiciona uma nova categoria de diãmetro.
	 * 
	 * @param categoriaAvaliacaoDiametroOsseoDto
	 * @param result
	 * @return ResponseEntity<Response<CategoriaAvaliacaoDiametroOsseo>>
	 * @throws ParseException 
	 */
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<CategoriaAvaliacaoDiametroOsseoDto>> adicionar(@Valid @RequestBody CategoriaAvaliacaoDiametroOsseoDto categoriaAvaliacaoDiametroOsseoDto,
			BindingResult result) throws ParseException {
		log.info("Adicionando categoria: {}", categoriaAvaliacaoDiametroOsseoDto.toString());
		Response<CategoriaAvaliacaoDiametroOsseoDto> response = new Response<CategoriaAvaliacaoDiametroOsseoDto>();
		validarProfissional(categoriaAvaliacaoDiametroOsseoDto, result);
		CategoriaAvaliacaoDiametroOsseo categoria = this.converterDtoParaCategoriaDiametro(categoriaAvaliacaoDiametroOsseoDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando categoria: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		categoria = this.categoriaAvaliacaoDiametroOsseoService.persistir(categoria);
		response.setData(this.converterCategoriaDto(categoria));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Atualiza os dados de uma categoria de diãmetro.
	 * 
	 * @param id
	 * @param categoriaAvaliacaoDiametroOsseoDto
	 * @return ResponseEntity<Response<CategoriaAvaliacaoDiametroOsseo>>
	 * @throws ParseException 
	 */
	@PutMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<CategoriaAvaliacaoDiametroOsseoDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody CategoriaAvaliacaoDiametroOsseoDto categoriaAvaliacaoDiametroOsseoDto, BindingResult result) throws ParseException {
		log.info("Atualizando categoria: {}", categoriaAvaliacaoDiametroOsseoDto.toString());
		Response<CategoriaAvaliacaoDiametroOsseoDto> response = new Response<CategoriaAvaliacaoDiametroOsseoDto>();
		validarProfissional(categoriaAvaliacaoDiametroOsseoDto, result);
		categoriaAvaliacaoDiametroOsseoDto.setId(Optional.of(id));
		CategoriaAvaliacaoDiametroOsseo categoria = this.converterDtoParaCategoriaDiametro(categoriaAvaliacaoDiametroOsseoDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando categoria: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		categoria = this.categoriaAvaliacaoDiametroOsseoService.persistir(categoria);
		response.setData(this.converterCategoriaDto(categoria));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Remove uma categoria de diâmetro por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<CategoriaAvaliacaoDiametroOsseo>>
	 */
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo categoria: {}", id);
		Response<String> response = new Response<String>();
		Optional<CategoriaAvaliacaoDiametroOsseo> lancamento = this.categoriaAvaliacaoDiametroOsseoService.buscarPorId(id);

		if (!lancamento.isPresent()) {
			log.info("Erro ao remover devido a categoria ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover categoria. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.categoriaAvaliacaoDiametroOsseoService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	/**
	 * Valida um profissional, verificando se ele é existente e válido no
	 * sistema.
	 * 
	 * @param categoriaReceitaDto
	 * @param result
	 */
	private void validarProfissional(CategoriaAvaliacaoDiametroOsseoDto categoriaAvaliacaoDiametroOsseoDto, BindingResult result) {
		if (categoriaAvaliacaoDiametroOsseoDto.getProfissionalId() == null) {
			result.addError(new ObjectError("profissional", "Profissional não informado."));
			return;
		}

		log.info("Validando profissional id {}: ", categoriaAvaliacaoDiametroOsseoDto.getProfissionalId());
		Optional<Profissional> profissional = this.profissionalService.buscarPorUsuarioId(categoriaAvaliacaoDiametroOsseoDto.getProfissionalId());
		if (!profissional.isPresent()) {
			result.addError(new ObjectError("profissional", "Profissional não encontrado. ID inexistente."));
		}
	}
	
	/**
	 * Converte uma entidade CategoriaAvaliacaoDiametroOsseo para seu respectivo DTO.
	 * 
	 * @param categoriaAvaliacaoDiametroOsseo
	 * @return CategoriaAvaliacaoDiametroOsseoDto
	 */
	private CategoriaAvaliacaoDiametroOsseoDto converterCategoriaDto(CategoriaAvaliacaoDiametroOsseo categoriaAvaliacaoDiametroOsseo) {
		
		CategoriaAvaliacaoDiametroOsseoDto categoriaAvaliacaoDiametroOsseoDto = new CategoriaAvaliacaoDiametroOsseoDto();
		categoriaAvaliacaoDiametroOsseoDto.setId(Optional.of(categoriaAvaliacaoDiametroOsseo.getId()));
		categoriaAvaliacaoDiametroOsseoDto.setDescricao(categoriaAvaliacaoDiametroOsseo.getDescricao());
		
		Long profissionalId = profissionalService.buscarPorId(categoriaAvaliacaoDiametroOsseo.getProfissional().getId()).get().getId();
		categoriaAvaliacaoDiametroOsseoDto.setProfissionalId(profissionalId);

		return categoriaAvaliacaoDiametroOsseoDto;
	}
	
	/**
	 * Converte uma CategoriaAvaliacaoDiametroOsseoDto para uma entidade categoriaAvaliacaoDiametroOsseoDto.
	 * 
	 * @param categoriaAvaliacaoDiametroOsseoDtoDto
	 * @param result
	 * @return categoriaAvaliacaoDiametroOsseoDto
	 * @throws ParseException 
	 */
	private CategoriaAvaliacaoDiametroOsseo converterDtoParaCategoriaDiametro(CategoriaAvaliacaoDiametroOsseoDto categoriaAvaliacaoDiametroOsseoDto, BindingResult result) throws ParseException {
		CategoriaAvaliacaoDiametroOsseo categoria = new CategoriaAvaliacaoDiametroOsseo();

		if (categoriaAvaliacaoDiametroOsseoDto.getId().isPresent()) {
			Optional<CategoriaAvaliacaoDiametroOsseo> cat = this.categoriaAvaliacaoDiametroOsseoService.buscarPorId(categoriaAvaliacaoDiametroOsseoDto.getId().get());
			if (cat.isPresent()) {
				categoria = cat.get();
			} else {
				result.addError(new ObjectError("categoria", "Categoria não encontrada."));
			}
		} else {
			Profissional profissional = profissionalService.buscarPorUsuarioId(categoriaAvaliacaoDiametroOsseoDto.getProfissionalId()).get();
			categoria.setProfissional(profissional);
		}

		categoria.setDescricao(categoriaAvaliacaoDiametroOsseoDto.getDescricao());

		return categoria;
	}


}
