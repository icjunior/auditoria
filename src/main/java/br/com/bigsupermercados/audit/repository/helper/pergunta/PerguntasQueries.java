package br.com.bigsupermercados.audit.repository.helper.pergunta;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.bigsupermercados.audit.dto.LancamentoAuditoriaPerguntaDTO;
import br.com.bigsupermercados.audit.model.Pergunta;
import br.com.bigsupermercados.audit.repository.filter.PerguntaFilter;

public interface PerguntasQueries {

	public Page<Pergunta> filtrar(PerguntaFilter filtro, Pageable pageable);
	
	public Pergunta filtrarPorCodigo(Long codigoPergunta);
	
	public List<LancamentoAuditoriaPerguntaDTO> buscarPerguntasPorAuditoria(Long codigoAuditoria, Long codigoSetor);
}
