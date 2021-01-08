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
import br.com.bigsupermercados.audit.model.Tipo;
import br.com.bigsupermercados.audit.repository.Tipos;
import br.com.bigsupermercados.audit.repository.filter.TipoFilter;
import br.com.bigsupermercados.audit.service.CadastroTipoService;
import br.com.bigsupermercados.audit.service.exception.ImpossivelExcluirEntidadeException;
import br.com.bigsupermercados.audit.service.exception.RegistroJaCadastradoException;

@Controller
@RequestMapping("/tipos")
public class TipoController {

	@Autowired
	private Tipos tipos;

	@Autowired
	private CadastroTipoService cadastroTipoService;

	@RequestMapping("/novo")
	public ModelAndView novo(Tipo tipo) {
		ModelAndView mv = new ModelAndView("tipo/CadastroTipo");
		return mv;
	}

	@PostMapping({ "/novo", "{\\+d}" })
	public ModelAndView cadastrar(@Valid Tipo tipo, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(tipo);
		}

		try {
			cadastroTipoService.salvar(tipo);
		} catch (RegistroJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(tipo);
		}

		attributes.addFlashAttribute("mensagem", "Tipo salvo com sucesso");

		return new ModelAndView("redirect:/tipos/novo");
	}

	@GetMapping
	public ModelAndView pesquisar(TipoFilter tiposFilter, BindingResult result,
			@PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("tipo/PesquisaTipos");

		PageWrapper<Tipo> paginaWrapper = new PageWrapper<>(tipos.filtrar(tiposFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Tipo tipo = tipos.getOne(codigo);
		
		ModelAndView mv = novo(tipo);
		mv.addObject("tipo", tipo);
		
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Long codigo) {
		Tipo tipo = tipos.getOne(codigo);
		try {
			cadastroTipoService.excluir(tipo);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
}
