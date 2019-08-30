package br.com.bigsupermercados.audit.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bigsupermercados.audit.model.Pergunta;
import br.com.bigsupermercados.audit.repository.Perguntas;
import br.com.bigsupermercados.audit.service.exception.ImpossivelExcluirEntidadeException;
import br.com.bigsupermercados.audit.service.exception.RegistroJaCadastradoException;

@Service
public class CadastroPerguntaService {

	@Autowired
	private Perguntas perguntas;

	@Transactional
	public Pergunta salvar(Pergunta pergunta) {
		Optional<Pergunta> perguntaOptional = perguntas.findByNomeIgnoreCaseAndSetor(pergunta.getNome(),
				pergunta.getSetor());

		if (perguntaOptional.isPresent() && pergunta.isNovo()) {
			throw new RegistroJaCadastradoException("Pergunta já cadastrada");
		}

		return perguntas.saveAndFlush(pergunta);
	}
	
	@Transactional
	public void excluir(Pergunta pergunta) {
		try {
			perguntas.delete(pergunta);
			perguntas.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível apagar pergunta. Já foi usada em algum momento");
		}
	}
}
