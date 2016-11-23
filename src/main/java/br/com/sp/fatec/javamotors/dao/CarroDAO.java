package br.com.sp.fatec.javamotors.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import br.com.sp.fatec.javamotors.model.Carro;
import br.com.sp.fatec.javamotors.model.Status;

public class CarroDAO {
	
	@PersistenceContext
	private EntityManager manager;
	
	public CarroDAO() {
		manager = PersistenceManager.INSTANCE.getEntityManager();
	}
	
	@Transactional
	public void insert(Carro carro) {
		try {
			manager.getTransaction().begin();
			manager.persist(carro);
			manager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			manager.getTransaction().rollback();
		}
		
	}
	
	@Transactional
	public void update(Carro carro) {
		try {
			manager.getTransaction().begin();
			manager.merge(carro);
			manager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			manager.getTransaction().rollback();
		}
	}
	
	public List<Carro> findAll() {
		TypedQuery<Carro> query = manager.createQuery("SELECT c FROM Carro c", Carro.class);
		return query.getResultList();
	}
	
	public List<Carro> findByStatus(Status statusVenda) {
		TypedQuery<Carro> query = manager.createQuery("SELECT c FROM Carro c WHERE c.status = :status", Carro.class);
		query.setParameter("status", statusVenda);
		return query.getResultList();
	}
	
	public Carro findById(long carroId) {
		Carro carro = manager.find(Carro.class, carroId);
		return carro;
	}
}
