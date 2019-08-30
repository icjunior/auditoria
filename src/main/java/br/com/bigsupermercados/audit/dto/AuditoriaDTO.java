package br.com.bigsupermercados.audit.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class AuditoriaDTO {

	private String nome;
	private String loja;
	private LocalDate data;
	private Date dataAuditoria;

	public AuditoriaDTO(String nome, String loja, LocalDate data) {
		super();
		this.nome = nome;
		this.loja = loja;
		this.data = data;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLoja() {
		return loja;
	}

	public void setLoja(String loja) {
		this.loja = loja;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Date getDataAuditoria() {
		return dataAuditoria;
	}

	public void setDataAuditoria(Date dataAuditoria) {
		this.dataAuditoria = dataAuditoria;
	}

	public void transformaData() {
		dataAuditoria = Date.from(LocalDateTime.of(data, LocalTime.of(0, 0, 0)).atZone(ZoneId.systemDefault()).toInstant());
	}
}