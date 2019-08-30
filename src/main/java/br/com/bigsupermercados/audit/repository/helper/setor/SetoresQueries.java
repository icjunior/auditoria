package br.com.bigsupermercados.audit.repository.helper.setor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.bigsupermercados.audit.model.Setor;
import br.com.bigsupermercados.audit.repository.filter.SetorFilter;

public interface SetoresQueries {

	public Page<Setor> filtrar(SetorFilter filtro, Pageable pageable);

	public Setor filtrarPorCodigo(Long codigoSetor);
}
