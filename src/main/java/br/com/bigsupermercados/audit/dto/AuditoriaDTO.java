package br.com.bigsupermercados.audit.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AuditoriaDTO {

	private String nome;
	private String loja;
	private Date dataAuditoria;
	private String dataHoraInicio;
	private String dataHoraFim;
	private String nomeAvaliador;

	public AuditoriaDTO(String nome, String loja, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim,
			String nomeAvaliador) {
		super();
		this.nome = nome;
		this.loja = loja;
		this.dataHoraInicio = dataHoraInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		this.dataHoraFim = dataHoraFim.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		this.nomeAvaliador = nomeAvaliador;
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

	public Date getDataAuditoria() {
		return dataAuditoria;
	}

	public void setDataAuditoria(Date dataAuditoria) {
		this.dataAuditoria = dataAuditoria;
	}

	public String getDataHoraInicio() {
		return dataHoraInicio;
	}

	public void setDataHoraInicio(String dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}

	public String getDataHoraFim() {
		return dataHoraFim;
	}

	public void setDataHoraFim(String dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
	}

	public String getNomeAvaliador() {
		return nomeAvaliador;
	}

	public void setNomeAvaliador(String nomeAvaliador) {
		this.nomeAvaliador = nomeAvaliador;
	}
}