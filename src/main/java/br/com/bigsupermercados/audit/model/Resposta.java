package br.com.bigsupermercados.audit.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "resposta_auditoria")
public class Resposta {

	public Resposta() {

	}

	public Resposta(Pergunta pergunta, Auditoria auditoria) {
		super();
		this.pergunta = pergunta;
		this.auditoria = auditoria;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@ManyToOne
	@JoinColumn(name = "pergunta_codigo")
	private Pergunta pergunta;

	@ManyToOne
	@JoinColumn(name = "auditoria_codigo")
	private Auditoria auditoria;

	private String comentario;

	private Integer nota = 0;

	private String foto;

	private Boolean satisfatorio;

	@Column(name = "content_type")
	private String contentType;

	@Column(name = "data_hora_inclusao")
	private LocalDateTime dataHoraInclusao = LocalDateTime.now();
}
