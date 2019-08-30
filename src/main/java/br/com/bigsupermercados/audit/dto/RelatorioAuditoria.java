package br.com.bigsupermercados.audit.dto;

import java.time.LocalDate;

public class RelatorioAuditoria {

	private LocalDate dataInicio;

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}
}
