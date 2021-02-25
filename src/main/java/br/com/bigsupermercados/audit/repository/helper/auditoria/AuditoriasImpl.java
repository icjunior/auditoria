package br.com.bigsupermercados.audit.repository.helper.auditoria;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.bigsupermercados.audit.dto.AuditoriaDTO;
import br.com.bigsupermercados.audit.dto.RespostaDTO;
import br.com.bigsupermercados.audit.model.Auditoria;
import br.com.bigsupermercados.audit.model.Grupo;
import br.com.bigsupermercados.audit.model.Usuario;
import br.com.bigsupermercados.audit.repository.filter.AuditoriaFilter;
import br.com.bigsupermercados.audit.repository.paginacao.PaginacaoUtil;
import br.com.bigsupermercados.audit.security.UsuarioSistema;
import br.com.bigsupermercados.audit.service.SelecaoPerguntaService;
import br.com.bigsupermercados.audit.service.SelecaoRespostaService;

public class AuditoriasImpl implements AuditoriasQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	PaginacaoUtil paginacaoUtil;

	@Autowired
	private SelecaoPerguntaService selecaoPerguntaService;

	@Autowired
	private SelecaoRespostaService selecaoRespostaService;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Auditoria> filtrar(AuditoriaFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Auditoria.class);
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);
		// adicionarFiltroVersaoNova(criteria);
		Page<Auditoria> registros = new PageImpl<>(criteria.list(), pageable, total(filtro));

		registros.forEach(auditoria -> {
			BigDecimal maximoPontos = new BigDecimal(
					(selecaoPerguntaService.perguntasPorAuditoria(auditoria).size()) * 5);

			BigDecimal pontosAuditoria = selecaoRespostaService.respostasPorAuditoria(auditoria).stream()
					.map(resposta -> {
						return new BigDecimal(resposta.getNota());
					}).reduce(BigDecimal.ZERO, BigDecimal::add);

			BigDecimal notaGeral = (pontosAuditoria.multiply(new BigDecimal(100)))
					.divide(maximoPontos.equals(BigDecimal.ZERO) ? BigDecimal.ONE : maximoPontos, 2, RoundingMode.FLOOR);

			auditoria.setNotaTotal(notaGeral);
		});

		return registros;
	}

	private Long total(AuditoriaFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Auditoria.class);
		adicionarFiltro(filtro, criteria);
//		adicionarFiltroVersaoNova(criteria);
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

//	private void adicionarFiltroVersaoNova(Criteria criteria) {
//		criteria.add(Restrictions.eq("versaoNova", true));
//	}

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
				+ "new br.com.bigsupermercados.audit.dto.RespostaDTO(r.codigo, r.pergunta.nome, r.comentario, r.pergunta.setor.nome, r.nota) "
				+ "from Resposta r " + "WHERE r.auditoria.codigo = :codigoAuditoria "
				+ "ORDER BY r.pergunta.setor.nome";

		List<RespostaDTO> itensFiltrados = manager.createQuery(jpql, RespostaDTO.class)
				.setParameter("codigoAuditoria", codigoAuditoria).getResultList();

		return itensFiltrados;
	}

	@Override
	public AuditoriaDTO cabecalhoAuditoria(Long codigoAuditoria) {
		String jpql = "SELECT "
				+ "new br.com.bigsupermercados.audit.dto.AuditoriaDTO(a.nome, a.loja.apelido, a.dataHoraInicio, a.dataHoraFim, u.nome, l.notaMinima) "
				+ "from Auditoria a Join a.loja l Join a.usuario u WHERE a.codigo = :codigoAuditoria";

		return manager.createQuery(jpql, AuditoriaDTO.class).setParameter("codigoAuditoria", codigoAuditoria)
				.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Auditoria> auditoriasPorPerfil() {
		UsuarioSistema usuarioLogado = (UsuarioSistema) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Usuario usuario = usuarioLogado.getUsuario();

		Criteria criteria = manager.unwrap(Session.class).createCriteria(Auditoria.class);

		if (!usuario.getGrupos().contains(new Grupo(1L, "Administrador"))) {
			criteria.add(Restrictions.eq("loja.codigo", usuario.getLoja().getCodigo()));
		}
		
		criteria.add(Restrictions.eq("finalizado", true));

		return criteria.list();
	}
}
