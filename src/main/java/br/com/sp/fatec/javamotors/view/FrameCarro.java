package br.com.sp.fatec.javamotors.view;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import br.com.sp.fatec.javamotors.controller.CarroController;
import br.com.sp.fatec.javamotors.controller.ClienteController;
import br.com.sp.fatec.javamotors.controller.IOController;
import br.com.sp.fatec.javamotors.controller.MarcaController;
import br.com.sp.fatec.javamotors.controller.ModeloController;
import br.com.sp.fatec.javamotors.controller.VersaoController;
import br.com.sp.fatec.javamotors.model.Carro;
import br.com.sp.fatec.javamotors.model.Cliente;
import br.com.sp.fatec.javamotors.model.Marca;
import br.com.sp.fatec.javamotors.model.Modelo;
import br.com.sp.fatec.javamotors.model.Status;
import br.com.sp.fatec.javamotors.model.Versao;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class FrameCarro extends JDialog {

	private JPanel contentPane;
	private JPanel panelDados;
	private JPanel panelTable;
	private JPanel panelBotoes;
	private JButton btnNovo;
	private JButton btnSalvar;
	private JPanel panelFormulario;
	private JScrollPane scrollPane;
	private JPanel panelDadosCarro;
	private JPanel panelFotosCarro;
	private JPanel panelDadosAnunciante;
	private JPanel panelDadosComprador;
	private JLabel lblId;
	private JTextField txtId;
	private JLabel lblMarca;
	private JComboBox<Marca> cbMarca;
	private JLabel lblModelo;
	private JComboBox<Modelo> cbModelo;
	private JLabel lblVersao;
	private JComboBox<Versao> cbVersao;
	private JLabel lblKm;
	private JTextField txtKm;
	private JLabel lblCor;
	private JTextField txtCor;
	private JLabel lblAnunciante;
	private JComboBox<Cliente> cbAnunciante;
	private JLabel lblCpfDoAnunciante;
	private JTextField txtCpfDoAnunciante;
	private JLabel lblEnderecoDoAnunciante;
	private JTextField txtEnderecoDoAnunciante;
	private JLabel lblEmailDoAnunciante;
	private JTextField txtEmailDoAnunciante;
	private JLabel lblDataDeAnuncio;
	private JTextField txtDataDeAnuncio;
	private JLabel lblComprador;
	private JTextField txtComprador;
	private JLabel lblCpfDoComprador;
	private JTextField txtCpfDoComprador;
	private JLabel lblEnderecoDoComprador;
	private JTextField txtEnderecoDoComprador;
	private JLabel lblEmailDoComprador;
	private JTextField txtEmailDoComprador;
	private JLabel lblDataDeVenda;
	private JTextField txtDataDeVenda;
	private JLabel picCarro;
	private JPanel panelBtnFoto;
	private JButton btnFoto;
	private JPanel panelBtnsFotos;
	private JButton btnPic1;
	private JButton btnPic2;
	private JButton btnPic3;
	private JTable tableCarro;
	
	private MarcaController marcaControlador;
    private ModeloController modeloControlador;
    private VersaoController versaoControlador;
    private ClienteController clienteControlador;
    private CarroController carroControlador;
    
    private boolean frameAbriu;
    private boolean listenerMarca;
    private boolean listenerModelo;
    private boolean listenerCliente;
    private boolean listenerLista;
    
    private String pastaImg = IOController.urlImagens() + "carro/";
    private ImageIcon[] listaPics = {null, null, null};
    private int picSelecionada;
    
    private Carro carro;
    private List<Carro> lista;
    
    
	
	/**
	 * Create the frame.
	 */
	public FrameCarro() {
		initialize();
		initListeners();
		
		frameAbriu = true;
        listenerLista = true;
        
        modeloControlador = new ModeloController();
        marcaControlador = new MarcaController();
        versaoControlador = new VersaoController();
        clienteControlador = new ClienteController();
        carroControlador = new CarroController();
        
        popularCbMarca();
        listenerMarca = true;
        
        popularCbAnunciante();
        listenerCliente = true;
        
        limparImg();
        
        atualizarTableCarros();
	}
	
	private void popularCbMarca() {
		for (Marca marca : marcaControlador.index()) {
            cbMarca.addItem(marca);
        }
		cbMarca.setSelectedIndex(-1);
	}
	
	private void popularCbAnunciante() {
		for (Cliente cliente : clienteControlador.index()) {
            cbAnunciante.addItem(cliente);
        }
        cbAnunciante.setSelectedIndex(-1);
	}
	
	private void limparImg() {
        picCarro.setIcon(new ImageIcon(getClass().getResource("/sem_foto_2.jpg")));
        for (int i = 0; i < 3; i++) {
            listaPics[i] = (ImageIcon)picCarro.getIcon();
        }
        picSelecionada = 1;
    }
	
	private void atualizarTableCarros() {
		tableCarro = criarTableCarros();
		scrollPane.setViewportView(tableCarro);
	}
	
	private JTable criarTableCarros() {
		if (frameAbriu) {
            frameAbriu = false;
        }
        lista = carroControlador.index();
        
        String[] headers = {"Cod.", "Marca", "Modelo", "Versão", "KM", "Anunciante", "Status"};
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
            model.setValueAt(lista.get(i).getStatus(), i, 6);
        }
        
        final JTable retorno = new JTable(model);
        retorno.getColumnModel().getColumn(0).setPreferredWidth(35);
        retorno.getColumnModel().getColumn(1).setPreferredWidth(80);
        retorno.getColumnModel().getColumn(2).setPreferredWidth(80);
        retorno.getColumnModel().getColumn(3).setPreferredWidth(245);
        retorno.getColumnModel().getColumn(4).setPreferredWidth(80);
        retorno.getColumnModel().getColumn(5).setPreferredWidth(178);
        retorno.getColumnModel().getColumn(6).setPreferredWidth(80);
        retorno.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        retorno.getTableHeader().setResizingAllowed(false);
        retorno.getTableHeader().setReorderingAllowed(false);
        
        retorno.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting() && listenerLista) {
                    carro = lista.get(retorno.getSelectedRow());
                    limparComprador();
                    popularCampos();
                    if (carro.getStatus() == Status.VENDIDO) {
                        disableTudo();
                    } else {
                        enableTudo();
                    }
                }
			}
		});
        return retorno;
	}
	
	private void popularCbModelo() {
        listenerModelo = false;
        cbVersao.removeAllItems();
        cbModelo.removeAllItems();
        for (Modelo modelo : modeloControlador.findByMarca((Marca)cbMarca.getSelectedItem())) {
            cbModelo.addItem(modelo);
        }
        cbModelo.setSelectedIndex(-1);
        listenerModelo = true;
    }
    
    private void popularCbVersao() {
        cbVersao.removeAllItems();
        for (Versao versao : versaoControlador.findByModelo((Modelo)cbModelo.getSelectedItem())) {
            cbVersao.addItem(versao);
        }
        cbVersao.setSelectedIndex(-1);
    }
    
    private String enderecoCompleto(Cliente cliente) {
        String retorno = cliente.getEndereco().getEndereco() + ", " +
                cliente.getEndereco().getNumero();
        return retorno;
    } 
    
    private void popularDadosAnunciante() {
        Cliente c = (Cliente)cbAnunciante.getSelectedItem();
        txtCpfDoAnunciante.setText(c.getCpf());
        txtEnderecoDoAnunciante.setText(enderecoCompleto(c));
        txtEmailDoAnunciante.setText(c.getEmail());
    }
    
    private boolean isImagemValida(File file) {
        String nome = file.getName().substring(file.getName().lastIndexOf(".")+1);
        return nome.equals("jpg") || nome.equals("jpeg") || nome.equals("png") || nome.equals("bmp");
    }
    
    private void subirImg() {
        try {
            JFileChooser fc = new JFileChooser();
            fc.showDialog(this, "Abrir");
            File f = fc.getSelectedFile();
            if (isImagemValida(f)){
                BufferedImage bi = ImageIO.read(f);
                picCarro.setIcon(new ImageIcon(bi.getScaledInstance(220, 134, Image.SCALE_DEFAULT)));
                
                if (picSelecionada == 1)
                    listaPics[0] = (ImageIcon)picCarro.getIcon();
                else if (picSelecionada == 2)
                    listaPics[1] = (ImageIcon)picCarro.getIcon();
                else
                    listaPics[2] = (ImageIcon)picCarro.getIcon();
                
            } else {
                JOptionPane.showMessageDialog(this, "Formato de arquivo inválido.\nDeve ser .jpg, .jpeg, .png ou .bmp");
            }
        } catch (IOException e) {
            System.out.println("Deu bosta na hora de subir a img chefe.");
        } catch (Exception e) {
            System.out.println("Deu bosta na hora de subir a img chefe.");
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
    
    private boolean isFormularioValido() {
        if (cbVersao.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Favor selecionar uma Marca/Modelo/Versão.");
            return false;
        } else if (txtKm.getText().length() <= 0) {
            JOptionPane.showMessageDialog(this, "Insira uma quilometragem válida");
            return false;
        } else if (txtCor.getText().length() <= 0) {
            JOptionPane.showMessageDialog(this, "Insira uma cor válida");
            return false;
        } else if (cbAnunciante.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Favor selecionar um cliente válido");
            return false;
        } else {
            return true;
        }
    }
    
    private void limpar() {
        listenerCliente = false;
        listenerMarca = false;
        listenerModelo = false;
        cbAnunciante.setSelectedIndex(-1);
        txtCpfDoAnunciante.setText("");
        txtEnderecoDoAnunciante.setText("");
        txtEmailDoAnunciante.setText("");
        cbVersao.setSelectedIndex(-1);
        cbModelo.setSelectedIndex(-1);
        cbMarca.setSelectedIndex(-1);
        listenerCliente = true;
        listenerMarca = true;
        listenerModelo = true;
        txtKm.setText("");
        txtCor.setText("");
        limparImg();
    }
    
    private void limparComprador() {
        txtComprador.setText("");
        txtCpfDoComprador.setText("");
        txtEmailDoComprador.setText("");
        txtEnderecoDoComprador.setText("");
        txtDataDeVenda.setText("");
    }
    
    private void novoCarro() {
        limpar();
        txtId.setText("");
        txtDataDeAnuncio.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        limparComprador();
        listenerLista = false;
        tableCarro.getSelectionModel().clearSelection();
        listenerLista = true;
        carro = null;
    }
    
    private String gerarPastaCarro(Carro carro) {
        String retorno = carro.getId() + "_" + 
                carro.getVersao().getModelo().getNome() + "_" +
                carro.getVersao().getModelo().getAno() + "_" +
                carro.getAnunciante().getNome();
        return retorno;
    }
    
    private void salvarImgs(Carro carro) {
        try {
            Integer i = 0;
            File f = new File(pastaImg + gerarPastaCarro(carro));
            f.mkdir();
            for (ImageIcon icon : listaPics) {
                BufferedImage bi = new BufferedImage(
                        icon.getIconWidth(),
                        icon.getIconHeight(),
                        BufferedImage.TYPE_INT_RGB);
                Graphics g = bi.createGraphics();
                icon.paintIcon(null, g, 0, 0);
                g.dispose();
                ImageIO.write(bi, "jpg", new File(pastaImg + gerarPastaCarro(carro) + "/" + i + ".jpg"));
                i++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private Set<String> pegarCaminhoFotos(Carro carro) {
        Set<String> retorno = new HashSet<String>();
    	String[] retorno1 = {"", "", ""};
        int i = 0;
        for (ImageIcon icon : listaPics) {
            retorno.add(pastaImg + gerarPastaCarro(carro) + "/" + i + ".jpg");
            i++;
        }
        return retorno;
    }
    
    private Carro criarCarro() {
        Carro carro = new Carro();
        carro.setVersao((Versao)cbVersao.getSelectedItem());
        carro.setKm(Float.parseFloat(txtKm.getText()));
        carro.setCor(txtCor.getText());
        carro.setDataAnuncio(LocalDate.now());
        carro.setAnunciante((Cliente) cbAnunciante.getSelectedItem());
        carro.setStatus(Status.A_VENDA);
        return carro;
    }
    
    private void salvarCarro() {
        if (isFormularioValido()) {
            if (carro == null) {
            	carro = criarCarro();
                if (carroControlador.create(carro)) {
                	JOptionPane.showMessageDialog(this, "Carro adicionado com sucesso");
                    carro.setFotos(pegarCaminhoFotos(carro));
                    salvarImgs(carro);
                    carroControlador.update(carro);
                }
            } else {
                editarCarro();
                if (carroControlador.update(carro))
                	JOptionPane.showMessageDialog(this, "Carro " + carro.getId()+ " atualizado com sucesso");
            }
            atualizarTableCarros();
            novoCarro();
        }
    }
    
    private void editarCarro() {
    	carro.setId(Long.parseLong(txtId.getText()));
        carro.setVersao((Versao)cbVersao.getSelectedItem());
        carro.setKm(Float.parseFloat(txtKm.getText()));
        carro.setCor(txtCor.getText());
        carro.setFotos(pegarCaminhoFotos(carro));
        carro.setAnunciante((Cliente) cbAnunciante.getSelectedItem());
        carro.setStatus(Status.A_VENDA);
        lista.set(tableCarro.getSelectedRow(), carro);
        salvarImgs(carro);
    }
    
    private void carregarImgs() {
        try {
            int i = 0;
            for (String caminho : carro.getFotos()) {
                BufferedImage bi = ImageIO.read(new File(caminho));
                listaPics[i] = new ImageIcon(bi.getScaledInstance(220, 134, Image.SCALE_DEFAULT));
                i++;
            }
            picCarro.setIcon(listaPics[0]);
            picSelecionada = 1;
        } catch (IOException e) {
            System.out.println("Deu bosta na hora de subir a img chefe.");
        }
    }
    
    private void setarCbMarca() {
        for (int i = 0; i < cbMarca.getItemCount(); i++) {
            if (cbMarca.getItemAt(i).getId() == carro.getVersao().getModelo().getMarca().getId())
                cbMarca.setSelectedIndex(i);
        }
    }
    
    private void setarCbModelo() {
        for (int i = 0; i < cbModelo.getItemCount(); i++) {
            if (cbModelo.getItemAt(i).getId() == carro.getVersao().getModelo().getId())
                cbModelo.setSelectedIndex(i);
        }
    }
    
    private void setarCbVersao() {
        for (int i = 0; i < cbVersao.getItemCount(); i++) {
            if (cbVersao.getItemAt(i).getId() == carro.getVersao().getId())
                cbVersao.setSelectedIndex(i);
        }
    }
    
    private void setarCbAnunciante() {
        for (int i = 0; i < cbAnunciante.getItemCount(); i++) {
            if (cbAnunciante.getItemAt(i).getId() == carro.getAnunciante().getId())
                cbAnunciante.setSelectedIndex(i);
        }
    }
    
    private void popularCampos() {
        if (carro != null) {
            carregarImgs();
            setarCbMarca();
            setarCbModelo();
            setarCbVersao();
            txtId.setText(carro.getId().toString());
            txtKm.setText(carro.getKm().toString());
            txtCor.setText(carro.getCor());
            setarCbAnunciante();
            txtCpfDoAnunciante.setText(carro.getAnunciante().getCpf());
            txtEnderecoDoAnunciante.setText(enderecoCompleto(carro.getAnunciante()));
            txtEmailDoAnunciante.setText(carro.getAnunciante().getEmail());
            txtDataDeAnuncio.setText(carro.getDataAnuncio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            if (carro.getComprador() != null) {
                txtComprador.setText("ID: " + Float.toString(carro.getComprador().getId()) + " - " +carro.getComprador().getNome());
                txtCpfDoComprador.setText(carro.getComprador().getCpf());
                txtEnderecoDoComprador.setText(enderecoCompleto(carro.getComprador()));
                txtEmailDoComprador.setText(carro.getComprador().getEmail());
                txtDataDeVenda.setText(carro.getDataVenda().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        }
    }
	
	private void disableTudo() {
		cbMarca.setEnabled(false);
        cbModelo.setEnabled(false);
        cbVersao.setEnabled(false);
        txtKm.setEnabled(false);
        txtCor.setEnabled(false);
        cbAnunciante.setEnabled(false);
        btnFoto.setEnabled(false);
	}
	
	private void enableTudo() {
		cbMarca.setEnabled(true);
        cbModelo.setEnabled(true);
        cbVersao.setEnabled(true);
        txtKm.setEnabled(true);
        txtCor.setEnabled(true);
        cbAnunciante.setEnabled(true);
        btnFoto.setEnabled(true);
	}
	
	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1300, 675);
		setLocationRelativeTo(null);
		setModal(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		Image iconFile = new ImageIcon(this.getClass().getResource("/file.png")).getImage();
		Image iconBroom = new ImageIcon(this.getClass().getResource("/broom.png")).getImage();
		Image iconDeleteForever = new ImageIcon(this.getClass().getResource("/delete-forever.png")).getImage();
		Image iconContentSave = new ImageIcon(this.getClass().getResource("/content-save.png")).getImage();
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{520, 753, 0};
		gbl_contentPane.rowHeights = new int[]{614, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		panelDados = new JPanel();
		panelDados.setBorder(new TitledBorder(null, "Dados do Carro", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		panelBotoes = new JPanel();
		
		panelFormulario = new JPanel();
		panelFormulario.setBorder(new LineBorder(Color.LIGHT_GRAY));
		GroupLayout gl_panelDados = new GroupLayout(panelDados);
		gl_panelDados.setHorizontalGroup(
			gl_panelDados.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelDados.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelDados.createParallelGroup(Alignment.TRAILING)
						.addComponent(panelFormulario, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
						.addComponent(panelBotoes, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panelDados.setVerticalGroup(
			gl_panelDados.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDados.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelBotoes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelFormulario, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
					.addContainerGap())
		);
		Image pic1 = new ImageIcon(this.getClass().getResource("/numeric-1-box.png")).getImage();
		Image pic2 = new ImageIcon(this.getClass().getResource("/numeric-2-box.png")).getImage();
		Image pic3 = new ImageIcon(this.getClass().getResource("/numeric-3-box.png")).getImage();
		Image iconCamera = new ImageIcon(this.getClass().getResource("/camera.png")).getImage();
		GridBagLayout gbl_panelFormulario = new GridBagLayout();
		gbl_panelFormulario.columnWidths = new int[]{240, 240, 0};
		gbl_panelFormulario.rowHeights = new int[]{292, 247, 0};
		gbl_panelFormulario.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panelFormulario.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelFormulario.setLayout(gbl_panelFormulario);
		
		panelDadosCarro = new JPanel();
		
		lblId = new JLabel("ID");
		
		txtId = new JTextField();
		txtId.setEnabled(false);
		txtId.setColumns(10);
		
		lblMarca = new JLabel("Marca");
		
		cbMarca = new JComboBox<Marca>();
		
		lblModelo = new JLabel("Modelo");
		
		cbModelo = new JComboBox<Modelo>();
		
		lblVersao = new JLabel("Versão");
		
		cbVersao = new JComboBox<Versao>();
		
		lblKm = new JLabel("KM");
		
		txtKm = new JTextField();
		txtKm.setColumns(10);
		
		lblCor = new JLabel("Cor");
		
		txtCor = new JTextField();
		txtCor.setColumns(10);
		GroupLayout gl_panelDadosCarro = new GroupLayout(panelDadosCarro);
		gl_panelDadosCarro.setHorizontalGroup(
			gl_panelDadosCarro.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDadosCarro.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelDadosCarro.createParallelGroup(Alignment.LEADING)
						.addComponent(cbMarca, 0, 220, Short.MAX_VALUE)
						.addComponent(lblId)
						.addComponent(txtId, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMarca)
						.addComponent(lblModelo)
						.addComponent(cbModelo, 0, 220, Short.MAX_VALUE)
						.addComponent(lblVersao)
						.addComponent(cbVersao, 0, 220, Short.MAX_VALUE)
						.addComponent(lblKm)
						.addComponent(txtKm, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
						.addComponent(lblCor)
						.addComponent(txtCor, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE))
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
					.addComponent(lblMarca)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbMarca, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblModelo)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbModelo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblVersao)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbVersao, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblKm)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtKm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblCor)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtCor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(25, Short.MAX_VALUE))
		);
		panelDadosCarro.setLayout(gl_panelDadosCarro);
		GridBagConstraints gbc_panelDadosCarro = new GridBagConstraints();
		gbc_panelDadosCarro.fill = GridBagConstraints.BOTH;
		gbc_panelDadosCarro.insets = new Insets(0, 0, 5, 5);
		gbc_panelDadosCarro.gridx = 0;
		gbc_panelDadosCarro.gridy = 0;
		panelFormulario.add(panelDadosCarro, gbc_panelDadosCarro);
		
		panelFotosCarro = new JPanel();
		
		picCarro = new JLabel("");
		
		Image iconLogo = new ImageIcon(this.getClass().getResource("/sem_foto_2.jpg")).getImage();
		picCarro.setIcon(new ImageIcon(iconLogo));
		picCarro.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		panelBtnFoto = new JPanel();
		
		panelBtnsFotos = new JPanel();
		GroupLayout gl_panelFotosCarro = new GroupLayout(panelFotosCarro);
		gl_panelFotosCarro.setHorizontalGroup(
			gl_panelFotosCarro.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelFotosCarro.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelFotosCarro.createParallelGroup(Alignment.TRAILING)
						.addComponent(panelBtnsFotos, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panelBtnFoto, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
						.addComponent(picCarro, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panelFotosCarro.setVerticalGroup(
			gl_panelFotosCarro.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelFotosCarro.createSequentialGroup()
					.addContainerGap()
					.addComponent(picCarro, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelBtnFoto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelBtnsFotos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(69, Short.MAX_VALUE))
		);
		panelBtnsFotos.setLayout(new GridLayout(1, 0, 0, 0));
		
		btnPic1 = new JButton("");
		btnPic1.setIcon(new ImageIcon(pic1));
		panelBtnsFotos.add(btnPic1);
		
		btnPic2 = new JButton("");
		btnPic2.setIcon(new ImageIcon(pic2));
		panelBtnsFotos.add(btnPic2);
		
		btnPic3 = new JButton("");
		btnPic3.setIcon(new ImageIcon(pic3));
		panelBtnsFotos.add(btnPic3);
		panelBtnFoto.setLayout(new GridLayout(0, 1, 0, 0));
		
		btnFoto = new JButton("Foto");
		btnFoto.setIcon(new ImageIcon(iconCamera));
		panelBtnFoto.add(btnFoto);
		panelFotosCarro.setLayout(gl_panelFotosCarro);
		GridBagConstraints gbc_panelFotosCarro = new GridBagConstraints();
		gbc_panelFotosCarro.fill = GridBagConstraints.BOTH;
		gbc_panelFotosCarro.insets = new Insets(0, 0, 5, 0);
		gbc_panelFotosCarro.gridx = 1;
		gbc_panelFotosCarro.gridy = 0;
		panelFormulario.add(panelFotosCarro, gbc_panelFotosCarro);
		
		panelDadosAnunciante = new JPanel();
		
		lblAnunciante = new JLabel("Anunciante");
		
		cbAnunciante = new JComboBox<Cliente>();
		
		lblCpfDoAnunciante = new JLabel("CPF do Anunciante");
		
		txtCpfDoAnunciante = new JTextField();
		txtCpfDoAnunciante.setEnabled(false);
		txtCpfDoAnunciante.setColumns(10);
		
		lblEnderecoDoAnunciante = new JLabel("Endereço do Anunciante");
		
		txtEnderecoDoAnunciante = new JTextField();
		txtEnderecoDoAnunciante.setEnabled(false);
		txtEnderecoDoAnunciante.setColumns(10);
		
		lblEmailDoAnunciante = new JLabel("E-Mail do Anunciante");
		
		txtEmailDoAnunciante = new JTextField();
		txtEmailDoAnunciante.setEnabled(false);
		txtEmailDoAnunciante.setColumns(10);
		
		lblDataDeAnuncio = new JLabel("Data de Anuncio");
		
		txtDataDeAnuncio = new JTextField();
		txtDataDeAnuncio.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		txtDataDeAnuncio.setEnabled(false);
		txtDataDeAnuncio.setColumns(10);
		GroupLayout gl_panelDadosAnunciante = new GroupLayout(panelDadosAnunciante);
		gl_panelDadosAnunciante.setHorizontalGroup(
			gl_panelDadosAnunciante.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDadosAnunciante.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelDadosAnunciante.createParallelGroup(Alignment.LEADING)
						.addComponent(cbAnunciante, 0, 220, Short.MAX_VALUE)
						.addComponent(lblAnunciante)
						.addComponent(lblCpfDoAnunciante)
						.addComponent(lblEnderecoDoAnunciante)
						.addComponent(txtEnderecoDoAnunciante, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
						.addComponent(lblEmailDoAnunciante)
						.addComponent(txtEmailDoAnunciante, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
						.addComponent(lblDataDeAnuncio)
						.addComponent(txtDataDeAnuncio, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
						.addComponent(txtCpfDoAnunciante, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panelDadosAnunciante.setVerticalGroup(
			gl_panelDadosAnunciante.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDadosAnunciante.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblAnunciante)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbAnunciante, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblCpfDoAnunciante)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtCpfDoAnunciante, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblEnderecoDoAnunciante)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtEnderecoDoAnunciante, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblEmailDoAnunciante)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtEmailDoAnunciante, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblDataDeAnuncio)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtDataDeAnuncio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(57, Short.MAX_VALUE))
		);
		panelDadosAnunciante.setLayout(gl_panelDadosAnunciante);
		GridBagConstraints gbc_panelDadosAnunciante = new GridBagConstraints();
		gbc_panelDadosAnunciante.fill = GridBagConstraints.BOTH;
		gbc_panelDadosAnunciante.insets = new Insets(0, 0, 0, 5);
		gbc_panelDadosAnunciante.gridx = 0;
		gbc_panelDadosAnunciante.gridy = 1;
		panelFormulario.add(panelDadosAnunciante, gbc_panelDadosAnunciante);
		
		panelDadosComprador = new JPanel();
		
		lblComprador = new JLabel("Comprador");
		
		txtComprador = new JTextField();
		txtComprador.setEnabled(false);
		txtComprador.setColumns(10);
		
		lblCpfDoComprador = new JLabel("CPF do Comprador");
		
		txtCpfDoComprador = new JTextField();
		txtCpfDoComprador.setEnabled(false);
		txtCpfDoComprador.setColumns(10);
		
		lblEnderecoDoComprador = new JLabel("Endereço do Comprador");
		
		txtEnderecoDoComprador = new JTextField();
		txtEnderecoDoComprador.setEnabled(false);
		txtEnderecoDoComprador.setColumns(10);
		
		lblEmailDoComprador = new JLabel("E-Mail do Comprador");
		
		txtEmailDoComprador = new JTextField();
		txtEmailDoComprador.setEnabled(false);
		txtEmailDoComprador.setColumns(10);
		
		lblDataDeVenda = new JLabel("Data de Venda");
		
		txtDataDeVenda = new JTextField();
		txtDataDeVenda.setEnabled(false);
		txtDataDeVenda.setColumns(10);
		GroupLayout gl_panelDadosComprador = new GroupLayout(panelDadosComprador);
		gl_panelDadosComprador.setHorizontalGroup(
			gl_panelDadosComprador.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDadosComprador.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelDadosComprador.createParallelGroup(Alignment.LEADING)
						.addComponent(txtComprador, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
						.addComponent(lblComprador)
						.addComponent(lblCpfDoComprador)
						.addComponent(lblEnderecoDoComprador)
						.addComponent(lblEmailDoComprador)
						.addComponent(lblDataDeVenda)
						.addComponent(txtCpfDoComprador, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
						.addComponent(txtEnderecoDoComprador, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
						.addComponent(txtEmailDoComprador, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
						.addComponent(txtDataDeVenda, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panelDadosComprador.setVerticalGroup(
			gl_panelDadosComprador.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDadosComprador.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblComprador)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtComprador, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblCpfDoComprador)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtCpfDoComprador, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblEnderecoDoComprador)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtEnderecoDoComprador, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblEmailDoComprador)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtEmailDoComprador, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblDataDeVenda)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtDataDeVenda, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(57, Short.MAX_VALUE))
		);
		panelDadosComprador.setLayout(gl_panelDadosComprador);
		GridBagConstraints gbc_panelDadosComprador = new GridBagConstraints();
		gbc_panelDadosComprador.fill = GridBagConstraints.BOTH;
		gbc_panelDadosComprador.gridx = 1;
		gbc_panelDadosComprador.gridy = 1;
		panelFormulario.add(panelDadosComprador, gbc_panelDadosComprador);
		panelBotoes.setLayout(new GridLayout(1, 0, 0, 0));
		
		btnNovo = new JButton("Novo");
		btnNovo.setIcon(new ImageIcon(iconFile));
		panelBotoes.add(btnNovo);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setIcon(new ImageIcon(iconContentSave));
		panelBotoes.add(btnSalvar);
		panelDados.setLayout(gl_panelDados);
		GridBagConstraints gbc_panelDados = new GridBagConstraints();
		gbc_panelDados.fill = GridBagConstraints.BOTH;
		gbc_panelDados.insets = new Insets(0, 0, 0, 5);
		gbc_panelDados.gridx = 0;
		gbc_panelDados.gridy = 0;
		contentPane.add(panelDados, gbc_panelDados);
		
		panelTable = new JPanel();
		panelTable.setBorder(new TitledBorder(null, "Carros Cadastrados", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		scrollPane = new JScrollPane();
		GroupLayout gl_panelTable = new GroupLayout(panelTable);
		gl_panelTable.setHorizontalGroup(
			gl_panelTable.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTable.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panelTable.setVerticalGroup(
			gl_panelTable.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTable.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
					.addContainerGap())
		);
		panelTable.setLayout(gl_panelTable);
		GridBagConstraints gbc_panelTable = new GridBagConstraints();
		gbc_panelTable.fill = GridBagConstraints.BOTH;
		gbc_panelTable.gridx = 1;
		gbc_panelTable.gridy = 0;
		contentPane.add(panelTable, gbc_panelTable);
	}

	private void initListeners() {
		cbMarca.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (listenerMarca) {
		            popularCbModelo();
		        }
			}
		});
		
		cbModelo.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (listenerModelo) {
		            popularCbVersao();
		        }
			}
		});
		
		cbAnunciante.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (listenerCliente) {
		            popularDadosAnunciante();
		        }
			}
		});
		
		btnFoto.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				subirImg();
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
		
		btnNovo.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				novoCarro();
			}
		});
		
		btnSalvar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				salvarCarro();
			}
		});
	}
}
