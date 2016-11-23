package br.com.sp.fatec.javamotors;

import javax.swing.UIManager;

import br.com.sp.fatec.javamotors.view.FramePrincipal;

public class App {
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		FramePrincipal frame = new FramePrincipal();
		frame.setVisible(true);
	}
}
