package br.com.bigsupermercados.audit.dto;

import static java.nio.file.FileSystems.getDefault;

import java.io.File;
import java.io.IOException;

public class RespostaDTO {

	private String pergunta;
	private boolean satisfatorio;
	private String comentario;
	private String foto;
	private String contentType;

	public RespostaDTO(String pergunta, boolean satisfatorio, String comentario, String foto, String contentType) {
		super();
		this.pergunta = pergunta;
		this.satisfatorio = satisfatorio;
		this.comentario = comentario;
		this.foto = foto;
		this.contentType = contentType;

		validaFoto(this.foto);
	}

	private void validaFoto(String foto) {
		String caminho = null;
		try {
			caminho = new File(getDefault().getPath(System.getenv("HOME"), ".brewerfotos").toString())
					.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (foto == null || foto.equals("")) {
			this.foto = caminho + "\\temp\\sem_imagem.jpg";
		} else {
			this.foto = caminho + "\\temp\\" + foto;
		}
	}

	public String getPergunta() {
		return pergunta;
	}

	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}

	public boolean getSatisfatorio() {
		return satisfatorio;
	}

	public void setSatisfatorio(boolean satisfatorio) {
		this.satisfatorio = satisfatorio;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		validaFoto(foto);
		this.foto = foto;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
