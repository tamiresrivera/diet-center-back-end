package br.com.projeto.projetotcc.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.projeto.projetotcc.api.entities.Usuario;

@Transactional(readOnly = true)
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Usuario findByNomeUsuario(String nomeUsuario);
	
	Usuario findByCpf(String cpf);
	
	Usuario findByEmail(String email);

	@Query(value = "SELECT d.* FROM usuario a " +
			       "JOIN profissional b on b.usuario_id = a.id " + 
			       "JOIN paciente c on c.profissional_id = b.id " +
			       "JOIN usuario d on d.id = c.usuario_id " + 
			       "WHERE b.usuario_id = :profissionalId " +
			       " #{#pageable} ORDER BY a.nome",
		   countQuery = "SELECT count(a.*) FROM usuario a " +
				        "JOIN profissional b on b.usuario_id = a.id " +
				        "JOIN paciente c on c.profissional_id = b.id " +
				        "JOIN usuario d on d.id = c.usuario_id " +
				        "WHERE b.usuario_id = :profissionalId"
			            ,
		   nativeQuery = true)
	    Page<Usuario> buscaPorProfissionalId(
	    		@Param("profissionalId") Long profissionalId, 
	            Pageable pageable);
	
	@Query(value = "SELECT d.* FROM usuario a " +
		       "JOIN profissional b on b.usuario_id = a.id " + 
		       "JOIN paciente c on c.profissional_id = b.id " +
		       "JOIN usuario d on d.id = c.usuario_id " + 
		       "WHERE b.usuario_id = :profissionalId " +
		       "ORDER BY a.nome",
	           nativeQuery = true)
	List<Usuario> buscaTodosPorProfissionalId(
 		@Param("profissionalId") Long profissionalId);
	
	@Query(value = "SELECT d.* FROM usuario a " +
		       "JOIN profissional b on b.usuario_id = a.id " + 
		       "JOIN paciente c on c.profissional_id = b.id " +
		       "JOIN usuario d on d.id = c.usuario_id " + 
		       "WHERE b.usuario_id = :receitaId " +
		       "ORDER BY a.nome",
	           nativeQuery = true)
	List<Usuario> buscaTodosPorReceitaId(
	 		@Param("receitaId") Long receitaId);
	
}
