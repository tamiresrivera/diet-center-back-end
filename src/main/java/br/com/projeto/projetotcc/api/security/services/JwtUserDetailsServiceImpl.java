package br.com.projeto.projetotcc.api.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.projeto.projetotcc.api.entities.Usuario;
import br.com.projeto.projetotcc.api.security.JwtUserFactory;
import br.com.projeto.projetotcc.api.services.UsuarioService;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioService.buscarPorNomeUsuario(username);

		if (usuario.isPresent()) {
			return JwtUserFactory.create(usuario.get());
		}

		throw new UsernameNotFoundException("Nome de Usuário não encontrado.");
	}

}
