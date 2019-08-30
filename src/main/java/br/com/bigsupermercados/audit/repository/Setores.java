package br.com.bigsupermercados.audit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bigsupermercados.audit.model.Setor;
import br.com.bigsupermercados.audit.model.Tipo;
import br.com.bigsupermercados.audit.repository.helper.setor.SetoresQueries;

@Repository
public interface Setores extends JpaRepository<Setor, Long>, SetoresQueries {

	public Optional<Setor> findByNomeIgnoreCaseAndTipo(String nome, Tipo tipo);
}
