package br.com.bigsupermercados.audit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bigsupermercados.audit.model.Auditoria;
import br.com.bigsupermercados.audit.model.Resposta;
import br.com.bigsupermercados.audit.model.Setor;
import br.com.bigsupermercados.audit.repository.Respostas;

@Service
public class SelecaoRespostaService {

	@Autowired
	private Respostas repository;

	public List<Resposta> respostasPorAuditoria(Auditoria auditoria) {
		return repository.findByAuditoria(auditoria);
	}
	
	public List<Resposta> respostasPorSetor(Setor setor, Long codigoAuditoria) {
		return repository.respostasPorSetor(setor, codigoAuditoria);
	}
}
