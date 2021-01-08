package br.com.bigsupermercados.audit.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.bigsupermercados.audit.controller.page.PageWrapper;
import br.com.bigsupermercados.audit.dto.EmailDTO;
import br.com.bigsupermercados.audit.dto.LancamentoAuditoriaPerguntaDTO;
import br.com.bigsupermercados.audit.mail.Mailer;
import br.com.bigsupermercados.audit.model.Auditoria;
import br.com.bigsupermercados.audit.model.Pergunta;
import br.com.bigsupermercados.audit.model.Resposta;
import br.com.bigsupermercados.audit.model.Setor;
import br.com.bigsupermercados.audit.model.Tipo;
import br.com.bigsupermercados.audit.repository.Auditorias;
import br.com.bigsupermercados.audit.repository.Lojas;
import br.com.bigsupermercados.audit.repository.Perguntas;
import br.com.bigsupermercados.audit.repository.Respostas;
import br.com.bigsupermercados.audit.repository.Setores;
import br.com.bigsupermercados.audit.repository.Tipos;
import br.com.bigsupermercados.audit.repository.filter.AuditoriaFilter;
import br.com.bigsupermercados.audit.service.CadastroAuditoriaService;
import br.com.bigsupermercados.audit.service.exception.ImpossivelExcluirEntidadeException;
import br.com.bigsupermercados.audit.service.exception.RegistroJaCadastradoException;

@Controller
@RequestMapping("/auditorias")
public class AuditoriaController {

	@Autowired
	private Auditorias auditorias;

	@Autowired
	private Lojas lojas;

	@Autowired
	private Tipos tipos;

	@Autowired
	private Setores setores;

	@Autowired
	private CadastroAuditoriaService cadastroAuditoriaService;

	@Autowired
	private Respostas respostas;

	@Autowired
	private Mailer mailer;

	@Autowired
	private Perguntas perguntas;

	@RequestMapping("/novo")
	public ModelAndView novo(Auditoria auditoria) {
		ModelAndView mv = new ModelAndView("auditoria/CadastroAuditoria");
		mv.addObject("lojas", lojas.findAll());
		mv.addObject("tipos", tipos.findAll());
		return mv;
	}

	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Auditoria auditoria, BindingResult result, Model model,
			RedirectAttributes attributes, HttpServletRequest httpServletRequest) {
		if (result.hasErrors()) {
			return novo(auditoria);
		}

		try {
			cadastroAuditoriaService.salvar(auditoria);
		} catch (RegistroJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(auditoria);
		}

		attributes.addFlashAttribute("mensagem", "Auditoria salva com sucesso");

		return new ModelAndView("redirect:/auditorias/novo");
	}

	@GetMapping
	public ModelAndView pesquisar(AuditoriaFilter auditoriasFilter, BindingResult result,
			@PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("auditoria/PesquisaAuditorias");

		PageWrapper<Auditoria> paginaWrapper = new PageWrapper<>(auditorias.filtrar(auditoriasFilter, pageable),
				httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	@GetMapping("/lancamento/{codigoAuditoria}")
	public ModelAndView lancar(@PathVariable Long codigoAuditoria) {
		Auditoria auditoria = auditorias.filtrarPorCodigo(codigoAuditoria);

		ModelAndView mv = new ModelAndView("auditoria/LancamentoAuditoria");
		mv.addObject("auditoria", auditoria);

		return mv;
	}

	@GetMapping("/lancamentoSetor/{codigoAuditoria}/{codigoTipo}")
	public ModelAndView lancarDetalhe(@PathVariable Long codigoAuditoria, @PathVariable Long codigoTipo) {
		Tipo tipo = tipos.filtrarPorCodigo(codigoTipo);

		ModelAndView mv = new ModelAndView("auditoria/LancamentoAuditoriaSetor");
		mv.addObject("tipo", tipo);
		mv.addObject("codigoAuditoria", codigoAuditoria);

		return mv;
	}

	@GetMapping("/lancamentoPergunta/{codigoAuditoria}/{codigoSetor}")
	public ModelAndView lancarPergunta(@PathVariable Long codigoAuditoria, @PathVariable Long codigoSetor) {
		Setor setor = setores.filtrarPorCodigo(codigoSetor);

		List<LancamentoAuditoriaPerguntaDTO> perguntasList = perguntas.pesquisarPorAuditoriaESetor(codigoAuditoria,
				codigoSetor);
		
		ModelAndView mv = new ModelAndView("auditoria/LancamentoAuditoriaPergunta");
		mv.addObject("setor", setor);
		mv.addObject("perguntas", perguntasList);
		mv.addObject("codigoAuditoria", codigoAuditoria);

		return mv;
	}

	@GetMapping("/lancamentoResposta/{codigoAuditoria}/{codigoPergunta}")
	public ModelAndView lancarResposta(@PathVariable("codigoAuditoria") Auditoria auditoria,
			@PathVariable("codigoPergunta") Pergunta pergunta) {

		ModelAndView mv = new ModelAndView("auditoria/LancamentoAuditoriaResposta");
		Optional<Resposta> respostaOptional = respostas.findByPerguntaCodigoAndAuditoriaCodigo(pergunta.getCodigo(),
				auditoria.getCodigo());

		Resposta resposta;

		if (respostaOptional.isPresent()) {
			resposta = respostaOptional.get();
		} else {
			resposta = new Resposta(pergunta, auditoria);
		}

		mv.addObject("respostaAuditoria", resposta);

		return mv;
	}

	@PostMapping("/lancamentoResposta")
	public ModelAndView salvarResposta(@Valid Resposta respostaAuditoria, BindingResult result, Model model,
			RedirectAttributes attributes, HttpServletRequest httpServletRequest) {

		try {
			cadastroAuditoriaService.salvarResposta(respostaAuditoria);
		} catch (RegistroJaCadastradoException e) {
			result.rejectValue("comentario", e.getMessage(), e.getMessage());
			return lancarResposta(respostaAuditoria.getAuditoria(), respostaAuditoria.getPergunta());
		}

		attributes.addFlashAttribute("mensagem",
				"Pergunta " + respostaAuditoria.getPergunta().getNome() + "respondida com sucesso.");

		return new ModelAndView(
				"redirect:/auditorias/lancamentoPergunta/" + respostaAuditoria.getAuditoria().getCodigo() + "/"
						+ respostaAuditoria.getPergunta().getSetor().getCodigo());
	}

	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Long codigo) {
		Auditoria auditoria = auditorias.getOne(codigo);
		try {
			cadastroAuditoriaService.excluir(auditoria);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

	@PostMapping(value = "/email", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> enviarEmail(@RequestBody EmailDTO email) {
		mailer.enviar(email);
		return ResponseEntity.ok().build();
	}
}