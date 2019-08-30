package br.com.bigsupermercados.audit.repository.helper.resposta;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.bigsupermercados.audit.dto.AnaliseRespostasDTO;

public class RespostasImpl implements RespostasQueries {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public AnaliseRespostasDTO analiseRespostas() {
		Optional<Long> optionalSatisfatorios = Optional.ofNullable(
				manager.createQuery("select count(*) from Resposta r WHERE r.satisfatorio = true", Long.class)
						.getSingleResult());

		Optional<Long> optionalInsatisfatorios = Optional.ofNullable(
				manager.createQuery("select count(*) from Resposta r WHERE r.satisfatorio = false", Long.class)
						.getSingleResult());

		AnaliseRespostasDTO analiseRespostas = new AnaliseRespostasDTO(optionalSatisfatorios.orElse(Long.valueOf("0")),
				optionalInsatisfatorios.orElse(Long.valueOf("0")));

		return analiseRespostas;
	}

}
