package br.com.bigsupermercados.audit.repository.helper.loja;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.bigsupermercados.audit.model.Loja;
import br.com.bigsupermercados.audit.repository.filter.LojaFilter;

public interface LojasQueries {

	public Page<Loja> filtrar(LojaFilter filtro, Pageable pageable);
}
