package br.com.sp.fatec.javamotors.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.GridLayout;
import java.awt.Image;

public class FramePrincipal extends JFrame {

	private JPanel contentPane;
	private JPanel panelMenu;
	private JButton btnMarca;
	private JButton btnModelo;
	private JButton btnCarro;
	private JButton btnCliente;
	private JButton btnVersao;

	/**
	 * Create the frame.
	 */
	public FramePrincipal() {
		initialize();
		initListeners();
	}
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		panelMenu = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelMenu, GroupLayout.PREFERRED_SIZE, 404, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelMenu, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(183, Short.MAX_VALUE))
		);
		Image iconBmw = new ImageIcon(this.getClass().getResource("/bmw.png")).getImage();
		Image iconCarProfile = new ImageIcon(this.getClass().getResource("/car_profile.png")).getImage();
		
		btnCarro = new JButton("");
		Image iconCar = new ImageIcon(this.getClass().getResource("/car.png")).getImage();
		btnCarro.setIcon(new ImageIcon(iconCar));
		
		btnVersao = new JButton("");
		Image iconAutomotive = new ImageIcon(this.getClass().getResource("/automotive.png")).getImage();
		btnVersao.setIcon(new ImageIcon(iconAutomotive));
		
		panelMenu.setLayout(new GridLayout(0, 5, 0, 0));
		panelMenu.add(btnCarro);
		
		btnCliente = new JButton("");
		Image iconAccountCard = new ImageIcon(this.getClass().getResource("/account-card.png")).getImage();
		btnCliente.setIcon(new ImageIcon(iconAccountCard));
		panelMenu.add(btnCliente);
		
		btnMarca = new JButton("");
		btnMarca.setIcon(new ImageIcon(iconBmw));
		panelMenu.add(btnMarca);
		
		btnModelo = new JButton("");
		btnModelo.setIcon(new ImageIcon(iconCarProfile));
		panelMenu.add(btnModelo);
		panelMenu.add(btnVersao);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void initListeners() {
		btnMarca.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				FrameMarca frame = new FrameMarca();
				frame.setVisible(true);
			}
		});
		
		btnModelo.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				FrameModelo frame = new FrameModelo();
				frame.setVisible(true);
			}
		});
	}
}
