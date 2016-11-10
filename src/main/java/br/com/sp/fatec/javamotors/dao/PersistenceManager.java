package br.com.sp.fatec.javamotors.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public enum PersistenceManager {
	INSTANCE;
	
	private EntityManagerFactory emFactory;
	
	private PersistenceManager() {
		// nome da persistence unit no persistence.xml
		emFactory = Persistence.createEntityManagerFactory("jpa-javamotors");
	}
	
	public EntityManager getEntityManager() {
		return emFactory.createEntityManager();
	}
	
	public void close() {
		emFactory.close();
	}
}
