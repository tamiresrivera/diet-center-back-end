package br.com.projeto.projetotcc.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.projeto.projetotcc.api.entities.Usuario;
import br.com.projeto.projetotcc.api.repositories.UsuarioRepository;
import br.com.projeto.projetotcc.api.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	private static final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario persistir(Usuario usuario) {
		log.info("Persistindo Usuário: {}", usuario);
		return this.usuarioRepository.save(usuario);
	}
	
	public Optional<Usuario> buscarPorNomeUsuario(String nomeUsuario) {
		log.info("Buscando Usuário pelo nome de usuario {}", nomeUsuario);
		return Optional.ofNullable(this.usuarioRepository.findByNomeUsuario(nomeUsuario));
	}
	
	public Optional<Usuario> buscarPorId(Long id) {
		log.info("Buscando Usuário pelo ID {}", id);
		return this.usuarioRepository.findById(id);
	}
	
	public Optional<Usuario> buscarPorCpf(String cpf) {
		log.info("Buscando Usuário pelo CPF {}", cpf);
		return Optional.ofNullable(this.usuarioRepository.findByCpf(cpf));
	}
	
	public Optional<Usuario> buscarPorEmail(String email) {
		log.info("Buscando Usuário pelo email {}", email);
		return Optional.ofNullable(this.usuarioRepository.findByEmail(email));
	}
	
	public Page<Usuario> buscarPorProfissionalId(Long profissionalId, PageRequest pageRequest) {
		log.info("Buscando usuarios para o profissional ID {}", profissionalId);
		return this.usuarioRepository.buscaPorProfissionalId(profissionalId, pageRequest);
	}
	
	public List<Usuario> buscarTodosPorProfissionalId(Long profissionalId) {
		log.info("Buscando todos os pacientes para o profissional ID {}", profissionalId);
		return this.usuarioRepository.buscaTodosPorProfissionalId(profissionalId);
	}
	
	public List<Usuario> buscarTodosPorReceitaId(Long receitaId) {
		log.info("Buscando todos os pacientes associados a receita ID {}", receitaId);
		return this.usuarioRepository.buscaTodosPorReceitaId(receitaId);
	}

}
