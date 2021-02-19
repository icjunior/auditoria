package br.com.bigsupermercados.audit.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
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

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

	private Boolean satisfatorio;

	@OneToMany(mappedBy = "resposta", fetch = FetchType.EAGER)
	private List<Foto> fotos;

	@Column(name = "content_type")
	private String contentType;

	@Column(name = "data_hora_inclusao")
	private LocalDateTime dataHoraInclusao = LocalDateTime.now();

	@Transient
	private String files;

	@Transient
	private List<String> arquivos;

	public Resposta() {

	}

	public Resposta(Pergunta pergunta, Auditoria auditoria) {
		super();
		this.pergunta = pergunta;
		this.auditoria = auditoria;
	}

	public void converteFoto() {

		String[] arquivos = this.files.split(",");

		List<String> arquivosList = new ArrayList<>();

		for (int i = 0; i < arquivos.length; i++) {
			arquivosList.add(arquivos[i]);
		}

		this.fotos = arquivosList.stream().map(Foto::new).collect(Collectors.toList());
	}

	public void populaFiles() {
		StringJoiner str = new StringJoiner(",");
		this.fotos.stream().forEach(foto -> {
			str.add(foto.getCaminho());
		});
		files = str.toString();
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
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}
}
