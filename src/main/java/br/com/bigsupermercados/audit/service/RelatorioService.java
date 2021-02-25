package br.com.bigsupermercados.audit.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bigsupermercados.audit.dto.AuditoriaDTO;
import br.com.bigsupermercados.audit.dto.FotoRelatorioDTO;
import br.com.bigsupermercados.audit.dto.RespostaDTO;
import br.com.bigsupermercados.audit.model.Auditoria;
import br.com.bigsupermercados.audit.model.Pergunta;
import br.com.bigsupermercados.audit.repository.Auditorias;
import br.com.bigsupermercados.audit.repository.FotoRepository;
import br.com.bigsupermercados.audit.storage.FotoStorage;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;

@Service
public class RelatorioService {

	@Autowired
	private Auditorias auditorias;

	@Autowired
	private FotoStorage fotoStorage;

	@Autowired
	private SelecaoPerguntaService selecaoPerguntaService;

	@Autowired
	private SelecaoRespostaService selecaoRespostaService;

	@Autowired
	private FotoRepository fotoRepository;

	public byte[] gerarRelatorioPorAuditoria(Long codigoAuditoria) throws Exception {
		AuditoriaDTO auditoria = auditorias.cabecalhoAuditoria(codigoAuditoria);
		BigDecimal notaGeral = calculaNotaGeral(codigoAuditoria);
		BigDecimal notaGeralAnterior = calculaNotaGeralAnterior(codigoAuditoria);

		List<RespostaDTO> respostaDTO = auditorias.relatorioPorAuditoria(codigoAuditoria);

//		respostaDTO.forEach(resposta -> {
//			if (!resposta.getFoto().equals("")) {
//				resposta.setArquivo(
//						new ByteArrayInputStream(fotoStorage.recuperaFotoTemporariaRelatorio(resposta.getFoto())));
//			}
//		});

		respostaDTO.forEach(resposta -> {
			List<FotoRelatorioDTO> fotos = fotoRepository.findByResposta_Codigo(resposta.getCodigo());

			if (!fotos.isEmpty()) {
				List<InputStream> arquivos = new ArrayList<>();
				List<String> fotosStr = fotos.stream().map(f -> f.getCaminho()).collect(Collectors.toList());
				resposta.setFotos(fotosStr);

				resposta.getFotos().stream().forEach(foto -> {
					arquivos.add(new ByteArrayInputStream(fotoStorage.recuperaFotoTemporariaRelatorio(foto)));
				});

				resposta.setArquivo(arquivos);
			}
		});

		JRFileVirtualizer fileVirtualizer = new JRFileVirtualizer(30, "/temp");

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");
		parametros.put("auditoriaNome", auditoria.getNome());
		parametros.put("lojaNome", auditoria.getLoja());
		parametros.put("dataAuditoria", auditoria.getDataAuditoria());
		parametros.put("dataInicio", auditoria.getDataHoraInicio());
		parametros.put("dataFim", auditoria.getDataHoraFim());
		parametros.put("nomeAvaliador", auditoria.getNomeAvaliador());
		parametros.put("notaGeral", notaGeral);
		parametros.put("notaGeralAnterior", notaGeralAnterior);
		parametros.put("avaliacao", avaliarAuditoria(notaGeral, auditoria));
		parametros.put(JRParameter.REPORT_VIRTUALIZER, fileVirtualizer);

		JRDataSource jrDataSource = new JRBeanCollectionDataSource(respostaDTO);

		InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/relatorio_auditoria_novo.jasper");

		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, jrDataSource);
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} finally {

		}
	}

	private BigDecimal calculaNotaGeral(Long codigoAuditoria) {
		Auditoria auditoria = auditorias.findOne(codigoAuditoria);

		BigDecimal maximoPontos = new BigDecimal((selecaoPerguntaService.perguntasPorAuditoria(auditoria).size()) * 5);
		BigDecimal pontosAuditoria = selecaoRespostaService.respostasPorAuditoria(auditoria).stream().map(resposta -> {
			return new BigDecimal(resposta.getNota());
		}).reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal notaGeral = (pontosAuditoria.multiply(new BigDecimal(100))).divide(maximoPontos, 2,
				RoundingMode.FLOOR);

		return notaGeral;
	}

	private BigDecimal calculaNotaGeralAnterior(Long codigoAuditoria) {
		BigDecimal notaGeral = BigDecimal.ZERO;

		Auditoria auditoria = auditorias.findOne(codigoAuditoria);
		Optional<Auditoria> auditoriaAnterior = auditorias.findTop1ByCodigoNotAndTipoAndLojaOrderByDataHoraFimDesc(
				auditoria.getCodigo(), auditoria.getTipo(), auditoria.getLoja());

		if (auditoriaAnterior.isPresent()) {
			List<Pergunta> perguntasPorAuditoria = selecaoPerguntaService
					.perguntasPorAuditoria(auditoriaAnterior.get());

			BigDecimal maximoPontos = new BigDecimal((perguntasPorAuditoria.size()) * 5);
			BigDecimal pontosAuditoria = selecaoRespostaService.respostasPorAuditoria(auditoriaAnterior.get()).stream()
					.map(resposta -> {
						return new BigDecimal(resposta.getNota());
					}).reduce(BigDecimal.ZERO, BigDecimal::add);

			notaGeral = (pontosAuditoria.multiply(new BigDecimal(100))).divide(maximoPontos, 2, RoundingMode.FLOOR);
		}

		return notaGeral;
	}

	public String avaliarAuditoria(BigDecimal notaGeral, AuditoriaDTO auditoria) {
		if (notaGeral.compareTo(new BigDecimal(70)) == -1) {
			return "Apresentar plano de ação";
		}

		if (notaGeral.compareTo(new BigDecimal(85)) == -1) {
			return "Necessita de melhorias";
		}

		if (notaGeral.compareTo(new BigDecimal(95)) == -1) {
			return "Tem qualidade";
		} else {
			return "Excelente, parabéns";
		}
	}
}
