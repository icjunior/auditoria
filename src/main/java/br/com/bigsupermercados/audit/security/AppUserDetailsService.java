package br.com.bigsupermercados.audit.security;

import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.bigsupermercados.audit.model.Usuario;
import br.com.bigsupermercados.audit.repository.Usuarios;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private Usuarios usuarios;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

		Optional<Usuario> usuarioOptional = usuarios.porLoginEAtivo(login);
		Usuario usuario = usuarioOptional
				.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorretos"));

		return new UsuarioSistema(usuario, new HashSet<>());
		
	}

//	private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
//		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//
//		// Lista de permissões do usuário
//		List<String> permissoes = usuarios.permissoes(usuario);
//
//		permissoes.forEach(p -> authorities.add(new SimpleGrantedAuthority("ROLE_" + p.toUpperCase())));
//
//		return authorities;
//	}

}
