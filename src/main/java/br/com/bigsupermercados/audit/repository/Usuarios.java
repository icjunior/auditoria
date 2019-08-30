package br.com.bigsupermercados.audit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bigsupermercados.audit.model.Usuario;
import br.com.bigsupermercados.audit.repository.helper.usuario.UsuariosQueries;

@Repository
public interface Usuarios extends JpaRepository<Usuario, Long>, UsuariosQueries{

	public Optional<Usuario> findByLoginIgnoreCase(String login);
}
