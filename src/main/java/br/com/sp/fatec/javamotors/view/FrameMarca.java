package br.com.sp.fatec.javamotors.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.GridBagLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;

public class FrameMarca extends JDialog {

	private JPanel contentPane;
	private JPanel panelTable;
	private JPanel panelDados;
	private JPanel panelBotoes;
	private JButton btnNovo;
	private JButton btnLimpar;
	private JButton btnExcluir;
	private JButton btnSalvar;
	private JPanel panelFormulario;
	private JLabel lblId;
	private JTextField txtId;
	private JLabel lblNome;
	private JTextField txtNome;
	private JLabel lblPaisDeOrigem;
	private JTextField txtPaisDeOrigem;
	private JLabel lblLogo;
	private JLabel lblPiclogo;
	private JPanel panelBotoesImagem;
	private JButton btnAddimagem;
	private JButton btnRmimagem;
	private JScrollPane scrollPane;

	/**
	 * Create the frame.
	 */
	public FrameMarca() {
		initialize();
	}
	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 513);
		setLocationRelativeTo(null);
		setModal(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 2, 0, 0));
		
		panelDados = new JPanel();
		panelDados.setBorder(new TitledBorder(null, "Dados da Marca", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panelDados);
		
		panelBotoes = new JPanel();
		
		panelFormulario = new JPanel();
		panelFormulario.setBorder(new LineBorder(SystemColor.scrollbar));
		GroupLayout gl_panelDados = new GroupLayout(panelDados);
		gl_panelDados.setHorizontalGroup(
			gl_panelDados.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panelDados.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelDados.createParallelGroup(Alignment.LEADING)
						.addComponent(panelFormulario, GroupLayout.PREFERRED_SIZE, 308, Short.MAX_VALUE)
						.addComponent(panelBotoes, GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panelDados.setVerticalGroup(
			gl_panelDados.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDados.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelBotoes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelFormulario, GroupLayout.PREFERRED_SIZE, 378, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		lblId = new JLabel("ID");
		
		txtId = new JTextField();
		txtId.setColumns(10);
		
		lblNome = new JLabel("Nome");
		
		txtNome = new JTextField();
		txtNome.setColumns(10);
		
		lblPaisDeOrigem = new JLabel("País de Origem");
		
		txtPaisDeOrigem = new JTextField();
		txtPaisDeOrigem.setColumns(10);
		
		lblLogo = new JLabel("Logo");
		
		lblPiclogo = new JLabel("");
		lblPiclogo.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		Image iconLogo = new ImageIcon(this.getClass().getResource("/sem_foto.jpg")).getImage();
		lblPiclogo.setIcon(new ImageIcon(iconLogo));
		
		panelBotoesImagem = new JPanel();
		GroupLayout gl_panelFormulario = new GroupLayout(panelFormulario);
		gl_panelFormulario.setHorizontalGroup(
			gl_panelFormulario.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelFormulario.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelFormulario.createParallelGroup(Alignment.LEADING)
						.addComponent(txtNome, GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
						.addGroup(gl_panelFormulario.createSequentialGroup()
							.addComponent(lblPiclogo)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panelBotoesImagem, GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
						.addComponent(lblId)
						.addComponent(txtId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNome)
						.addComponent(lblPaisDeOrigem)
						.addComponent(lblLogo)
						.addComponent(txtPaisDeOrigem, GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panelFormulario.setVerticalGroup(
			gl_panelFormulario.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelFormulario.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(lblId)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNome)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblPaisDeOrigem)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtPaisDeOrigem, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelFormulario.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panelFormulario.createSequentialGroup()
							.addComponent(lblLogo)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblPiclogo))
						.addComponent(panelBotoesImagem, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panelBotoesImagem.setLayout(new GridLayout(0, 1, 0, 0));
		
		btnRmimagem = new JButton("");
		Image iconDelete = new ImageIcon(this.getClass().getResource("/delete.png")).getImage();
		btnRmimagem.setIcon(new ImageIcon(iconDelete));
		panelBotoesImagem.add(btnRmimagem);
		
		btnAddimagem = new JButton("");
		panelBotoesImagem.add(btnAddimagem);
		Image iconCamera = new ImageIcon(this.getClass().getResource("/camera.png")).getImage();
		btnAddimagem.setIcon(new ImageIcon(iconCamera));
		panelFormulario.setLayout(gl_panelFormulario);
		panelBotoes.setLayout(new GridLayout(1, 0, 0, 0));
		
		btnNovo = new JButton("");
		Image iconFile = new ImageIcon(this.getClass().getResource("/file.png")).getImage();
		btnNovo.setIcon(new ImageIcon(iconFile));
		panelBotoes.add(btnNovo);
		
		btnLimpar = new JButton("");
		Image iconBroom = new ImageIcon(this.getClass().getResource("/broom.png")).getImage();
		btnLimpar.setIcon(new ImageIcon(iconBroom));
		panelBotoes.add(btnLimpar);
		
		btnExcluir = new JButton("");
		Image iconDeleteForever = new ImageIcon(this.getClass().getResource("/delete-forever.png")).getImage();
		btnExcluir.setIcon(new ImageIcon(iconDeleteForever));
		panelBotoes.add(btnExcluir);
		
		btnSalvar = new JButton("");
		panelBotoes.add(btnSalvar);
		Image iconContentSave = new ImageIcon(this.getClass().getResource("/content-save.png")).getImage();
		btnSalvar.setIcon(new ImageIcon(iconContentSave));
		panelDados.setLayout(gl_panelDados);
		
		panelTable = new JPanel();
		panelTable.setBorder(new TitledBorder(null, "Marca Cadastrada", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panelTable);
		
		scrollPane = new JScrollPane();
		GroupLayout gl_panelTable = new GroupLayout(panelTable);
		gl_panelTable.setHorizontalGroup(
			gl_panelTable.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTable.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panelTable.setVerticalGroup(
			gl_panelTable.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTable.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
					.addContainerGap())
		);
		panelTable.setLayout(gl_panelTable);
	}
}
