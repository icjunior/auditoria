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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.bigsupermercados.audit.controller.page.PageWrapper;
import br.com.bigsupermercados.audit.model.Pergunta;
import br.com.bigsupermercados.audit.repository.Perguntas;
import br.com.bigsupermercados.audit.repository.Setores;
import br.com.bigsupermercados.audit.repository.Tipos;
import br.com.bigsupermercados.audit.repository.filter.PerguntaFilter;
import br.com.bigsupermercados.audit.service.CadastroPerguntaService;
import br.com.bigsupermercados.audit.service.exception.ImpossivelExcluirEntidadeException;
import br.com.bigsupermercados.audit.service.exception.RegistroJaCadastradoException;

@Controller
@RequestMapping("/perguntas")
public class PerguntaController {

	@Autowired
	private Perguntas perguntas;
	@Autowired
	private Tipos tipos;
	@Autowired
	private Setores setores;

	@Autowired
	private CadastroPerguntaService cadastroPerguntaService;

	@RequestMapping("/novo")
	public ModelAndView novo(Pergunta pergunta) {
		ModelAndView mv = new ModelAndView("pergunta/CadastroPergunta");
		mv.addObject("tipos", tipos.findAll());
		mv.addObject("setores", setores.findAll());
		return mv;
	}

	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	@PostMapping({ "/novo", "{\\+d}" })
	public ModelAndView cadastrar(@Valid Pergunta pergunta, BindingResult result, Model model,
			RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(pergunta);
		}

		try {
			cadastroPerguntaService.salvar(pergunta);
		} catch (RegistroJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(pergunta);
		}

		attributes.addFlashAttribute("mensagem", "Pergunta salva com sucesso");

		return new ModelAndView("redirect:/perguntas/novo");
	}

	@GetMapping
	public ModelAndView pesquisar(PerguntaFilter perguntasFilter, BindingResult result,
			@PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("pergunta/PesquisaPerguntas");

		PageWrapper<Pergunta> paginaWrapper = new PageWrapper<>(perguntas.filtrar(perguntasFilter, pageable),
				httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Pergunta pergunta = perguntas.getOne(codigo);

		ModelAndView mv = novo(pergunta);
		mv.addObject("pergunta", pergunta);

		return mv;
	}

	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Long codigo) {
		Pergunta pergunta = perguntas.getOne(codigo);
		try {
			cadastroPerguntaService.excluir(pergunta);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

	@PutMapping("/inativar/{codigo}")
	public @ResponseBody ResponseEntity<?> inativar(@PathVariable("codigo") Long codigo) {
		Pergunta pergunta = perguntas.getOne(codigo);
		try {
			cadastroPerguntaService.inativar(pergunta);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.ok().build();
	}
}
