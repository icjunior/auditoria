package br.com.bigsupermercados.audit.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.bigsupermercados.audit.model.Usuario;
import br.com.bigsupermercados.audit.security.UsuarioSistema;

@Service
public class UsuarioAutenticadoService {

	public static Usuario usuarioAutenticado() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UsuarioSistema usuarioSistema = (UsuarioSistema) authentication.getPrincipal();
		return usuarioSistema.getUsuario();
	}
}
