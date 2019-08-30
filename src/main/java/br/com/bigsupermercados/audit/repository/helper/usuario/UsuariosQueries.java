package br.com.bigsupermercados.audit.repository.helper.usuario;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.bigsupermercados.audit.model.Usuario;
import br.com.bigsupermercados.audit.repository.filter.UsuarioFilter;

public interface UsuariosQueries {

	public Page<Usuario> filtrar(UsuarioFilter filtro, Pageable pageable);
	
	public Optional<Usuario> porLoginEAtivo(String email);
}
