package br.com.renan.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.renan.model.Tarefa;

@Repository
public class JpaTarefaDao implements TarefaDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Tarefa buscaPorId(Long id) {
		return entityManager.find(Tarefa.class, id);
	}

	@Override
	public List<Tarefa> lista() {
		return entityManager.createQuery("select t from Tarefa t").getResultList();
	}

	@Override
	public void adiciona(Tarefa t) {
		entityManager.persist(t);
	}

	@Override
	public void altera(Tarefa t) {
		entityManager.merge(t);
	}

	@Override
	public void remove(Tarefa t) {
		Object managed = entityManager.merge(t);
		entityManager.remove(managed);
	}

	@Override
	public void finaliza(Long id) {
		Tarefa tarefa = buscaPorId(id);
		tarefa.setFinalizado(true);
		tarefa.setDataFinalizacao(Calendar.getInstance());
		entityManager.merge(tarefa);
	}

}
