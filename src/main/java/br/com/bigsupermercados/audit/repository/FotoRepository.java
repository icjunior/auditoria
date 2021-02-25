package br.com.bigsupermercados.audit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bigsupermercados.audit.dto.FotoRelatorioDTO;
import br.com.bigsupermercados.audit.model.Foto;

public interface FotoRepository extends JpaRepository<Foto, Long> {

	Optional<Foto> findByCaminhoAndResposta_Codigo(String caminho, Long codigo);
	
	List<FotoRelatorioDTO> findByResposta_Codigo(Long codigo);

}
