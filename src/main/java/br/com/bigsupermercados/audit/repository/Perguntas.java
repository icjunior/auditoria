package br.com.bigsupermercados.audit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bigsupermercados.audit.model.Pergunta;
import br.com.bigsupermercados.audit.model.Setor;
import br.com.bigsupermercados.audit.repository.helper.pergunta.PerguntasQueries;

@Repository
public interface Perguntas extends JpaRepository<Pergunta, Long>, PerguntasQueries{

	public Optional<Pergunta> findByNomeIgnoreCaseAndSetor(String nome, Setor setor);
	
	public List<Pergunta> findByAtivo(boolean status);
}
