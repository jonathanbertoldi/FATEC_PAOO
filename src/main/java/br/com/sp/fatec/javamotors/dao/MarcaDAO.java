package br.com.sp.fatec.javamotors.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import br.com.sp.fatec.javamotors.model.Marca;

public class MarcaDAO {
	
	@PersistenceContext
	private EntityManager manager;
	
	public MarcaDAO() {
		manager = PersistenceManager.INSTANCE.getEntityManager();
	}
	
	@Transactional
	public void insert(Marca marca) {
		try {
			manager.getTransaction().begin();
			manager.persist(marca);
			manager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			manager.getTransaction().rollback();
		}
		
	}
	
	@Transactional
	public void update(Marca marca) {
		try {
			manager.getTransaction().begin();
			manager.merge(marca);
			manager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			manager.getTransaction().rollback();
		}
	}
	
	public List<Marca> findAll() {
		TypedQuery<Marca> query = manager.createQuery("SELECT m FROM Marca m", Marca.class);
		return query.getResultList();
	}
	
	public Marca findById(long marcaId) {
		Marca marca = manager.find(Marca.class, marcaId);
		return marca;
	}
}
