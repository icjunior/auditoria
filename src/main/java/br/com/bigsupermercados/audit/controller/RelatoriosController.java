package br.com.bigsupermercados.audit.controller;

import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.bigsupermercados.audit.model.Auditoria;
import br.com.bigsupermercados.audit.repository.Auditorias;
import br.com.bigsupermercados.audit.service.RelatorioService;

@Controller
@RequestMapping("/relatorios")
public class RelatoriosController {

	@Autowired
	private RelatorioService relatorioService;

	@Autowired
	private Auditorias auditorias;

	@GetMapping("/auditoria")
	public ModelAndView auditoria() {
		ModelAndView mv = new ModelAndView("relatorio/RelatorioPorAuditoria");
		mv.addObject("auditorias", auditorias.findAll());
		mv.addObject(new Auditoria());
		return mv;
	}

	@PostMapping("/auditoria")
	public ResponseEntity<byte[]> gerarRelatorioAuditoria(Auditoria auditoria) throws Exception {
		byte[] relatorio = relatorioService.gerarRelatorioPorAuditoria(auditoria.getCodigo());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(relatorio);
	}
}
