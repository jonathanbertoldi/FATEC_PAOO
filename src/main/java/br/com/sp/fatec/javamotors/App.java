package br.com.sp.fatec.javamotors;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.sp.fatec.javamotors.controller.MarcaController;
import br.com.sp.fatec.javamotors.dao.PersistenceManager;
import br.com.sp.fatec.javamotors.model.Marca;

public class App {
	public static void main(String[] args) {
		EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
		em.close();
		PersistenceManager.INSTANCE.close();
		/*
		MarcaController controller = new MarcaController();
		
		List<Marca> lista = controller.index();
		
		for (Marca marca : lista) {
			System.out.println(marca.getId());
			System.out.println(marca.getNome());
			System.out.println(marca.getPais());
			System.out.println(marca.getLogo());
		}
		 */
	}
}
