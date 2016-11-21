package br.com.sp.fatec.javamotors.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.sp.fatec.javamotors.model.Modelo;

public class ModeloDAO {

	@PersistenceContext
	private EntityManager manager;

	public ModeloDAO() {
		manager = PersistenceManager.INSTANCE.getEntityManager();
	}
	
	public void insert(Modelo modelo) {
		try {
			manager.getTransaction().begin();
			System.out.println("Tá aqui DAO");
			manager.persist(modelo);
			manager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			manager.getTransaction().rollback();
		}
	}
	
	public void update(Modelo modelo) {
		try {
			manager.getTransaction().begin();
			manager.merge(modelo);
			manager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			manager.getTransaction().rollback();
		}
	}
	
	public List<Modelo> findAll() {
		TypedQuery<Modelo> query = manager.createQuery("SELECT m FROM Modelo m WHERE m.deletadoEm = null", Modelo.class);
		return query.getResultList();
	}
	
	public Modelo findById(long marcaId) {
		Modelo modelo = manager.find(Modelo.class, marcaId);
		return modelo;
	}
	
	public List<Modelo> findByMarca(long marcaId) {
		TypedQuery<Modelo> query = manager.createQuery("SELECT m FROM Modelo m WHERE m.marca.id = :marcaId", Modelo.class);
		query.setParameter("marcaId", marcaId);
		return query.getResultList();
	}
}
