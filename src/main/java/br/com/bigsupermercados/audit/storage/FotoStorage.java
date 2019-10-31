package br.com.bigsupermercados.audit.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {

	public String salvarTemporariamente(MultipartFile[] files);

	public byte[] recuperaFotoTemporaria(String nome);

	public void salvar(String foto);

	public byte[] recuperar(String nome);

	byte[] recuperaFotoTemporariaRelatorio(String nome);
	
	public void gerarThumbnail(String foto);
}
