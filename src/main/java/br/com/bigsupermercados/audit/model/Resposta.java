package br.com.bigsupermercados.audit.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "resposta_auditoria")
public class Resposta {

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

//	private String foto;

	private Boolean satisfatorio;

	@OneToMany(mappedBy = "resposta", fetch = FetchType.EAGER)
	private List<Foto> fotos;

	@Column(name = "content_type")
	private String contentType;

	@Column(name = "data_hora_inclusao")
	private LocalDateTime dataHoraInclusao = LocalDateTime.now();

	@Transient
	private List<String> files;

	public Resposta() {

	}

	public Resposta(Pergunta pergunta, Auditoria auditoria) {
		super();
		this.pergunta = pergunta;
		this.auditoria = auditoria;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Pergunta getPergunta() {
		return pergunta;
	}

	public void setPergunta(Pergunta pergunta) {
		this.pergunta = pergunta;
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Integer getNota() {
		return nota;
	}

	public void setNota(Integer nota) {
		this.nota = nota;
	}

//	public String getFoto() {
//		return foto;
//	}
//
//	public void setFoto(String foto) {
//		this.foto = foto;
//	}

	public Boolean getSatisfatorio() {
		return satisfatorio;
	}

	public void setSatisfatorio(Boolean satisfatorio) {
		this.satisfatorio = satisfatorio;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public LocalDateTime getDataHoraInclusao() {
		return dataHoraInclusao;
	}

	public void setDataHoraInclusao(LocalDateTime dataHoraInclusao) {
		this.dataHoraInclusao = dataHoraInclusao;
	}

	public List<Foto> getFotos() {
		return fotos;
	}

	public void setFotos(List<Foto> fotos) {
		this.fotos = fotos;
	}

	public List<String> getFiles() {
		return files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}

	public void converteFoto() {
		this.fotos = files.stream().map(Foto::new).collect(Collectors.toList());
	}
}
