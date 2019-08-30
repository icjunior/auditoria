package br.com.bigsupermercados.audit.repository.helper.tipo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.bigsupermercados.audit.model.Tipo;
import br.com.bigsupermercados.audit.repository.filter.TipoFilter;

public interface TiposQueries {

	public Page<Tipo> filtrar(TipoFilter filtro, Pageable pageable);
	
	public Tipo filtrarPorCodigo(Long codigoTipo);
}
