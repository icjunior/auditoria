package br.com.bigsupermercados.audit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bigsupermercados.audit.model.Auditoria;
import br.com.bigsupermercados.audit.model.Loja;
import br.com.bigsupermercados.audit.model.Tipo;
import br.com.bigsupermercados.audit.repository.helper.auditoria.AuditoriasQueries;

@Repository
public interface Auditorias extends JpaRepository<Auditoria, Long>, AuditoriasQueries {

	public Optional<Auditoria> findByNomeIgnoreCase(String nome);

	public Optional<Auditoria> findTop1ByCodigoNotAndTipoAndLojaOrderByDataHoraFimDesc(Long codigoAuditoria, Tipo tipo, Loja loja);
}
