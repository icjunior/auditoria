package br.com.bigsupermercados.audit.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bigsupermercados.audit.model.Auditoria;
import br.com.bigsupermercados.audit.repository.Auditorias;
import br.com.bigsupermercados.audit.repository.Perguntas;
import br.com.bigsupermercados.audit.repository.Respostas;
import br.com.bigsupermercados.audit.service.exception.RegistroJaCadastradoException;

@Service
public class FechamentoAuditoriaService {

	@Autowired
	private Auditorias auditorias;

	@Autowired
	private Respostas respostaRepository;

	@Autowired
	private Perguntas perguntaRepository;

	@Transactional
	public void fechar(Auditoria auditoria) {

		Integer quantidadeRepostas = respostaRepository.findByAuditoria(auditoria).size();
		Integer quantidadePerguntas = perguntaRepository.pesquisarPerguntasPorAuditoria(auditoria.getCodigo()).size();

		if (quantidadePerguntas.equals(quantidadeRepostas)) {
			auditoria.setFinalizado(true);
			auditoria.setDataHoraFim(LocalDateTime.now());
			auditorias.saveAndFlush(auditoria);
		}

		throw new RegistroJaCadastradoException(
				"Existem perguntas sem respostas. Favor revise-as e tente finalizar novamente.");
	}
}
