package br.com.bigsupermercados.audit.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bigsupermercados.audit.model.Tipo;
import br.com.bigsupermercados.audit.repository.Tipos;
import br.com.bigsupermercados.audit.service.exception.ImpossivelExcluirEntidadeException;
import br.com.bigsupermercados.audit.service.exception.RegistroJaCadastradoException;

@Service
public class CadastroTipoService {

	@Autowired
	private Tipos tipos;

	@Transactional
	public Tipo salvar(Tipo tipo) {
		Optional<Tipo> tipoOptional = tipos.findByNomeIgnoreCase(tipo.getNome());

		if (tipoOptional.isPresent() && tipo.isNovo()) {
			throw new RegistroJaCadastradoException("Tipo já cadastrado");
		}

		return tipos.saveAndFlush(tipo);
	}
	
	@Transactional
	public void excluir(Tipo tipo) {
		try {
			tipos.delete(tipo);
			tipos.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível apagar tipo. Já foi usada em algum momento");
		}
	}
}
