package br.com.bigsupermercados.audit.repository.filter;

import java.util.List;

import br.com.bigsupermercados.audit.model.Grupo;

public class UsuarioFilter {

	private String nome;
	private String login;
	private List<Grupo> grupos;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}
}