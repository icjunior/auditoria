package br.com.bigsupermercados.audit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.bigsupermercados.audit.model.Auditoria;
import br.com.bigsupermercados.audit.model.Resposta;
import br.com.bigsupermercados.audit.model.Setor;
import br.com.bigsupermercados.audit.repository.helper.resposta.RespostasQueries;

@Repository
public interface Respostas extends JpaRepository<Resposta, Long>, RespostasQueries {

	public Optional<Resposta> findByPerguntaCodigoAndAuditoriaCodigo(Long codigoPergunta, Long codigoAuditoria);

	public List<Resposta> findByAuditoria(Auditoria auditoria);

	@Query("SELECT r FROM Resposta r JOIN r.auditoria a JOIN a.tipo t JOIN t.setores s WHERE s = :setor AND a.codigo = :codigoAuditoria")
	public List<Resposta> respostasPorSetor(@Param("setor") Setor setor,
			@Param("codigoAuditoria") Long codigoAuditoria);
}
