package br.com.projeto.projetotcc.api.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.com.projeto.projetotcc.api.entities.Usuario;
import br.com.projeto.projetotcc.api.enums.PerfilEnum;

public class JwtUserFactory {

	private JwtUserFactory() {
	}

	/**
	 * Converte e gera um JwtUser com base nos dados de um usuario.
	 * 
	 * @param usuario
	 * @return JwtUser
	 */
	public static JwtUser create(Usuario usuario) {
		return new JwtUser(usuario.getId(), usuario.getNomeUsuario(), usuario.getSenha(),
				mapToGrantedAuthorities(usuario.getPerfil()));
	}

	/**
	 * Converte o perfil do usuário para o formato utilizado pelo Spring Security.
	 * 
	 * @param perfilEnum
	 * @return List<GrantedAuthority>
	 */
	private static List<GrantedAuthority> mapToGrantedAuthorities(PerfilEnum perfilEnum) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(perfilEnum.toString()));
		return authorities;
	}

}

