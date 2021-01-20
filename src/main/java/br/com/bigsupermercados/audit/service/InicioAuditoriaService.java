package br.com.bigsupermercados.audit.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bigsupermercados.audit.model.Auditoria;
import br.com.bigsupermercados.audit.repository.Auditorias;

@Service
public class InicioAuditoriaService {

	@Autowired
	private Auditorias repository;

	@Transactional
	public void marcarInicio(Auditoria auditoria) {
		if (auditoria.getDataHoraInicio() == null) {
			auditoria.setUsuario(UsuarioAutenticadoService.usuarioAutenticado());
			auditoria.setDataHoraInicio(LocalDateTime.now());
			repository.saveAndFlush(auditoria);
		}
	}
}
