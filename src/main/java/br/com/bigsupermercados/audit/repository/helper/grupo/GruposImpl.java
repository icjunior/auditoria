package br.com.bigsupermercados.audit.repository.helper.grupo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import br.com.bigsupermercados.audit.model.Grupo;
import br.com.bigsupermercados.audit.model.Usuario;

public class GruposImpl implements GruposQueries {

	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Grupo> porUsuario(Usuario usuario) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Grupo.class);
		
		criteria.createAlias("itens", "i", JoinType.LEFT_OUTER_JOIN);
		
		criteria.add(Restrictions.eq("id.usuario.codigo", usuario.getCodigo()));
		
		return (List<Grupo>) criteria.list();
	}
}
