package br.com.bigsupermercados.audit.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import br.com.bigsupermercados.audit.validation.AtributoConfirmacao;

@AtributoConfirmacao(atributo = "senha", atributoConfirmacao = "confirmacaoSenha", message = "Confirmação da senha não confere")
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@NotBlank(message = "Nome do usuário não pode ser em branco")
	@Size(max = 100)
	private String nome;

	@NotBlank(message = "Login não pode ser em branco")
	@Size(max = 50)
	private String login;

	private String senha;

	@Transient
	private String confirmacaoSenha;

	@Size(min = 1, message = "Selecione pelo menos um grupo")
	@ManyToMany
	@JoinTable(name = "usuario_grupo", joinColumns = @JoinColumn(name = "codigo_usuario"), inverseJoinColumns = @JoinColumn(name = "codigo_grupo"))
	private List<Grupo> grupos;

	@NotNull(message = "Uma loja deve ser selecionada")
	@ManyToOne
	@JoinColumn(name = "loja_codigo")
	private Loja loja;

	@OneToMany(mappedBy = "usuario")
	private List<Auditoria> auditorias;

	private boolean ativo = true;

	public boolean isNovo() {
		return this.codigo == null;
	}

	@PreUpdate
	private void preUpdate() {
		this.confirmacaoSenha = this.senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public List<Auditoria> getAuditorias() {
		return auditorias;
	}

	public void setAuditorias(List<Auditoria> auditorias) {
		this.auditorias = auditorias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}
