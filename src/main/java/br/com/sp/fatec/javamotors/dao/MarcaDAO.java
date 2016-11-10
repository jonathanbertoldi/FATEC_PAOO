package br.com.sp.fatec.javamotors.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.sp.fatec.javamotors.model.Marca;

public class MarcaDAO {
	
	@PersistenceContext
	private EntityManager manager;
	
	public MarcaDAO() {
		manager = PersistenceManager.INSTANCE.getEntityManager();
	}
	
	public void insert(Marca marca) {
		manager.persist(marca);
	}
	
	public void update(Marca marca) {
		manager.merge(marca);
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
