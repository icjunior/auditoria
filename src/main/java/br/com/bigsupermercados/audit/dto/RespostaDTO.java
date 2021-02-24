package br.com.bigsupermercados.audit.dto;

import static java.nio.file.FileSystems.getDefault;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class RespostaDTO {

	private Long codigo;
	private String pergunta;
	private String comentario;
	private String foto;
	private List<String> fotos;
	private String contentType;
	private String setor;
	private InputStream arquivo;
	private List<InputStream> arquivos;

	public RespostaDTO(Long codigo, String pergunta, String comentario, String contentType, String setor) {
		super();
		this.codigo = codigo;
		this.pergunta = pergunta;
		this.comentario = comentario;
		this.contentType = contentType;
		this.setor = setor;
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

	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}

	public List<String> getFotos() {
		return fotos;
	}

	public void setFotos(List<String> fotos) {
		this.fotos = fotos;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public List<InputStream> getArquivos() {
		return arquivos;
	}

	public void setArquivos(List<InputStream> arquivos) {
		this.arquivos = arquivos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((setor == null) ? 0 : setor.hashCode());
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
		RespostaDTO other = (RespostaDTO) obj;
		if (setor == null) {
			if (other.setor != null)
				return false;
		} else if (!setor.equals(other.setor))
			return false;
		return true;
	}

	public InputStream getArquivo() {
		return arquivo;
	}

	public void setArquivo(InputStream arquivo) {
		this.arquivo = arquivo;
	}
}
