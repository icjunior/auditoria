package br.com.bigsupermercados.audit.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bigsupermercados.audit.dto.AuditoriaDTO;
import br.com.bigsupermercados.audit.dto.RespostaDTO;
import br.com.bigsupermercados.audit.repository.Auditorias;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class RelatorioService {

	@Autowired
	private Auditorias auditorias;

	public byte[] gerarRelatorioPorAuditoria(Long codigoAuditoria) throws Exception {
		AuditoriaDTO auditoria = auditorias.cabecalhoAuditoria(codigoAuditoria);
		List<RespostaDTO> auditoriaList = auditorias.relatorioPorAuditoria(codigoAuditoria);

		auditoria.transformaData();

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");
		parametros.put("content-type", "application/pdf");
		parametros.put("Content-disposition", "inline; filename=fatura.pdf");
		parametros.put("auditoriaNome", auditoria.getNome());
		parametros.put("lojaNome", auditoria.getLoja());
		parametros.put("dataAuditoria", auditoria.getDataAuditoria());

		JRDataSource jrDataSource = new JRBeanCollectionDataSource(auditoriaList);

		InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/relatorio_auditoria.jasper");

		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, jrDataSource);
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} finally {

		}
	}
}
