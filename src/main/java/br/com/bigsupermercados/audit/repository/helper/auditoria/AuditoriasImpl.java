package br.com.bigsupermercados.audit.repository.helper.auditoria;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.bigsupermercados.audit.dto.AuditoriaDTO;
import br.com.bigsupermercados.audit.dto.RespostaDTO;
import br.com.bigsupermercados.audit.model.Auditoria;
import br.com.bigsupermercados.audit.repository.filter.AuditoriaFilter;
import br.com.bigsupermercados.audit.repository.paginacao.PaginacaoUtil;

public class AuditoriasImpl implements AuditoriasQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Auditoria> filtrar(AuditoriaFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Auditoria.class);
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	private Long total(AuditoriaFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Auditoria.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(AuditoriaFilter filtro, Criteria criteria) {
		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Auditoria filtrarPorCodigo(Long codigoAuditoria) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Auditoria.class);
		criteria.add(Restrictions.eq("codigo", codigoAuditoria));
		Auditoria auditoria = (Auditoria) criteria.list().stream().findFirst().get();
		Hibernate.initialize(auditoria.getTipos());
		return auditoria;
	}

	@Override
	public List<RespostaDTO> relatorioPorAuditoria(Long codigoAuditoria) {
		String jpql = "SELECT "
				+ "new br.com.bigsupermercados.audit.dto.RespostaDTO(r.pergunta.nome, r.satisfatorio, r.comentario, r.foto, r.contentType, r.pergunta.setor.nome) "
				+ "from Resposta r " + "WHERE r.auditoria.codigo = :codigoAuditoria "
				+ "ORDER BY r.pergunta.setor.nome";

		List<RespostaDTO> itensFiltrados = manager.createQuery(jpql, RespostaDTO.class)
				.setParameter("codigoAuditoria", codigoAuditoria).getResultList();

		return itensFiltrados;
	}

	@Override
	public AuditoriaDTO cabecalhoAuditoria(Long codigoAuditoria) {
		String jpql = "SELECT " + "new br.com.bigsupermercados.audit.dto.AuditoriaDTO(a.nome, a.loja.apelido, a.data) "
				+ "from Auditoria a WHERE a.codigo = :codigoAuditoria";

		return manager.createQuery(jpql, AuditoriaDTO.class).setParameter("codigoAuditoria", codigoAuditoria)
				.getSingleResult();
	}
}
