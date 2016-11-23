package br.com.sp.fatec.javamotors.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.sp.fatec.javamotors.model.Versao;

public class VersaoDAO {
	
	@PersistenceContext
	private EntityManager manager;

	public VersaoDAO() {
		manager = PersistenceManager.INSTANCE.getEntityManager();
	}
	
	public void insert(Versao versao) {
		try {
			manager.getTransaction().begin();
			manager.persist(versao);
			manager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			manager.getTransaction().rollback();
		}
	}
	
	public void update(Versao versao) {
		try {
			manager.getTransaction().begin();
			manager.merge(versao);
			manager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			manager.getTransaction().rollback();
		}
	}
	
	public List<Versao> findAll() {
		TypedQuery<Versao> query = manager.createQuery("SELECT v FROM Versao v WHERE v.deletadoEm = null", Versao.class);
		return query.getResultList();
	}
	
	public Versao findById(long versaoId) {
		Versao versao = manager.find(Versao.class, versaoId);
		return versao;
	}
	
	public List<Versao> findByModelo(long versaoId) {
		TypedQuery<Versao> query = manager.createQuery("SELECT v FROM Versao v WHERE v.modelo.id = :modeloId", Versao.class);
		query.setParameter("modeloId", versaoId);
		return query.getResultList();
	}
	
}
