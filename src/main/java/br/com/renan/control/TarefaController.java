package br.com.renan.control;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.renan.dao.JdbcTarefaDao;
import br.com.renan.dao.JpaTarefaDao;
import br.com.renan.dao.TarefaDao;
import br.com.renan.model.Tarefa;

@Controller
@Transactional
public class TarefaController {

	@Autowired
	@Qualifier("jpaTarefaDao")
	TarefaDao dao;

	@RequestMapping("novaTarefa")
	public String form() {
		return "tarefa/formulario";
	}
	
	@RequestMapping("adicionaTarefa")
	public String adicionar(@Valid Tarefa tarefa, BindingResult result) {
		dao.adiciona(tarefa);
		if (result.hasErrors()) {
			return "tarefa/formulario";
		}
		return "redirect:listaTarefas";
	}

	@RequestMapping("listaTarefas")
	public String lista(Model model) {
		List<Tarefa> tarefas;
		tarefas = dao.lista();
		model.addAttribute("tarefas", tarefas);
		return "tarefa/lista";
	}

	@RequestMapping("removeTarefa")
	public String removeTarefa(Tarefa tarefa) {
		dao.remove(tarefa);
		return "redirect:listaTarefas";
	}

	@RequestMapping("mostraTarefa")
	public String mostraTarefa(Long id, Model model) {
		model.addAttribute("tarefa", dao.buscaPorId(id));
		return "tarefa/mostra";
	}

	@RequestMapping("alteraTarefa")
	public String altera(Tarefa tarefa) {
		dao.altera(tarefa);
		return "redirect:listaTarefas";
	}

	@RequestMapping("finalizaTarefa")
	public void finalizaAgora(Long id, HttpServletResponse response) {
		dao.finaliza(id);
		response.setStatus(200);
	}

}
