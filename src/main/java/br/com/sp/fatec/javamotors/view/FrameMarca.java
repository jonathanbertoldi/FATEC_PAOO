package br.com.sp.fatec.javamotors.view;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import br.com.sp.fatec.javamotors.controller.IOController;
import br.com.sp.fatec.javamotors.controller.MarcaController;
import br.com.sp.fatec.javamotors.model.Marca;

import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class FrameMarca extends JDialog {

	private final String MARCA_PATH = IOController.urlImagens() + "marca/";
	
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
	private JTable tableMarcas;

	private boolean frameAbriu;
	private boolean tableListenerAtivo;
	private List<Marca> marcas;
	private Marca marca;

	private MarcaController marcaController;
	
	/**
	 * Create the frame.
	 */
	public FrameMarca() {
		initialize();
		initListeners();
		
		marcaController = new MarcaController();
		
		tableListenerAtivo = true;
		frameAbriu = true;
		
		atualizarTableMarcas();
	}
	
	private JTable criarTableMarcas() {
		marcas = marcaController.index();
		if (frameAbriu) {
			frameAbriu = false;
		}
		
		String[] headers = {"Cod.", "Nome", "País de Origem"};
		TableModel model = new DefaultTableModel(headers, marcas.size()) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		for (int i = 0; i < marcas.size(); i++) {
            model.setValueAt(marcas.get(i).getId(), i, 0);
            model.setValueAt(marcas.get(i).getNome(), i, 1);
            model.setValueAt(marcas.get(i).getPais(), i, 2);
        }
        
        final JTable retorno = new JTable(model);
        retorno.getColumnModel().getColumn(0).setPreferredWidth(35);
        retorno.getColumnModel().getColumn(1).setPreferredWidth(150);
        retorno.getColumnModel().getColumn(2).setPreferredWidth(100);
        retorno.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        retorno.getTableHeader().setResizingAllowed(false);
        retorno.getTableHeader().setReorderingAllowed(false);
        
        retorno.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && tableListenerAtivo) {
                    marca = marcas.get(retorno.getSelectedRow());
                    popularCampos();
                }
            }
        });
        return retorno;
	}

	private void atualizarTableMarcas() {
		tableMarcas = criarTableMarcas();
		scrollPane.setViewportView(tableMarcas);
	}

	private boolean isFormularioValido(){
        if (txtNome.getText().length() < 1){
            JOptionPane.showMessageDialog(this, "Insira um nome válido para a marca");
            return false;
        } else if (txtPaisDeOrigem.getText().length() < 1) {
            JOptionPane.showMessageDialog(this, "Insira um país válido para a marca");
            return false;
        } else {
            return true;
        }
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
                lblPiclogo.setIcon(new ImageIcon(bi.getScaledInstance(194, 194, Image.SCALE_DEFAULT)));
            } else {
                JOptionPane.showMessageDialog(this, "Formato de arquivo inválido.\nDeve ser .jpg, .jpeg, .png ou .bmp");
            }
        } catch (IOException e) {
            System.out.println("Deu bosta na hora de subir a img chefe.");
        } catch (Exception e) {
            System.out.println("Deu bosta na hora de subir a img chefe.");
        }
    }
    
    private void limparImg() {
    	lblPiclogo.setIcon(new ImageIcon(getClass().getResource("/sem_foto.png")));
    }
    
    private void limparTudo() {
        limparImg();
        txtNome.setText("");
        txtPaisDeOrigem.setText("");
    }
    
    private void novaMarca() {
        marca = null;
        txtId.setText("");
        tableListenerAtivo = false;
        tableMarcas.clearSelection();
        tableListenerAtivo = true;
        limparTudo();
    }
    
    private void salvarImg(String nome) {
        try {
            BufferedImage bi = new BufferedImage(
            		lblPiclogo.getIcon().getIconWidth(),
            		lblPiclogo.getIcon().getIconHeight(),
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = bi.createGraphics();
            lblPiclogo.getIcon().paintIcon(null, g, 0, 0);
            g.dispose();
            ImageIO.write(bi, "jpg", new File(MARCA_PATH + nome + ".jpg"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private Marca criarMarca() {
        Marca m = new Marca();
        m.setNome(txtNome.getText());
        m.setPais(txtPaisDeOrigem.getText());
        m.setLogo(MARCA_PATH + m.getNome() + ".jpg");
        salvarImg(m.getNome());
        return m;
    }
    
    private void salvarMarca() {
        if (isFormularioValido()) {
            if (marca == null) {
                if (marcaController.create(criarMarca()))
                	JOptionPane.showMessageDialog(this, "Marca adicionada com sucesso");
            } else {
            	editarMarca();
                if (marcaController.update(marca))
                	JOptionPane.showMessageDialog(this, "Marca " + marca.getNome() + " atualizada com sucesso");
            }
            atualizarTableMarcas();
            novaMarca();
        }
    }
    
    private void apagarImgVelha() {
        try {
            File f = new File(marca.getLogo());
            System.out.println(f.getPath());
            f.delete();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void editarMarca() {
        apagarImgVelha();
        marca.setId(Long.parseLong(txtId.getText()));
        marca.setNome(txtNome.getText());
        marca.setPais(txtPaisDeOrigem.getText());
        marca.setLogo(MARCA_PATH + marca.getNome() + ".jpg");
        salvarImg(marca.getNome());
    }
    
    private void abrirImg() {
        try {
            BufferedImage bi = ImageIO.read(new File(marca.getLogo()));
            lblPiclogo.setIcon(new ImageIcon(bi.getScaledInstance(194, 194, Image.SCALE_DEFAULT)));
        } catch (IOException e) {
            System.out.println("Deu bosta na hora de subir a img chefe.");
        }
    }
    
    private void popularCampos() {
        if (marca != null) {
            txtId.setText(marca.getId().toString());
            txtNome.setText(marca.getNome());
            txtPaisDeOrigem.setText(marca.getPais());
            abrirImg();
        }
    }
    
    private void excluirMarca() {
        if (marca != null) {
            if (JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir a marca " + marca.getNome() + "?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (marcaController.destroy(marca))
                	JOptionPane.showMessageDialog(this, "Marca excluída com sucesso");
                atualizarTableMarcas();
                novaMarca();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma marca para excluí-la");
        }
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
		txtId.setEnabled(false);
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
		Image iconLogo = new ImageIcon(this.getClass().getResource("/sem_foto.png")).getImage();
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

	private void initListeners() {
		btnAddimagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				subirImg();
			}
		});
		
		btnRmimagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparImg();
			}
		});
		
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparTudo();
			}
		});
		
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				novaMarca();
			}
		});
		
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvarMarca();
			}
		});
		
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirMarca();
			}
		});
	}
}
