package br.com.bigsupermercados.audit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

	private Boolean satisfatorio;

	private String foto;

	@Column(name = "content_type")
	private String contentType;

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

	public Boolean getSatisfatorio() {
		return satisfatorio;
	}

	public void setSatisfatorio(Boolean satisfatorio) {
		this.satisfatorio = satisfatorio;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((auditoria == null) ? 0 : auditoria.hashCode());
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
		Resposta other = (Resposta) obj;
		if (auditoria == null) {
			if (other.auditoria != null)
				return false;
		} else if (!auditoria.equals(other.auditoria))
			return false;
		return true;
	}
}
