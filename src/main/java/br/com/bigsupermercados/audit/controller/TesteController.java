package br.com.bigsupermercados.audit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bigsupermercados.audit.model.Auditoria;
import br.com.bigsupermercados.audit.service.FechamentoAuditoriaService;

@RestController
@RequestMapping("/teste")
public class TesteController {

	@Autowired
	private FechamentoAuditoriaService fechamentoAuditoriaService;
	
	@PutMapping("/fechar/{auditoria}")
	public ResponseEntity<?> fechar(@PathVariable("auditoria") Auditoria auditoria) {
		fechamentoAuditoriaService.fechar(auditoria);
		return ResponseEntity.ok("Finalizado");
	}
}
