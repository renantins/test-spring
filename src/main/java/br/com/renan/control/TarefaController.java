package br.com.renan.control;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.renan.model.JdbcTarefaDao;
import br.com.renan.model.Tarefa;

@Controller
public class TarefaController {

	private JdbcTarefaDao dao;

	@Autowired
	public TarefaController(JdbcTarefaDao dao) {
		this.dao = dao;
	}

	@RequestMapping("novaTarefa")
	public String form() {
		return "tarefa/formulario";
	}

	@RequestMapping("adicionaTarefa")
	public String adicionar(@Valid Tarefa tarefa, BindingResult result) {
		try {
			dao.adiciona(tarefa);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		if (result.hasErrors()) {
			return "tarefa/formulario";
		}
		return "tarefa/adicionada";
	}

	@RequestMapping("listaTarefas")
	public String lista(Model model) {
		List<Tarefa> tarefas;
		try {
			tarefas = dao.lista();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		model.addAttribute("tarefas", tarefas);
		return "tarefa/lista";
	}

	@RequestMapping("removeTarefa")
	public String removeTarefa(Tarefa tarefa) {
		try {
			dao.remove(tarefa);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return "redirect:listaTarefas";
	}

	@RequestMapping("mostraTarefa")
	public String mostraTarefa(Long id, Model model) {
		try {
			model.addAttribute("tarefa", dao.buscaPorId(id));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return "tarefa/mostra";
	}

	@RequestMapping("alteraTarefa")
	public String altera(Tarefa tarefa) {
		try {
			dao.altera(tarefa);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return "redirect:listaTarefas";
	}

	@RequestMapping("finalizaTarefa")
	public void finalizaAgora(Long id, HttpServletResponse response) {
		try {
			dao.finaliza(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		response.setStatus(200);
	}

}
