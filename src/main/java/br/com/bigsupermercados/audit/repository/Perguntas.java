package br.com.bigsupermercados.audit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.bigsupermercados.audit.dto.LancamentoAuditoriaPerguntaDTO;
import br.com.bigsupermercados.audit.model.Auditoria;
import br.com.bigsupermercados.audit.model.Pergunta;
import br.com.bigsupermercados.audit.model.Setor;
import br.com.bigsupermercados.audit.repository.helper.pergunta.PerguntasQueries;

@Repository
public interface Perguntas extends JpaRepository<Pergunta, Long>, PerguntasQueries {

	public Optional<Pergunta> findByNomeIgnoreCaseAndSetor(String nome, Setor setor);

	public List<Pergunta> findByAtivo(boolean status);

	@Query(value = "select pergunta.codigo as \"codigoPergunta\", pergunta.nome as pergunta, resposta_auditoria.nota as avaliacao, "
			+ "auditoria.codigo as \"codigoAuditoria\", setor.codigo as \"codigoSetor\", setor.nome as setor "
			+ "from auditoria "
			+ "inner join tipo on auditoria.tipo_codigo = tipo.codigo "
			+ "inner join setor on tipo.codigo = setor.tipo_codigo "
			+ "inner join pergunta on setor.codigo = pergunta.setor_codigo "
			+ "left join resposta_auditoria on pergunta.codigo = resposta_auditoria.pergunta_codigo "
			+ "and auditoria.codigo = resposta_auditoria.auditoria_codigo "
			+ "where auditoria.codigo = :codigoAuditoria and setor.codigo = :codigoSetor "
			+ "and pergunta.ativo = true", nativeQuery = true)
	public List<LancamentoAuditoriaPerguntaDTO> pesquisarPorAuditoriaESetor(
			@Param("codigoAuditoria") Long codigoAuditoria, @Param("codigoSetor") Long codigoSetor);

	@Query("SELECT p FROM Auditoria a JOIN a.tipo tipo JOIN tipo.setores setor JOIN setor.perguntas p WHERE a = :auditoria AND a.versaoNova = true")
	public List<Pergunta> perguntasPorAuditoria(@Param("auditoria") Auditoria auditoria);

}