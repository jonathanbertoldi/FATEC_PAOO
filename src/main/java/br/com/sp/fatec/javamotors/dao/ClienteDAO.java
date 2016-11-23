package br.com.sp.fatec.javamotors.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import br.com.sp.fatec.javamotors.model.Cliente;

public class ClienteDAO {
	@PersistenceContext
	private EntityManager manager;
	
	public ClienteDAO() {
		manager = PersistenceManager.INSTANCE.getEntityManager();
	}
	
	@Transactional
	public void insert(Cliente cliente) {
		try {
			manager.getTransaction().begin();
			manager.persist(cliente);
			manager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			manager.getTransaction().rollback();
		}
		
	}
	
	@Transactional
	public void update(Cliente cliente) {
		try {
			manager.getTransaction().begin();
			manager.merge(cliente);
			manager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			manager.getTransaction().rollback();
		}
	}
	
	public List<Cliente> findAll() {
		TypedQuery<Cliente> query = manager.createQuery("SELECT c FROM Cliente c WHERE c.deletadoEm = null", Cliente.class);
		return query.getResultList();
	}
	
	public Cliente findById(long clienteId) {
		Cliente cliente = manager.find(Cliente.class, clienteId);
		return cliente;
	}
}
