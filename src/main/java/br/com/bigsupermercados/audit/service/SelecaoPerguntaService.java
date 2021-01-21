package br.com.bigsupermercados.audit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bigsupermercados.audit.model.Auditoria;
import br.com.bigsupermercados.audit.model.Pergunta;
import br.com.bigsupermercados.audit.repository.Perguntas;

@Service
public class SelecaoPerguntaService {

	@Autowired
	private Perguntas repository;
	
	public List<Pergunta> perguntasPorAuditoria(Auditoria auditoria){
		return repository.perguntasPorAuditoria(auditoria);
	}
}
