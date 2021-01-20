package br.com.bigsupermercados.audit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bigsupermercados.audit.repository.Perguntas;

@Service
public class SelecaoPerguntaService {

	@Autowired
	private Perguntas repository;
}
