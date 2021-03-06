package br.com.bigsupermercados.audit.dto;

import java.math.BigDecimal;

public interface LancamentoAuditoriaPerguntaDTO {

	Long getCodigoPergunta();

	String getPergunta();

	Integer getAvaliacao();

	Long getCodigoAuditoria();

	Long getCodigoSetor();

	String getSetor();
	
	BigDecimal getNota();
}
