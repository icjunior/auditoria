package br.com.bigsupermercados.audit.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

//	@Column(name = "mostra_comentario")
//	private Boolean mostraComentario;

	@Column(name = "ativo", columnDefinition = "boolean default true")
	private boolean ativo = true;

	@OneToMany(mappedBy = "auditoria")
	private List<Resposta> respostas;

	@Column(name = "data_hora_inclusao")
	private LocalDateTime dataHoraInclusao = LocalDateTime.now();

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

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public List<Resposta> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<Resposta> respostas) {
		this.respostas = respostas;
	}

	public LocalDateTime getDataHoraInclusao() {
		return dataHoraInclusao;
	}

	public void setDataHoraInclusao(LocalDateTime dataHoraInclusao) {
		this.dataHoraInclusao = dataHoraInclusao;
	}
}
