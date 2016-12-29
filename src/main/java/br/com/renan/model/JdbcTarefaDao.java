package br.com.renan.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.mysql.jdbc.Driver;

public class JdbcTarefaDao {
	
	private Connection connection;

	public JdbcTarefaDao() throws SQLException{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		connection = DriverManager.getConnection("jdbc:mysql://localhost/tarefas","root","root");
	}

	public void adiciona(Tarefa tarefa) throws SQLException {
		
		PreparedStatement ps = connection
				.prepareStatement("insert into tarefa(descricao) values (?)");
		ps.setString(1, tarefa.getDescricao());
		//ps.setBoolean(2, tarefa.isFinalizando());
		//ps.setDate(2, (Date) tarefa.getDataFinalizacao().getTime());
		ps.execute();
	}
	
	public List lista() throws SQLException{
		List list = new ArrayList<Tarefa>();
		
		PreparedStatement ps = connection.prepareStatement("select * from tarefa");
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			Tarefa tarefa = new Tarefa();
			tarefa.setId(rs.getLong("id"));
			tarefa.setDescricao(rs.getString("descricao"));
			tarefa.setFinalizando(rs.getBoolean("finalizando"));
			Calendar cal = new GregorianCalendar();
			cal.setTime(rs.getDate("datafinalizacao"));
			tarefa.setDataFinalizacao(cal);
			list.add(tarefa);
		}
		return list;
	}
	

}
