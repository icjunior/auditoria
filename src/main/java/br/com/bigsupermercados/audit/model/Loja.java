package br.com.bigsupermercados.audit.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

@Entity
@Table(name = "loja")
public class Loja implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@NotBlank(message = "Razão social não pode ficar em branco")
	@Size(max = 100, message = "Número de caracteres maior do que o permitido para o campo razão social")
	private String nome;

	@NotBlank(message = "Razão social não pode ficar em branco")
	@Size(max = 50, message = "Número de caracteres maior do que o permitido para o campo apelido")
	private String apelido;

	@NotBlank(message = "Endereço não pode ficar em branco")
	@Size(max = 255, message = "Número de caracteres maior do que o permitido para o campo endereço")
	private String endereco;

	@NotBlank(message = "Número não pode ficar em branco")
	private String numero;

	@NotBlank(message = "Bairro não pode ficar em branco")
	@Size(max = 100, message = "Número de caracteres maior do que o permitido para o campo bairro")
	private String bairro;

	@NotBlank(message = "Cidade não pode ficar em branco")
	@Size(max = 100, message = "Número de caracteres maior do que o permitido para o campo cidade")
	private String cidade;

	@NotBlank(message = "Estado não pode ficar em branco")
	@Size(max = 100, message = "Número de caracteres maior do que o permitido para o campo estado")
	private String estado;

	@NotBlank(message = "CEP não pode ficar em branco")
	@Size(max = 10, message = "Número de caracteres maior do que o permitido para o campo CEP")
	private String cep;

	@NotBlank(message = "CNPJ não pode ficar em branco")
	@CNPJ
	private String cnpj;

	@NotBlank(message = "I.E. não pode ficar em branco")
	@Size(max = 15, message = "Número de caracteres maior do que o permitido para o campo I.E.")
	private String ie;

	@NotBlank(message = "Telefone não pode ficar em branco")
	private String telefone;

	@OneToMany(mappedBy = "loja")
	private List<Auditoria> auditorias;

	@Email
	@Column(name = "email_gerente")
	private String emailGerente;

	@Email
	@Column(name = "email_supervisor")
	private String emailSupervisor;

	@PrePersist
	@PreUpdate
	private void preUpdate() {
		this.cnpj = removerFormatacao(cnpj);
	}

	public boolean isNovo() {
		return codigo == null;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public List<Auditoria> getAuditorias() {
		return auditorias;
	}

	public void setAuditorias(List<Auditoria> auditorias) {
		this.auditorias = auditorias;
	}

	public String getEmailGerente() {
		return emailGerente;
	}

	public void setEmailGerente(String emailGerente) {
		this.emailGerente = emailGerente;
	}

	public String getEmailSupervisor() {
		return emailSupervisor;
	}

	public void setEmailSupervisor(String emailSupervisor) {
		this.emailSupervisor = emailSupervisor;
	}

	public static String removerFormatacao(String cnpj) {
		return cnpj.replaceAll("\\.|-|/", "");
	}
}
