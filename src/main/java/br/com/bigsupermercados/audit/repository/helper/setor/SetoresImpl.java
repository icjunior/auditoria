package br.com.bigsupermercados.audit.repository.helper.setor;

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

import br.com.bigsupermercados.audit.model.Setor;
import br.com.bigsupermercados.audit.repository.filter.SetorFilter;
import br.com.bigsupermercados.audit.repository.paginacao.PaginacaoUtil;

public class SetoresImpl implements SetoresQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Setor> filtrar(SetorFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Setor.class);
		paginacaoUtil.preparar(criteria, pageable);
		filtrarAtivos(filtro, criteria);
		adicionarFiltro(filtro, criteria);
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	private Long total(SetorFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Setor.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}
	
	private void filtrarAtivos(SetorFilter filtro, Criteria criteria) {
		criteria.add(Restrictions.eq("ativo", true));
	}

	private void adicionarFiltro(SetorFilter filtro, Criteria criteria) {
		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Setor filtrarPorCodigo(Long codigoSetor) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Setor.class);
		criteria.add(Restrictions.eq("codigo", codigoSetor));
		Setor tipo = (Setor) criteria.list().stream().findFirst().get();
		Hibernate.initialize(tipo.getPerguntas());
		return tipo;
	}
}
