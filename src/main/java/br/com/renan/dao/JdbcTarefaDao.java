package br.com.renan.dao;

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

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Driver;

import br.com.renan.model.Tarefa;

@Repository
public class JdbcTarefaDao implements TarefaDao {

	private Connection connection;
	
	@Autowired
	public JdbcTarefaDao(DataSource dataSource) {

		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void adiciona(Tarefa tarefa) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			PreparedStatement ps = connection.prepareStatement("insert into tarefa(descricao,finalizado) values (?,?)");
			ps.setString(1, tarefa.getDescricao());
			ps.setBoolean(2, tarefa.isFinalizado());
			ps.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public List lista() {
		List list = new ArrayList<Tarefa>();

		PreparedStatement ps;
		try {
			ps = connection.prepareStatement("select * from tarefa");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Tarefa tarefa = popularTarefa(rs);
				list.add(tarefa);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
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

	@Override
	public void remove(Tarefa tarefa) {
		if (tarefa.getId() == null) {
			throw new IllegalStateException("id esta vazio");
		}

		PreparedStatement ps;
		try {
			ps = connection.prepareStatement("delete from tarefa where id = ?");
			ps.setLong(1, tarefa.getId());
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public Tarefa buscaPorId(Long id) {

		if (id == null) {
			throw new IllegalStateException("id invalido");
		}

		try {
			PreparedStatement ps;
			ps = connection.prepareStatement("select * from tarefa where id = ?");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return null;
	}

	@Override
	public void altera(Tarefa tarefa) {
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(
					"update tarefa set descricao = ?, finalizado = ?, datafinalizacao = ? where id = ?");
			ps.setString(1, tarefa.getDescricao());
			ps.setBoolean(2, tarefa.isFinalizado());
			ps.setDate(3, tarefa.getDataFinalizacao() != null ? new Date(tarefa.getDataFinalizacao().getTimeInMillis())
					: null);
			ps.setLong(4, tarefa.getId());
			ps.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void finaliza(Long id) {
		if (id == null) {
			throw new IllegalStateException("id invalido");
		}
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement("update tarefa set finalizado = ?, datafinalizacao = ? where id = ?");
			ps.setBoolean(1, true);
			ps.setDate(2, new Date(Calendar.getInstance().getTimeInMillis()));
			ps.setLong(3, id);
			ps.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

}
