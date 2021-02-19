package br.com.bigsupermercados.audit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bigsupermercados.audit.model.Tipo;
import br.com.bigsupermercados.audit.repository.helper.tipo.TiposQueries;

@Repository
public interface Tipos extends JpaRepository<Tipo, Long>, TiposQueries{

	public Optional<Tipo> findByNomeIgnoreCase(String nome);

}
