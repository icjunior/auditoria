package br.com.bigsupermercados.audit.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bigsupermercados.audit.model.Auditoria;
import br.com.bigsupermercados.audit.repository.Auditorias;

@Service
public class FechamentoAuditoriaService {

	@Autowired
	private Auditorias auditorias;

	@Transactional
	public void fechar(Auditoria auditoria) {
		auditoria.setFinalizado(true);
		auditoria.setDataHoraFim(LocalDateTime.now());
		auditorias.saveAndFlush(auditoria);
	}
}
