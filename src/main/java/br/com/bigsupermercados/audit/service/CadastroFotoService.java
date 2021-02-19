package br.com.bigsupermercados.audit.service;

import java.util.List;
import java.util.Optional;

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
		resposta.getFotos().stream().forEach(foto -> foto.setResposta(resposta));

		List<Foto> fotos = resposta.getFotos();

		fotos.stream().forEach(foto -> {
			if (!foto.getCaminho().equals("")) {
				Optional<Foto> fotoOpt = repository.findByCaminhoAndResposta_Codigo(foto.getCaminho(),
						foto.getResposta().getCodigo());

				if (!fotoOpt.isPresent()) {
					repository.save(foto);
				}
			}
		});
	}
}
