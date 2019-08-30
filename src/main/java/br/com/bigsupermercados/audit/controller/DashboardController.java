package br.com.bigsupermercados.audit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.bigsupermercados.audit.repository.Auditorias;
import br.com.bigsupermercados.audit.repository.Respostas;

@Controller
public class DashboardController {

	@Autowired
	private Auditorias auditorias;
	
	@Autowired
	private Respostas respostas;

	@GetMapping("/")
	public ModelAndView dashboard() {
		ModelAndView mv = new ModelAndView("Dashboard");
		mv.addObject("totalAuditorias", auditorias.count());
		mv.addObject("analiseRespostas", respostas.analiseRespostas());
		return mv;
	}

}