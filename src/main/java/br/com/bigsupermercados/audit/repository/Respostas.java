package br.com.bigsupermercados.audit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bigsupermercados.audit.model.Resposta;
import br.com.bigsupermercados.audit.repository.helper.resposta.RespostasQueries;

@Repository
public interface Respostas extends JpaRepository<Resposta, Long>, RespostasQueries {

	public Optional<Resposta> findByPerguntaCodigoAndAuditoriaCodigo(Long codigo, Long codigo2);
}
