package br.com.bigsupermercados.audit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.bigsupermercados.audit.controller.page.PageWrapper;
import br.com.bigsupermercados.audit.model.Loja;
import br.com.bigsupermercados.audit.repository.Lojas;
import br.com.bigsupermercados.audit.repository.filter.LojaFilter;
import br.com.bigsupermercados.audit.service.CadastroLojaService;
import br.com.bigsupermercados.audit.service.exception.ImpossivelExcluirEntidadeException;
import br.com.bigsupermercados.audit.service.exception.RegistroJaCadastradoException;

@Controller
@RequestMapping("/lojas")
public class LojasController {

	@Autowired
	private Lojas lojas;

	@Autowired
	private CadastroLojaService cadastroLojaService;

	@RequestMapping("/novo")
	public ModelAndView novo(Loja loja) {
		ModelAndView mv = new ModelAndView("loja/CadastroLoja");
		return mv;
	}

	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView cadastrar(@Valid Loja loja, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(loja);
		}

		try {
			cadastroLojaService.salvar(loja);
		} catch (RegistroJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(loja);
		}

		attributes.addFlashAttribute("mensagem", "Loja salva com sucesso");

		return new ModelAndView("redirect:/lojas/novo");
	}

	@GetMapping
	public ModelAndView pesquisar(LojaFilter lojaFilter, BindingResult result,
			@PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("loja/PesquisaLojas");

		PageWrapper<Loja> paginaWrapper = new PageWrapper<>(lojas.filtrar(lojaFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Loja loja = lojas.getOne(codigo);

		ModelAndView mv = novo(loja);
		mv.addObject("loja", loja);

		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Long codigo) {
		Loja loja = lojas.getOne(codigo);
		try {
			cadastroLojaService.excluir(loja);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
}
