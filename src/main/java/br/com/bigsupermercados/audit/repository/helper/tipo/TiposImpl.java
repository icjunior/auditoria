package br.com.bigsupermercados.audit.repository.helper.tipo;

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

import br.com.bigsupermercados.audit.model.Tipo;
import br.com.bigsupermercados.audit.repository.filter.TipoFilter;
import br.com.bigsupermercados.audit.repository.paginacao.PaginacaoUtil;

public class TiposImpl implements TiposQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Tipo> filtrar(TipoFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Tipo.class);
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	private Long total(TipoFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Tipo.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(TipoFilter filtro, Criteria criteria) {
		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Tipo filtrarPorCodigo(Long codigoTipo) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Tipo.class);
		criteria.add(Restrictions.eq("codigo", codigoTipo));
		Tipo tipo = (Tipo) criteria.list().stream().findFirst().get();
		Hibernate.initialize(tipo.getSetores());
		return tipo;
	}
}
