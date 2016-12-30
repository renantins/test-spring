package br.com.renan.control;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.renan.model.JdbcTarefaDao;
import br.com.renan.model.Tarefa;

@Controller
public class TarefaController {

	@RequestMapping("novaTarefa")
	public String form() {
		return "tarefa/formulario";
	}

	@RequestMapping("adicionaTarefa")
	public String adicionar(@Valid Tarefa tarefa, BindingResult result) throws SQLException {
		JdbcTarefaDao dao = new JdbcTarefaDao();
		dao.adiciona(tarefa);
		if (result.hasErrors()) {
			return "tarefa/formulario";
		}
		return "tarefa/adicionada";
	}
	
	@RequestMapping("listaTarefas")
	public String lista(Model model) throws SQLException {
	  JdbcTarefaDao dao = new JdbcTarefaDao();
	  List<Tarefa> tarefas = dao.lista(); 
	  model.addAttribute("tarefas", tarefas);
	  return "tarefa/lista";
	}
	
	@RequestMapping("removeTarefa")
	public String removeTarefa(Tarefa tarefa) throws SQLException{
		JdbcTarefaDao dao = new JdbcTarefaDao();
		dao.remove(tarefa);
		return "redirect:listaTarefas";
	}
	
	@RequestMapping("mostraTarefa")
	public String mostraTarefa(Long id, Model model) throws SQLException{
		JdbcTarefaDao dao = new JdbcTarefaDao();
		model.addAttribute("tarefa",dao.buscaPorId(id));
		return "tarefa/mostra";
	}
	
	@RequestMapping("alteraTarefa")
	public String altera(Tarefa tarefa) throws SQLException{
		JdbcTarefaDao dao = new JdbcTarefaDao();
		dao.altera(tarefa);
		return "redirect:listaTarefas";
	}
	
	
	@RequestMapping("finalizaTarefa")
	public void finalizaAgora(Long id, HttpServletResponse response) throws SQLException{
		JdbcTarefaDao dao = new JdbcTarefaDao();
		dao.finaliza(id);
		response.setStatus(200);
	}

}
