package br.com.projeto.projetotcc.api.controllers;

import java.security.NoSuchAlgorithmException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.projetotcc.api.dtos.CadastroUsuarioDto;
import br.com.projeto.projetotcc.api.entities.Paciente;
import br.com.projeto.projetotcc.api.entities.Profissional;
import br.com.projeto.projetotcc.api.entities.Usuario;
import br.com.projeto.projetotcc.api.response.Response;
import br.com.projeto.projetotcc.api.services.PacienteService;
import br.com.projeto.projetotcc.api.services.ProfissionalService;
import br.com.projeto.projetotcc.api.services.UsuarioService;
import br.com.projeto.projetotcc.api.utils.PasswordUtils;
import br.com.projeto.projetotcc.api.enums.PerfilEnum;

@RestController
@RequestMapping("/api/cadastrar-usuario")
@CrossOrigin(origins = "*")
public class CadastroUsuarioController {
	
	private static final Logger log = LoggerFactory.getLogger(CadastroUsuarioController.class);
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ProfissionalService profissionalService;
	
	@Autowired
	private PacienteService pacienteService;

	public CadastroUsuarioController() {
	}
	
	/**
	 * Cadastra um usuário no sistema.
	 * 
	 * @param cadastroUsuarioDto
	 * @param result
	 * @return ResponseEntity<Response<CadastroUsuarioDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping
	public ResponseEntity<Response<CadastroUsuarioDto>> cadastrar(@Valid @RequestBody CadastroUsuarioDto cadastroUsuarioDto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Cadastrando Usuario: {}", cadastroUsuarioDto.toString());
		Response<CadastroUsuarioDto> response = new Response<CadastroUsuarioDto>();

		validarDadosExistentes(cadastroUsuarioDto, result);
		Usuario usuario = this.converterDtoParaUsuario(cadastroUsuarioDto, result);
	

		if (result.hasErrors()) {
			log.error("Erro validando dados de usuário: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.usuarioService.persistir(usuario);
		
		if (usuario.getPerfil() == PerfilEnum.ROLE_ADMIN) {
			//cadastra tabela complementar do profissional
			Profissional profissional = new Profissional();
			profissional.setUsuario(usuario);
			profissionalService.persistir(profissional);
		}
		else {
			//cadastra tabela complementar do paciente
			Paciente paciente = new Paciente();
			paciente.setUsuario(usuario);
			pacienteService.persistir(paciente);
		}

		response.setData(this.converterCadastroUsuarioDto(usuario));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Verifica se o usuario está cadastrado e se o usuário não existe na base de dados.
	 * 
	 * @param cadastroUsuarioDto
	 * @param result
	 */
	private void validarDadosExistentes(CadastroUsuarioDto cadastroUsuarioDto, BindingResult result) {
		
		this.usuarioService.buscarPorCpf(cadastroUsuarioDto.getCpf())
			.ifPresent(func -> result.addError(new ObjectError("usuario", "CPF já existente.")));

		this.usuarioService.buscarPorNomeUsuario(cadastroUsuarioDto.getNomeUsuario())
			.ifPresent(func -> result.addError(new ObjectError("usuario", "Nome de Usuário já existente.")));
	}
	
	/**
	 * Converte os dados do DTO para usuario.
	 * 
	 * @param cadastroUsuarioDto
	 * @param result
	 * @return Usuario
	 * @throws NoSuchAlgorithmException
	 */
	private Usuario converterDtoParaUsuario(CadastroUsuarioDto cadastroUsuarioDto, BindingResult result)
			throws NoSuchAlgorithmException {
		Usuario usuario = new Usuario();
		usuario.setNome(cadastroUsuarioDto.getNome());
		usuario.setNomeUsuario(cadastroUsuarioDto.getNomeUsuario());
		usuario.setEmail(cadastroUsuarioDto.getEmail());
		usuario.setCpf(cadastroUsuarioDto.getCpf());
		usuario.setPerfil(cadastroUsuarioDto.getPerfil());
		usuario.setSenha(PasswordUtils.gerarBCrypt(cadastroUsuarioDto.getSenha()));
		usuario.setTelefone(cadastroUsuarioDto.getTelefone());

		return usuario;
	}
	
	/**
	 * Popula o DTO de cadastro com os dados do usuário.
	 * 
	 * @param usuario
	 * @return CadastroUsuarioDto
	 */
	private CadastroUsuarioDto converterCadastroUsuarioDto(Usuario usuario) {
		CadastroUsuarioDto cadastroUsuarioDto = new CadastroUsuarioDto();
		cadastroUsuarioDto.setId(usuario.getId());
		cadastroUsuarioDto.setNome(usuario.getNome());
		cadastroUsuarioDto.setNomeUsuario(usuario.getNomeUsuario());
		cadastroUsuarioDto.setEmail(usuario.getEmail());
		cadastroUsuarioDto.setCpf(usuario.getCpf());
		cadastroUsuarioDto.setTelefone(usuario.getTelefone());

		return cadastroUsuarioDto;
	}
	
}
