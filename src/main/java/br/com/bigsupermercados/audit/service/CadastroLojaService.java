package br.com.bigsupermercados.audit.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bigsupermercados.audit.model.Loja;
import br.com.bigsupermercados.audit.repository.Lojas;
import br.com.bigsupermercados.audit.service.exception.ImpossivelExcluirEntidadeException;
import br.com.bigsupermercados.audit.service.exception.RegistroJaCadastradoException;

@Service
public class CadastroLojaService {

	@Autowired
	private Lojas lojas;

	@Transactional
	public Loja salvar(Loja loja) {
		Optional<Loja> lojaOptional = lojas.findByCnpj(loja.getCnpj());

		if (lojaOptional.isPresent() && loja.isNovo()) {
			throw new RegistroJaCadastradoException("Nome da loja já cadastrado");
		}

		return lojas.saveAndFlush(loja);
	}
	
	@Transactional
	public void excluir(Loja loja) {
		try {
			lojas.delete(loja);
			lojas.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível apagar loja. Já foi usada em algum momento");
		}
	}
}
