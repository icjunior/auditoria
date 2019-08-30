package br.com.bigsupermercados.audit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "pergunta")
public class Pergunta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@NotBlank(message = "A pergunta não pdoe ser em branco")
	private String nome;

	@NotNull(message = "Um setor deve ser selecionado")
	@ManyToOne
	@JoinColumn(name = "setor_codigo")
	private Setor setor;

	@Column(name = "mostra_comentario")
	private Boolean mostraComentario;

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

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public Boolean getMostraComentario() {
		return mostraComentario;
	}

	public void setMostraComentario(Boolean mostraComentario) {
		this.mostraComentario = mostraComentario;
	}
}
