package br.com.bigsupermercados.audit.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bigsupermercados.audit.model.Auditoria;
import br.com.bigsupermercados.audit.repository.Auditorias;
import br.com.bigsupermercados.audit.service.exception.RegistroJaCadastradoException;

@Service
public class FechamentoAuditoriaService {

	@Autowired
	private Auditorias repository;

	@Autowired
	private SelecaoPerguntaService selecaoPerguntaService;

	@Autowired
	private SelecaoRespostaService selecaoRespostaService;

	@Transactional
	public void fechar(Auditoria auditoria) {

		Integer quantidadeRepostas = selecaoRespostaService.respostasPorAuditoria(auditoria).size();
		Integer quantidadePerguntas = selecaoPerguntaService.perguntasPorAuditoria(auditoria).size();

		if (!quantidadePerguntas.equals(quantidadeRepostas)) {
			throw new RegistroJaCadastradoException(
					"Existem perguntas sem respostas. Favor revise-as e tente finalizar novamente.");			
		}
		
		auditoria.setFinalizado(true);
		auditoria.setDataHoraFim(LocalDateTime.now());
		repository.saveAndFlush(auditoria);
	}
}
