package br.com.bigsupermercados.audit.repository.helper.auditoria;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.bigsupermercados.audit.dto.AuditoriaDTO;
import br.com.bigsupermercados.audit.dto.RespostaDTO;
import br.com.bigsupermercados.audit.model.Auditoria;
import br.com.bigsupermercados.audit.repository.filter.AuditoriaFilter;

public interface AuditoriasQueries {

	public Page<Auditoria> filtrar(AuditoriaFilter filtro, Pageable pageable);

	public Auditoria filtrarPorCodigo(Long codigoAuditoria);

	public List<RespostaDTO> relatorioPorAuditoria(Long codigoAuditoria);
	
	public AuditoriaDTO cabecalhoAuditoria(Long codigoAuditoria);
}
