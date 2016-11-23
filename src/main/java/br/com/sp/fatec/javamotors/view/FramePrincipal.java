package br.com.sp.fatec.javamotors.view;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import br.com.sp.fatec.javamotors.controller.CarroController;
import br.com.sp.fatec.javamotors.model.Carro;
import br.com.sp.fatec.javamotors.model.Cliente;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.BorderLayout;

public class FramePrincipal extends JFrame {

	private JPanel contentPane;
	private JPanel panelMenu;
	private JButton btnMarca;
	private JButton btnModelo;
	private JButton btnCarro;
	private JButton btnCliente;
	private JButton btnVersao;
	private JPanel panelTable;
	private JPanel panelDados;
	private JScrollPane scrollPane;
	private JPanel panelWrapper;
	private JPanel panelFoto;
	private JPanel panelDadosCarro;
	private JPanel panelDadosAnunciante;
	private JPanel panelBotaoVenda;
	private JButton btnRealizarVenda;
	private JLabel picCarro;
	private JPanel panel_4;
	private JButton btnPic1;
	private JButton btnPic2;
	private JButton btnPic3;
	private JLabel lblId;
	private JTextField txtId;
	private JLabel lblMarca;
	private JTextField txtMarca;
	private JTextField txtModelo;
	private JLabel lblModelo;
	private JLabel lblVersao;
	private JTextField txtVersao;
	private JLabel lblKm;
	private JTextField txtKm;
	private JTextField txtCor;
	private JLabel lblCor;
	private JLabel lblAnunciante;
	private JTextField txtAnunciante;
	private JLabel lblCpf;
	private JTextField txtCpf;
	private JTextField txtDataDeAnuncio;
	private JLabel lblDataDeAnuncio;
	private JLabel lblEndereco;
	private JTextField txtEndereco;
	private JLabel lblEmail;
	private JTextField txtEmail;
	private JTable tableCarro;
	
	private boolean frameAbriu;
    private boolean listenerLista;
    
    private CarroController carroControlador;
    
    private ImageIcon[] listaPics = {null, null, null};
    private int picSelecionada;
    
    private Carro carro;
    private List<Carro> lista;
	
	/**
	 * Create the frame.
	 */
	public FramePrincipal() {
		initialize();
		initListeners();
		
		frameAbriu = true;
        listenerLista = true;
        
        carroControlador = new CarroController();
        
        setarNoImg();
        atualizarTableCarros();
	}
	
	private void setarNoImg() {
		ImageIcon icon = new ImageIcon(getClass().getResource("/sem_foto_3.jpg"));
        BufferedImage bi = new BufferedImage(
                        icon.getIconWidth(),
                        icon.getIconHeight(),
                        BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        icon.paintIcon(null, g, 0, 0);
        g.dispose();
        picCarro.setIcon(new ImageIcon(bi.getScaledInstance(276, 192, Image.SCALE_DEFAULT)));
	}
	
	private void atualizarTableCarros() {
		tableCarro = criarTableCarros();
		scrollPane.setViewportView(tableCarro);
	}
	
	private JTable criarTableCarros() {
        if (frameAbriu) {
            // frameAbriu = false;
        }
        lista = carroControlador.showOnSale();
        
        String[] headers = {"Cod.", "Marca", "Modelo", "Versão", "KM", "Anunciante"};
        TableModel model = new DefaultTableModel(headers, lista.size()){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (int i = 0; i < lista.size(); i++) {
            model.setValueAt(lista.get(i).getId(), i, 0);
            model.setValueAt(lista.get(i).getVersao().getModelo().getMarca(), i, 1);
            model.setValueAt(lista.get(i).getVersao().getModelo(), i, 2);
            model.setValueAt(lista.get(i).getVersao().toString(), i, 3);
            model.setValueAt(lista.get(i).getKm(), i, 4);
            model.setValueAt(lista.get(i).getAnunciante().toString(), i, 5);
        }
        
        final JTable retorno = new JTable(model);
        retorno.getColumnModel().getColumn(0).setPreferredWidth(35);
        retorno.getColumnModel().getColumn(1).setPreferredWidth(100);
        retorno.getColumnModel().getColumn(2).setPreferredWidth(100);
        retorno.getColumnModel().getColumn(3).setPreferredWidth(265);
        retorno.getColumnModel().getColumn(4).setPreferredWidth(100);
        retorno.getColumnModel().getColumn(5).setPreferredWidth(178);
        retorno.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        retorno.getTableHeader().setResizingAllowed(false);
        retorno.getTableHeader().setReorderingAllowed(false);
        
        retorno.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting() && listenerLista) {
                    carro = lista.get(retorno.getSelectedRow());
                    popularCampos();
                }
			}
		});
        return retorno;
    }
    
    private String enderecoCompleto(Cliente cliente) {
        String retorno = cliente.getEndereco().getEndereco() + ", " +
                cliente.getEndereco().getNumero();
        return retorno;
    } 
    
    private void carregarImgs() {
        try {
            int i = 0;
            for (String caminho : carro.getFotos()) {
                BufferedImage bi = ImageIO.read(new File(caminho));
                listaPics[i] = new ImageIcon(bi.getScaledInstance(276, 192, Image.SCALE_DEFAULT));
                i++;
            }
            picCarro.setIcon(listaPics[0]);
            picSelecionada = 1;
        } catch (IOException e) {
            System.out.println("Deu bosta na hora de subir a img chefe.");
        }
    }
    
    private void popularCampos() {
        if (carro != null) {
            carregarImgs();
            txtMarca.setText(carro.getVersao().getModelo().getMarca().getNome());
            txtModelo.setText(carro.getVersao().getModelo().getNome()+" - "+carro.getVersao().getModelo().getAno());
            txtVersao.setText(carro.getVersao().toString());
            txtId.setText(carro.getId().toString());
            txtKm.setText(carro.getKm().toString());
            txtAnunciante.setText("ID: " + carro.getAnunciante().getId() + " - " + carro.getAnunciante().getNome());
            txtCpf.setText(carro.getAnunciante().getCpf());
            txtEndereco.setText(enderecoCompleto(carro.getAnunciante()));
            txtEmail.setText(carro.getAnunciante().getEmail());
            txtDataDeAnuncio.setText(carro.getDataAnuncio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
    }
    
    private void trocarPicSelecionada(int i) {
        if (i == 1) {
            picSelecionada = 1;
            picCarro.setIcon(listaPics[0]);
        } else if (i == 2) {
            picSelecionada = 2;
            picCarro.setIcon(listaPics[1]);
        } else {
            picSelecionada = 3;
            picCarro.setIcon(listaPics[2]);
        }
    }
	
	
	private void initialize() {
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1612, 906);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		panelMenu = new JPanel();
		
		panelTable = new JPanel();
		panelTable.setBorder(new TitledBorder(null, "Carros a Venda", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		panelDados = new JPanel();
		panelDados.setBorder(new TitledBorder(null, "Informa\u00E7\u00F5es do Carro", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(panelTable, GroupLayout.DEFAULT_SIZE, 1164, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panelDados, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(panelMenu, GroupLayout.PREFERRED_SIZE, 1566, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelMenu, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panelTable, GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE)
						.addComponent(panelDados, GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		panelWrapper = new JPanel();
		panelWrapper.setBorder(new LineBorder(Color.GRAY));
		GroupLayout gl_panelDados = new GroupLayout(panelDados);
		gl_panelDados.setHorizontalGroup(
			gl_panelDados.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDados.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelWrapper, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panelDados.setVerticalGroup(
			gl_panelDados.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDados.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelWrapper, GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)
					.addContainerGap())
		);
		GridBagLayout gbl_panelWrapper = new GridBagLayout();
		gbl_panelWrapper.columnWidths = new int[]{362, 0};
		gbl_panelWrapper.rowHeights = new int[]{199, 209, 188, 63, 0};
		gbl_panelWrapper.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panelWrapper.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelWrapper.setLayout(gbl_panelWrapper);
		
		panelFoto = new JPanel();
		
		picCarro = new JLabel("");
		Image iconLogo = new ImageIcon(this.getClass().getResource("/sem_foto_3.jpg")).getImage();
		picCarro.setIcon(new ImageIcon(iconLogo));
		picCarro.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		panel_4 = new JPanel();
		GroupLayout gl_panelFoto = new GroupLayout(panelFoto);
		gl_panelFoto.setHorizontalGroup(
			gl_panelFoto.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelFoto.createSequentialGroup()
					.addContainerGap()
					.addComponent(picCarro, GroupLayout.PREFERRED_SIZE, 276, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panelFoto.setVerticalGroup(
			gl_panelFoto.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelFoto.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelFoto.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_4, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
						.addComponent(picCarro, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		Image pic1 = new ImageIcon(this.getClass().getResource("/numeric-1-box.png")).getImage();
		Image pic2 = new ImageIcon(this.getClass().getResource("/numeric-2-box.png")).getImage();
		Image pic3 = new ImageIcon(this.getClass().getResource("/numeric-3-box.png")).getImage();
		
		btnPic1 = new JButton("");
		btnPic1.setIcon(new ImageIcon(pic1));
		
		btnPic2 = new JButton("");
		btnPic2.setIcon(new ImageIcon(pic2));
		
		btnPic3 = new JButton("");
		btnPic3.setIcon(new ImageIcon(pic3));
		
		panel_4.setLayout(new GridLayout(0, 1, 0, 0));
		panel_4.add(btnPic1);
		panel_4.add(btnPic2);
		panel_4.add(btnPic3);
		panelFoto.setLayout(gl_panelFoto);
		GridBagConstraints gbc_panelFoto = new GridBagConstraints();
		gbc_panelFoto.fill = GridBagConstraints.BOTH;
		gbc_panelFoto.insets = new Insets(0, 0, 5, 0);
		gbc_panelFoto.gridx = 0;
		gbc_panelFoto.gridy = 0;
		panelWrapper.add(panelFoto, gbc_panelFoto);
		
		panelDadosCarro = new JPanel();
		GridBagConstraints gbc_panelDadosCarro = new GridBagConstraints();
		gbc_panelDadosCarro.fill = GridBagConstraints.BOTH;
		gbc_panelDadosCarro.insets = new Insets(0, 0, 5, 0);
		gbc_panelDadosCarro.gridx = 0;
		gbc_panelDadosCarro.gridy = 1;
		panelWrapper.add(panelDadosCarro, gbc_panelDadosCarro);
		
		lblId = new JLabel("ID");
		
		txtId = new JTextField();
		txtId.setEnabled(false);
		txtId.setColumns(10);
		
		lblMarca = new JLabel("Marca");
		
		txtMarca = new JTextField();
		txtMarca.setEnabled(false);
		txtMarca.setColumns(10);
		
		txtModelo = new JTextField();
		txtModelo.setEnabled(false);
		txtModelo.setColumns(10);
		
		lblModelo = new JLabel("Modelo");
		
		lblVersao = new JLabel("Versão");
		
		txtVersao = new JTextField();
		txtVersao.setEnabled(false);
		txtVersao.setColumns(10);
		
		lblKm = new JLabel("KM");
		
		txtKm = new JTextField();
		txtKm.setEnabled(false);
		txtKm.setColumns(10);
		
		txtCor = new JTextField();
		txtCor.setEnabled(false);
		txtCor.setColumns(10);
		
		lblCor = new JLabel("Cor");
		GroupLayout gl_panelDadosCarro = new GroupLayout(panelDadosCarro);
		gl_panelDadosCarro.setHorizontalGroup(
			gl_panelDadosCarro.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDadosCarro.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelDadosCarro.createParallelGroup(Alignment.LEADING)
						.addComponent(txtVersao, GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
						.addGroup(gl_panelDadosCarro.createSequentialGroup()
							.addGroup(gl_panelDadosCarro.createParallelGroup(Alignment.LEADING)
								.addComponent(lblId)
								.addComponent(txtId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblMarca)
								.addComponent(txtMarca, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panelDadosCarro.createParallelGroup(Alignment.LEADING)
								.addComponent(txtModelo)
								.addComponent(lblModelo)))
						.addComponent(lblVersao)
						.addGroup(gl_panelDadosCarro.createSequentialGroup()
							.addGroup(gl_panelDadosCarro.createParallelGroup(Alignment.LEADING)
								.addComponent(txtKm, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblKm))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panelDadosCarro.createParallelGroup(Alignment.LEADING)
								.addComponent(lblCor)
								.addComponent(txtCor, GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_panelDadosCarro.setVerticalGroup(
			gl_panelDadosCarro.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDadosCarro.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblId)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelDadosCarro.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMarca)
						.addComponent(lblModelo))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelDadosCarro.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtMarca, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtModelo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblVersao)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtVersao, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelDadosCarro.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblKm)
						.addComponent(lblCor))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelDadosCarro.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtKm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtCor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(31, Short.MAX_VALUE))
		);
		panelDadosCarro.setLayout(gl_panelDadosCarro);
		
		panelDadosAnunciante = new JPanel();
		
		lblAnunciante = new JLabel("Anunciante");
		
		txtAnunciante = new JTextField();
		txtAnunciante.setEnabled(false);
		txtAnunciante.setColumns(10);
		
		lblCpf = new JLabel("CPF");
		
		txtCpf = new JTextField();
		txtCpf.setEnabled(false);
		txtCpf.setColumns(10);
		
		txtDataDeAnuncio = new JTextField();
		txtDataDeAnuncio.setEnabled(false);
		txtDataDeAnuncio.setColumns(10);
		
		lblDataDeAnuncio = new JLabel("Data de Anuncio");
		
		lblEndereco = new JLabel("Endereço");
		
		txtEndereco = new JTextField();
		txtEndereco.setEditable(true);
		txtEndereco.setEnabled(false);
		txtEndereco.setText("");
		txtEndereco.setColumns(10);
		
		lblEmail = new JLabel("E-Mail");
		
		txtEmail = new JTextField();
		txtEmail.setEnabled(false);
		txtEmail.setColumns(10);
		GroupLayout gl_panelDadosAnunciante = new GroupLayout(panelDadosAnunciante);
		gl_panelDadosAnunciante.setHorizontalGroup(
			gl_panelDadosAnunciante.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDadosAnunciante.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelDadosAnunciante.createParallelGroup(Alignment.LEADING)
						.addComponent(txtEndereco, GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
						.addComponent(txtAnunciante, GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
						.addComponent(lblAnunciante)
						.addGroup(gl_panelDadosAnunciante.createSequentialGroup()
							.addGroup(gl_panelDadosAnunciante.createParallelGroup(Alignment.LEADING)
								.addComponent(txtCpf, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblCpf))
							.addGap(7)
							.addGroup(gl_panelDadosAnunciante.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDataDeAnuncio)
								.addComponent(txtDataDeAnuncio)))
						.addComponent(lblEndereco)
						.addComponent(lblEmail)
						.addComponent(txtEmail, GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panelDadosAnunciante.setVerticalGroup(
			gl_panelDadosAnunciante.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDadosAnunciante.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblAnunciante)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtAnunciante, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelDadosAnunciante.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCpf)
						.addComponent(lblDataDeAnuncio))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelDadosAnunciante.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtCpf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtDataDeAnuncio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblEndereco)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtEndereco, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblEmail)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(24, Short.MAX_VALUE))
		);
		panelDadosAnunciante.setLayout(gl_panelDadosAnunciante);
		GridBagConstraints gbc_panelDadosAnunciante = new GridBagConstraints();
		gbc_panelDadosAnunciante.fill = GridBagConstraints.BOTH;
		gbc_panelDadosAnunciante.insets = new Insets(0, 0, 5, 0);
		gbc_panelDadosAnunciante.gridx = 0;
		gbc_panelDadosAnunciante.gridy = 2;
		panelWrapper.add(panelDadosAnunciante, gbc_panelDadosAnunciante);
		
		panelBotaoVenda = new JPanel();
		
		btnRealizarVenda = new JButton("Realizar Venda");
		Image picVenda = new ImageIcon(this.getClass().getResource("/Key Exchange-64.png")).getImage();
		btnRealizarVenda.setIcon(new ImageIcon(picVenda));
		GridBagConstraints gbc_panelBotaoVenda = new GridBagConstraints();
		gbc_panelBotaoVenda.fill = GridBagConstraints.BOTH;
		gbc_panelBotaoVenda.gridx = 0;
		gbc_panelBotaoVenda.gridy = 3;
		panelWrapper.add(panelBotaoVenda, gbc_panelBotaoVenda);
		panelBotaoVenda.setLayout(new GridLayout(0, 1, 0, 0));
		panelBotaoVenda.add(btnRealizarVenda);
		panelDados.setLayout(gl_panelDados);
		
		scrollPane = new JScrollPane();
		GroupLayout gl_panelTable = new GroupLayout(panelTable);
		gl_panelTable.setHorizontalGroup(
			gl_panelTable.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTable.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1132, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panelTable.setVerticalGroup(
			gl_panelTable.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTable.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)
					.addContainerGap())
		);
		panelTable.setLayout(gl_panelTable);
		Image iconBmw = new ImageIcon(this.getClass().getResource("/bmw.png")).getImage();
		Image iconCarProfile = new ImageIcon(this.getClass().getResource("/car_profile.png")).getImage();
		
		btnCarro = new JButton("Carros");
		Image iconCar = new ImageIcon(this.getClass().getResource("/car.png")).getImage();
		btnCarro.setIcon(new ImageIcon(iconCar));
		
		btnVersao = new JButton("Versões");
		Image iconAutomotive = new ImageIcon(this.getClass().getResource("/automotive.png")).getImage();
		btnVersao.setIcon(new ImageIcon(iconAutomotive));
		
		panelMenu.setLayout(new GridLayout(0, 5, 0, 0));
		panelMenu.add(btnCarro);
		
		btnCliente = new JButton("Clientes");
		Image iconAccountCard = new ImageIcon(this.getClass().getResource("/account-card.png")).getImage();
		btnCliente.setIcon(new ImageIcon(iconAccountCard));
		panelMenu.add(btnCliente);
		
		btnMarca = new JButton("Marcas");
		btnMarca.setIcon(new ImageIcon(iconBmw));
		panelMenu.add(btnMarca);
		
		btnModelo = new JButton("Modelos");
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
		
		btnVersao.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				FrameVersao frame = new FrameVersao();
				frame.setVisible(true);
			}
		});
		
		btnCliente.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				FrameCliente frame = new FrameCliente();
				frame.setVisible(true);
			}
		});
		
		btnCarro.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				FrameCarro frame = new FrameCarro();
				frame.setVisible(true);
			}
		});
		
		btnPic1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				trocarPicSelecionada(1);
			}
		});
		
		btnPic2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				trocarPicSelecionada(2);
			}
		});
		
		btnPic3.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				trocarPicSelecionada(3);
			}
		});
	}
}
