package br.com.bigsupermercados.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bigsupermercados.audit.model.Foto;

public interface FotoRepository extends JpaRepository<Foto, Long> {

}
