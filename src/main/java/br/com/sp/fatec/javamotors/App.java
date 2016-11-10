package br.com.sp.fatec.javamotors;

import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.UIManager;

import br.com.sp.fatec.javamotors.controller.MarcaController;
import br.com.sp.fatec.javamotors.dao.PersistenceManager;
import br.com.sp.fatec.javamotors.model.Marca;
import br.com.sp.fatec.javamotors.view.FramePrincipal;

public class App {
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
		em.close();
		PersistenceManager.INSTANCE.close();
		
		FramePrincipal frame = new FramePrincipal();
		frame.setVisible(true);
	}
}
