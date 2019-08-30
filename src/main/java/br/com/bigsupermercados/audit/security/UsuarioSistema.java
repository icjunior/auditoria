package br.com.bigsupermercados.audit.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import br.com.bigsupermercados.audit.model.Usuario;

public class UsuarioSistema extends User {

	private Usuario usuario;
	private static final long serialVersionUID = 1L;

	public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getLogin(), usuario.getSenha(), authorities);
		this.usuario = usuario;
	}


	public Usuario getUsuario() {
		return usuario;
	}

}
