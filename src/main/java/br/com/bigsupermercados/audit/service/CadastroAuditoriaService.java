package br.com.bigsupermercados.audit.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bigsupermercados.audit.model.Auditoria;
import br.com.bigsupermercados.audit.model.Resposta;
import br.com.bigsupermercados.audit.repository.Auditorias;
import br.com.bigsupermercados.audit.repository.Respostas;
import br.com.bigsupermercados.audit.service.exception.ImpossivelExcluirEntidadeException;
import br.com.bigsupermercados.audit.service.exception.RegistroJaCadastradoException;

@Service
public class CadastroAuditoriaService {

	@Autowired
	private Auditorias auditorias;

	@Autowired
	private Respostas respostas;

	@Autowired
	private CadastroFotoService cadastroFotoService;

	@Transactional
	public Auditoria salvar(Auditoria auditoria) {
		Optional<Auditoria> tipoOptional = auditorias.findByNomeIgnoreCase(auditoria.getNome());

		if (tipoOptional.isPresent()) {
			throw new RegistroJaCadastradoException("Auditoria já cadastrada");
		}

		return auditorias.saveAndFlush(auditoria);
	}

	@Transactional
	public Resposta salvarResposta(Resposta resposta) {
		if (resposta.getNota() <= 4 && resposta.getComentario() == "") {
			throw new RegistroJaCadastradoException(
					"Notas menores ou igual a 4 não podem ficar sem uma justificativa.");
		}

		resposta.converteFoto();
		
		respostas.saveAndFlush(resposta);
		cadastroFotoService.gravar(resposta);

		return resposta;
	}

	@Transactional
	public void excluir(Auditoria auditoria) {
		try {
			auditorias.delete(auditoria);
			auditorias.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível apagar auditoria. Já foi usada em algum momento");
		}
	}
}
