package br.com.bigsupermercados.audit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bigsupermercados.audit.model.Foto;
import br.com.bigsupermercados.audit.model.Resposta;
import br.com.bigsupermercados.audit.repository.FotoRepository;

@Service
public class CadastroFotoService {

	@Autowired
	private FotoRepository repository;

	public void gravar(Resposta resposta) {
		resposta.converteFoto();
		resposta.getFotos().stream().forEach(foto -> foto.setResposta(resposta));

		List<Foto> fotos = resposta.getFotos();

		repository.save(fotos);
	}
}
