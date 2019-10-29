package br.com.bigsupermercados.audit.repository.helper.grupo;

import java.util.List;

import br.com.bigsupermercados.audit.model.Grupo;
import br.com.bigsupermercados.audit.model.Usuario;

public interface GruposQueries {

	public List<Grupo> porUsuario(Usuario usuario);
}
