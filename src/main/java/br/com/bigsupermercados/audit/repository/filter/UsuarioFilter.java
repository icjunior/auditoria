package br.com.bigsupermercados.audit.repository.filter;

public class UsuarioFilter {

	private String nome;
	private String login;

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
}