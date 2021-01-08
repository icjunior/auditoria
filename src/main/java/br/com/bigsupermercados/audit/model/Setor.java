package br.com.bigsupermercados.audit.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "setor")
public class Setor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@NotBlank(message = "Nome do setor não pode ser em branco")
	@Size(max = 100)
	private String nome;

	@NotNull(message = "O tipo deve ser informado")
	@ManyToOne
	@JoinColumn(name = "tipo_codigo")
	private Tipo tipo;

	@OneToMany(mappedBy = "setor", fetch = FetchType.EAGER)
	private List<Pergunta> perguntas;
	
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

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public List<Pergunta> getPerguntas() {
		return perguntas;
	}

	public void setPerguntas(List<Pergunta> perguntas) {
		this.perguntas = perguntas;
	}
	
	public Integer getQuantidadePerguntas() {
		return this.perguntas.size();
	}
}
