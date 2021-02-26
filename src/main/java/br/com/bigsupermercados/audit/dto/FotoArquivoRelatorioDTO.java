package br.com.bigsupermercados.audit.dto;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class FotoArquivoRelatorioDTO {

	private InputStream nome;

	public FotoArquivoRelatorioDTO() {
		
	}
	
	public FotoArquivoRelatorioDTO(ByteArrayInputStream nome) {
		this.nome = nome;
	}

	public InputStream getNome() {
		return nome;
	}

	public void setArquivo(InputStream nome) {
		this.nome = nome;
	}

}
