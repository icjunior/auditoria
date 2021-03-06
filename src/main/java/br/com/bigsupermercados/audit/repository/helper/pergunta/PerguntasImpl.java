package br.com.bigsupermercados.audit.repository.helper.pergunta;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
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

import br.com.bigsupermercados.audit.dto.LancamentoAuditoriaPerguntaDTO;
import br.com.bigsupermercados.audit.model.Pergunta;
import br.com.bigsupermercados.audit.repository.filter.PerguntaFilter;
import br.com.bigsupermercados.audit.repository.paginacao.PaginacaoUtil;

public class PerguntasImpl implements PerguntasQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Pergunta> filtrar(PerguntaFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Pergunta.class);
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	private Long total(PerguntaFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Pergunta.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(PerguntaFilter filtro, Criteria criteria) {
		criteria.add(Restrictions.eq("ativo", true));

		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Pergunta filtrarPorCodigo(Long codigoPergunta) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Pergunta.class);
		criteria.add(Restrictions.eq("codigo", codigoPergunta));
		Pergunta pergunta = (Pergunta) criteria.list().stream().findFirst().get();
		return pergunta;
	}

	@Override
	@Transactional(readOnly = true)
	public List<LancamentoAuditoriaPerguntaDTO> buscarPerguntasPorAuditoria(Long codigoAuditoria, Long codigoSetor) {
		String jpql = "SELECT "
				+ "new br.com.bigsupermercados.audit.dto.LancamentoAuditoriaPerguntaDTO(perguntas.codigo, perguntas.nome, respostas.nota, auditoria.codigo, setores.codigo, setores.nome) "
				+ "FROM Auditoria auditoria "
				+ "JOIN auditoria.tipos tipos "
				+ "JOIN tipos.setores setores "
				+ "JOIN setores.perguntas perguntas "
				+ "LEFT JOIN auditoria.respostas respostas AND perguntas.respostas "
				+ "WHERE auditoria.codigo = :codigoAuditoria AND setores.codigo = (:codigoSetor)";

		return manager.createQuery(jpql, LancamentoAuditoriaPerguntaDTO.class)
				.setParameter("codigoAuditoria", codigoAuditoria).setParameter("codigoSetor", codigoSetor).getResultList();

	}
}
