package br.com.bigsupermercados.audit.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bigsupermercados.audit.model.Setor;
import br.com.bigsupermercados.audit.repository.Setores;
import br.com.bigsupermercados.audit.service.exception.ImpossivelExcluirEntidadeException;
import br.com.bigsupermercados.audit.service.exception.RegistroJaCadastradoException;

@Service
public class CadastroSetorService {

	@Autowired
	private Setores setores;

	@Transactional
	public Setor salvar(Setor setor) {
		Optional<Setor> setorOptional = setores.findByNomeIgnoreCaseAndTipo(setor.getNome(), setor.getTipo());

		if (setorOptional.isPresent() && setor.isNovo()) {
			throw new RegistroJaCadastradoException("Setor já cadastrado");
		}

		return setores.saveAndFlush(setor);
	}

	@Transactional
	public void excluir(Setor setor) {
		try {
//			setores.delete(setor);
			setor.setAtivo(false);
			setores.save(setor);
//			setores.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível apagar setor. Já foi usada em algum momento");
		}
	}
}
