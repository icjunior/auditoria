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
import br.com.bigsupermercados.audit.model.Setor;
import br.com.bigsupermercados.audit.repository.Setores;
import br.com.bigsupermercados.audit.repository.Tipos;
import br.com.bigsupermercados.audit.repository.filter.SetorFilter;
import br.com.bigsupermercados.audit.service.CadastroSetorService;
import br.com.bigsupermercados.audit.service.exception.ImpossivelExcluirEntidadeException;
import br.com.bigsupermercados.audit.service.exception.RegistroJaCadastradoException;

@Controller
@RequestMapping("/setores")
public class SetorController {

	@Autowired
	private Setores setores;

	@Autowired
	private Tipos tipos;

	@Autowired
	private CadastroSetorService cadastroSetorService;

	@RequestMapping("/novo")
	public ModelAndView novo(Setor setor) {
		ModelAndView mv = new ModelAndView("setor/CadastroSetor");
		mv.addObject("tipos", tipos.findAll());
		return mv;
	}

	@PostMapping({"/novo", "{\\+d}"})
	public ModelAndView cadastrar(@Valid Setor setor, BindingResult result, Model model,
			RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(setor);
		}

		try {

		} catch (RegistroJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(setor);
		}
		cadastroSetorService.salvar(setor);
		attributes.addFlashAttribute("mensagem", "Setor salvo com sucesso");

		return new ModelAndView("redirect:/setores/novo");
	}

	@GetMapping
	public ModelAndView pesquisar(SetorFilter setoresFilter, BindingResult result,
			@PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("setor/PesquisaSetores");

		PageWrapper<Setor> paginaWrapper = new PageWrapper<>(setores.filtrar(setoresFilter, pageable),
				httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Setor setor = setores.findOne(codigo);
		
		ModelAndView mv = novo(setor);
		mv.addObject("setor", setor);
		
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Long codigo) {
		Setor setor = setores.findOne(codigo);
		try {
			cadastroSetorService.excluir(setor);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
}
