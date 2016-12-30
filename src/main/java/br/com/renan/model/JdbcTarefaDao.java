package br.com.renan.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.mysql.jdbc.Driver;

public class JdbcTarefaDao {

	private Connection connection;

	public JdbcTarefaDao() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		connection = DriverManager.getConnection("jdbc:mysql://localhost/tarefas", "root", "root");
	}

	public void adiciona(Tarefa tarefa) throws SQLException {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		PreparedStatement ps = connection
				.prepareStatement("insert into tarefa(descricao,finalizado) values (?,?)");
		ps.setString(1, tarefa.getDescricao());
		ps.setBoolean(2, tarefa.isFinalizado());
		ps.execute();
	}

	public List lista() throws SQLException {
		List list = new ArrayList<Tarefa>();

		PreparedStatement ps = connection.prepareStatement("select * from tarefa");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Tarefa tarefa = popularTarefa(rs);
			list.add(tarefa);
		}
		rs.close();
		ps.close();
		return list;
	}

	public Tarefa popularTarefa(ResultSet rs) throws SQLException {
		Tarefa tarefa = new Tarefa();
		tarefa.setId(rs.getLong("id"));
		tarefa.setDescricao(rs.getString("descricao"));
		tarefa.setFinalizado(rs.getBoolean("finalizado"));
		Date data = rs.getDate("datafinalizacao");
		if (data != null) {
			Calendar dataFinalizacao = Calendar.getInstance();
			dataFinalizacao.setTime(data);
			tarefa.setDataFinalizacao(dataFinalizacao);
		}
		return tarefa;
	}

}
