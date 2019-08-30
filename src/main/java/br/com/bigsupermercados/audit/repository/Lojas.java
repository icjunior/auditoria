package br.com.bigsupermercados.audit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bigsupermercados.audit.model.Loja;
import br.com.bigsupermercados.audit.repository.helper.loja.LojasQueries;

@Repository
public interface Lojas extends JpaRepository<Loja, Long>, LojasQueries {

	public Optional<Loja> findByNomeIgnoreCase(String nome);

	public Optional<Loja> findByCnpj(String cnpj);
}
