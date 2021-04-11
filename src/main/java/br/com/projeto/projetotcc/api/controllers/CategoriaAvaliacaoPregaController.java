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

import br.com.projeto.projetotcc.api.dtos.CategoriaAvaliacaoPregaDto;
import br.com.projeto.projetotcc.api.entities.CategoriaAvaliacaoPrega;
import br.com.projeto.projetotcc.api.entities.Profissional;
import br.com.projeto.projetotcc.api.response.Response;
import br.com.projeto.projetotcc.api.services.CategoriaAvaliacaoPregaService;
import br.com.projeto.projetotcc.api.services.ProfissionalService;

@RestController
@RequestMapping("/api/categoria-avaliacao-prega")
@CrossOrigin(origins = "*")
public class CategoriaAvaliacaoPregaController {
	
	private static final Logger log = LoggerFactory.getLogger(CategoriaAvaliacaoDiametroOsseoController.class);
	
	@Autowired
	private CategoriaAvaliacaoPregaService categoriaAvaliacaoPregaService;

	@Autowired
	private ProfissionalService profissionalService;
	
	public CategoriaAvaliacaoPregaController() {
	}
	
	/**
	 * Retorna as categorias de pregas de um profissional;
	 * 
	 * @param profissionalId
	 * @return ResponseEntity<Response<CategoriaAvaliacaoPregaDto>>
	 */
	@GetMapping(value = "/profissional/{profissionalId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<List<CategoriaAvaliacaoPregaDto>>> listarPorProfissionalId(
			@PathVariable("profissionalId") Long profissionalId) {
		log.info("Buscando todas as categorias de diâmetros por ID do profissional: {}", profissionalId);
		Response<List<CategoriaAvaliacaoPregaDto>> response = new Response<List<CategoriaAvaliacaoPregaDto>>();

		Profissional profissional = profissionalService.buscarPorUsuarioId(profissionalId).get();
		
		List<CategoriaAvaliacaoPrega> categorias = this.categoriaAvaliacaoPregaService.buscarPorProfissionalId(profissional.getId());
		List<CategoriaAvaliacaoPregaDto> categoriasDto = categorias.stream()
				.map(categoria -> this.converterCategoriaDto(categoria))
				.collect(Collectors.toList());

		response.setData(categoriasDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna uma categoria de prega por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<CategoriaAvaliacaoPregaDto>>
	 */
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<CategoriaAvaliacaoPregaDto>> listarPorId(@PathVariable("id") Long id) {
		log.info("Buscando categoria por ID: {}", id);
		Response<CategoriaAvaliacaoPregaDto> response = new Response<CategoriaAvaliacaoPregaDto>();
		Optional<CategoriaAvaliacaoPrega> categoria = this.categoriaAvaliacaoPregaService.buscarPorId(id);

		if (!categoria.isPresent()) {
			log.info("Categoria não encontrada para o ID: {}", id);
			response.getErrors().add("Categoria não encontrada para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterCategoriaDto(categoria.get()));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Adiciona uma nova categoria de prega.
	 * 
	 * @param categoriaAvaliacaoPregaDto
	 * @param result
	 * @return ResponseEntity<Response<CategoriaAvaliacaoPrega>>
	 * @throws ParseException 
	 */
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<CategoriaAvaliacaoPregaDto>> adicionar(@Valid @RequestBody CategoriaAvaliacaoPregaDto categoriaAvaliacaoPregaDto,
			BindingResult result) throws ParseException {
		log.info("Adicionando categoria: {}", categoriaAvaliacaoPregaDto.toString());
		Response<CategoriaAvaliacaoPregaDto> response = new Response<CategoriaAvaliacaoPregaDto>();
		validarProfissional(categoriaAvaliacaoPregaDto, result);
		CategoriaAvaliacaoPrega categoria = this.converterDtoParaCategoriaPrega(categoriaAvaliacaoPregaDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando categoria: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		categoria = this.categoriaAvaliacaoPregaService.persistir(categoria);
		response.setData(this.converterCategoriaDto(categoria));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Atualiza os dados de uma categoria de diãmetro.
	 * 
	 * @param id
	 * @param categoriaAvaliacaoPregaDto
	 * @return ResponseEntity<Response<CategoriaAvaliacaoPrega>>
	 * @throws ParseException 
	 */
	@PutMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<CategoriaAvaliacaoPregaDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody CategoriaAvaliacaoPregaDto categoriaAvaliacaoPregaDto, BindingResult result) throws ParseException {
		log.info("Atualizando categoria: {}", categoriaAvaliacaoPregaDto.toString());
		Response<CategoriaAvaliacaoPregaDto> response = new Response<CategoriaAvaliacaoPregaDto>();
		validarProfissional(categoriaAvaliacaoPregaDto, result);
		categoriaAvaliacaoPregaDto.setId(Optional.of(id));
		CategoriaAvaliacaoPrega categoria = this.converterDtoParaCategoriaPrega(categoriaAvaliacaoPregaDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando categoria: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		categoria = this.categoriaAvaliacaoPregaService.persistir(categoria);
		response.setData(this.converterCategoriaDto(categoria));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Remove uma categoria de prega por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<CategoriaAvaliacaoPrega>>
	 */
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo categoria: {}", id);
		Response<String> response = new Response<String>();
		Optional<CategoriaAvaliacaoPrega> lancamento = this.categoriaAvaliacaoPregaService.buscarPorId(id);

		if (!lancamento.isPresent()) {
			log.info("Erro ao remover devido a categoria ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover categoria. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.categoriaAvaliacaoPregaService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	/**
	 * Valida um profissional, verificando se ele é existente e válido no
	 * sistema.
	 * 
	 * @param categoriaReceitaDto
	 * @param result
	 */
	private void validarProfissional(CategoriaAvaliacaoPregaDto categoriaAvaliacaoPregaDto, BindingResult result) {
		if (categoriaAvaliacaoPregaDto.getProfissionalId() == null) {
			result.addError(new ObjectError("profissional", "Profissional não informado."));
			return;
		}

		log.info("Validando profissional id {}: ", categoriaAvaliacaoPregaDto.getProfissionalId());
		Optional<Profissional> profissional = this.profissionalService.buscarPorUsuarioId(categoriaAvaliacaoPregaDto.getProfissionalId());
		if (!profissional.isPresent()) {
			result.addError(new ObjectError("profissional", "Profissional não encontrado. ID inexistente."));
		}
	}
	
	/**
	 * Converte uma entidade CategoriaAvaliacaoPrega para seu respectivo DTO.
	 * 
	 * @param categoriaAvaliacaoPrega
	 * @return CategoriaAvaliacaoPregaDto
	 */
	private CategoriaAvaliacaoPregaDto converterCategoriaDto(CategoriaAvaliacaoPrega categoriaAvaliacaoPrega) {
		
		CategoriaAvaliacaoPregaDto categoriaAvaliacaoPregaDto = new CategoriaAvaliacaoPregaDto();
		categoriaAvaliacaoPregaDto.setId(Optional.of(categoriaAvaliacaoPrega.getId()));
		categoriaAvaliacaoPregaDto.setDescricao(categoriaAvaliacaoPrega.getDescricao());
		
		Long profissionalId = profissionalService.buscarPorId(categoriaAvaliacaoPrega.getProfissional().getId()).get().getId();
		categoriaAvaliacaoPregaDto.setProfissionalId(profissionalId);

		return categoriaAvaliacaoPregaDto;
	}
	
	/**
	 * Converte uma CategoriaAvaliacaoPregaDto para uma entidade CategoriaAvaliacaoPrega.
	 * 
	 * @param CategoriaAvaliacaoPregaDto
	 * @param result
	 * @return categoriaAvaliacaoPregaoDto
	 * @throws ParseException 
	 */
	private CategoriaAvaliacaoPrega converterDtoParaCategoriaPrega(CategoriaAvaliacaoPregaDto categoriaAvaliacaoPregaDto, BindingResult result) throws ParseException {
		CategoriaAvaliacaoPrega categoria = new CategoriaAvaliacaoPrega();

		if (categoriaAvaliacaoPregaDto.getId().isPresent()) {
			Optional<CategoriaAvaliacaoPrega> cat = this.categoriaAvaliacaoPregaService.buscarPorId(categoriaAvaliacaoPregaDto.getId().get());
			if (cat.isPresent()) {
				categoria = cat.get();
			} else {
				result.addError(new ObjectError("categoria", "Categoria não encontrada."));
			}
		} else {
			Profissional profissional = profissionalService.buscarPorUsuarioId(categoriaAvaliacaoPregaDto.getProfissionalId()).get();
			categoria.setProfissional(profissional);
		}

		categoria.setDescricao(categoriaAvaliacaoPregaDto.getDescricao());

		return categoria;
	}


}
