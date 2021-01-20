package br.com.bigsupermercados.audit.service;

import org.springframework.stereotype.Service;

import br.com.bigsupermercados.audit.model.Auditoria;
import br.com.bigsupermercados.audit.model.Usuario;

@Service
public class ComparaUsuarioService {

	public boolean desabilitaSalvarResposta(Auditoria auditoria) {
		Usuario usuarioLogado = UsuarioAutenticadoService.usuarioAutenticado();
		Usuario usuarioAuditoria = auditoria.getUsuario();
		
		return !usuarioAuditoria.equals(usuarioLogado);
	}

}
