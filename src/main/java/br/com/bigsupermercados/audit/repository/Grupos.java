package br.com.bigsupermercados.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bigsupermercados.audit.model.Grupo;
import br.com.bigsupermercados.audit.repository.helper.grupo.GruposQueries;

@Repository
public interface Grupos extends JpaRepository<Grupo, Long>, GruposQueries {

}
