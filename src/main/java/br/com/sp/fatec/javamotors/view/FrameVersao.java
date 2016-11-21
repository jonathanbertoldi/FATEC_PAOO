package br.com.sp.fatec.javamotors.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import net.miginfocom.swing.MigLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import br.com.sp.fatec.javamotors.controller.MarcaController;
import br.com.sp.fatec.javamotors.controller.ModeloController;
import br.com.sp.fatec.javamotors.controller.VersaoController;
import br.com.sp.fatec.javamotors.model.Cambio;
import br.com.sp.fatec.javamotors.model.Combustivel;
import br.com.sp.fatec.javamotors.model.Marca;
import br.com.sp.fatec.javamotors.model.Modelo;
import br.com.sp.fatec.javamotors.model.Versao;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class FrameVersao extends JDialog {

	private JPanel contentPane;
	private JPanel panelDados;
	private JPanel panelTable;
	private JPanel panelFormulario;
	private JPanel panelBotoes;
	private JButton btnNovo;
	private JButton btnLimpar;
	private JButton btnExcluir;
	private JButton btnSalvar;
	private JScrollPane scrollPane;
	private JPanel panelWest;
	private JPanel panelEast;
	private JLabel lblId;
	private JTextField textField;
	private JLabel lblMotor;
	private JTextField txtMotor;
	private JLabel lblCilindradas;
	private JTextField txtCilindradas;
	private JLabel lblPortas;
	private JTextField txtPortas;
	private JTextField txtValvulas;
	private JLabel lblValvulas;
	private JLabel lblMarca;
	private JComboBox<Marca> cbMarca;
	private JLabel lblModelo;
	private JComboBox<Modelo> cbModelo;
	private JLabel lblCambio;
	private JComboBox<Cambio> cbCambio;
	private JLabel lblCombustivel;
	private JComboBox<Combustivel> cbCombustivel;
	
	private JTable tableVersoes;

	
	private boolean frameAbriu;
    private boolean listenerAtivo = false;
    private boolean tableListenerAtivo;
    private MarcaController marcaController;
    private ModeloController modeloController;
    private VersaoController versaoController;
    
    private Versao versao;
    private List<Versao> lista;
	
	/**
	 * Create the frame.
	 */
	public FrameVersao() {
		initialize();
		initListeners();
		
		marcaController = new MarcaController();
		modeloController = new ModeloController();
		versaoController = new VersaoController();
		
		frameAbriu = true;
		tableListenerAtivo = true;
		
		popularCbMarcas();
		atualizarTableVersoes();
	}
	
	private void popularCbMarcas() {
		for (Marca marca : marcaController.index()) {
            cbMarca.addItem(marca);
        }
        cbMarca.setSelectedIndex(-1);
        cbMarca.setMaximumRowCount(10);
        listenerAtivo = true;
	}
	
	private void popularCbModelos(Marca filtro) {
		if (listenerAtivo) {
            for (Modelo modelo : modeloController.findByMarca(filtro)) {
                cbModelo.addItem(modelo);
            }
        }
		cbModelo.setSelectedIndex(-1);
	}
	
	private void atualizarTableVersoes() {
		tableVersoes = criarTableVersoes();
		scrollPane.setViewportView(tableVersoes);
	}
	
	private JTable criarTableVersoes() {
        lista = versaoController.index();
		
		if (frameAbriu) {
            frameAbriu = false;
        }
        
        String[] headers = {"Cod.", "Marca", "Modelo", "Ano", "Versão"};
        TableModel model = new DefaultTableModel(headers, lista.size()){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (int i = 0; i < lista.size(); i++) {
            model.setValueAt(lista.get(i).getId(), i, 0);
            model.setValueAt(lista.get(i).getModelo().getMarca().getNome(), i, 1);
            model.setValueAt(lista.get(i).getModelo().getNome(), i, 2);
            model.setValueAt(lista.get(i).getModelo().getAno(), i, 3);
            model.setValueAt(gerarStringVersao(lista.get(i)), i, 4);
        }
        
        final JTable retorno = new JTable(model);
        retorno.getColumnModel().getColumn(0).setPreferredWidth(35);
        retorno.getColumnModel().getColumn(1).setPreferredWidth(80);
        retorno.getColumnModel().getColumn(2).setPreferredWidth(80);
        retorno.getColumnModel().getColumn(3).setPreferredWidth(60);
        retorno.getColumnModel().getColumn(4).setPreferredWidth(345);
        retorno.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        retorno.getTableHeader().setResizingAllowed(false);
        retorno.getTableHeader().setReorderingAllowed(false);
        
        retorno.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			public void valueChanged(ListSelectionEvent e) {
				if (tableListenerAtivo) {
                    versao = lista.get(retorno.getSelectedRow());
                    popularCampos();
                }
			}
		});
        
        return retorno;
	}
	
	private String gerarStringVersao(Versao versao) {
        String retorno = Float.toString(versao.getCilindradas() / 1000) + " " +
                versao.getNomeMotor() + " " +
                Integer.toString(versao.getValvulas()) + "V " +
                versao.getCombustivel() + " " +
                Integer.toString(versao.getPortas()) + "P " +
                versao.getCambio();
        return retorno;
    }
	
	private boolean isFormularioValido() {
        if (cbModelo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione um modelo");
            return false;
        } else if (txtMotor.getText().length() <= 0) {
            JOptionPane.showMessageDialog(this, "Insira um nome válido para o motor");
            return false;
        } else if (txtCilindradas.getText().length() <= 0) {
            JOptionPane.showMessageDialog(this, "Selecione um número válido de Cilindradas (CC)");
            return false;
        } else if (txtValvulas.getText().length() <= 0) {
            JOptionPane.showMessageDialog(this, "Insira um número válido de válvulas");
            return false;
        } else if (txtPortas.getText().length() <= 0) {
            JOptionPane.showMessageDialog(this, "Insira um número válido de portas");
            return false;
        } else if (cbCambio.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um tipo de câmbio");
            return false;
        } else if (cbCombustivel.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um tipo de combustível");
            return false;
        } else {
            return true;
        }
    }
    
    private void limpar() {
        listenerAtivo = false;
        cbMarca.setSelectedIndex(-1);
        cbModelo.setSelectedIndex(-1);
        listenerAtivo = true;
        txtMotor.setText("");
        txtCilindradas.setText("");
        txtValvulas.setText("");
        txtPortas.setText("");
        cbCambio.setSelectedIndex(-1);
        cbCombustivel.setSelectedIndex(-1);
    }
    
    private void novaVersao() {
        limpar();
        textField.setText("");
        tableListenerAtivo = false;
        tableVersoes.getSelectionModel().clearSelection();
        tableListenerAtivo = true;
        versao = null;
    }
    
    private Versao criarVersao() {
        Versao versao = new Versao();
        versao.setCilindradas(Float.parseFloat(txtCilindradas.getText()));
        versao.setNomeMotor(txtMotor.getText());
        versao.setValvulas(Integer.parseInt(txtValvulas.getText()));
        versao.setPortas(Integer.parseInt(txtPortas.getText()));
        versao.setCambio((Cambio)cbCambio.getSelectedItem());
        versao.setCombustivel((Combustivel)cbCombustivel.getSelectedItem());
        versao.setModelo((Modelo)cbModelo.getSelectedItem());
        return versao;
    }
    
    private void salvarVersao() {
        if (isFormularioValido()) {
            if (versao == null) {
                if (versaoController.create(criarVersao()))
                	JOptionPane.showMessageDialog(this, "Versão adicionada com sucesso");
            } else {
                editarVersao();
                if (versaoController.update(versao))
                	JOptionPane.showMessageDialog(this, "Versão " + versao.getModelo().toString() + ": " + gerarStringVersao(versao) + " atualizada com sucesso");
            }
            atualizarTableVersoes();
            novaVersao();
        }
    }
    
    private void editarVersao() {
        versao.setId(Long.parseLong(textField.getText()));
        versao.setCilindradas(Float.parseFloat(txtCilindradas.getText()));
        versao.setNomeMotor(txtMotor.getText());
        versao.setValvulas(Integer.parseInt(txtValvulas.getText()));
        versao.setPortas(Integer.parseInt(txtPortas.getText()));
        versao.setCambio((Cambio)cbCambio.getSelectedItem());
        versao.setCombustivel((Combustivel)cbCombustivel.getSelectedItem());
        versao.setModelo((Modelo)cbModelo.getSelectedItem());
    }
    
    private void popularCbMarca() {
        for (int i = 0; i < cbMarca.getItemCount(); i++) {
            if (cbMarca.getItemAt(i).getId() == versao.getModelo().getMarca().getId())
                cbMarca.setSelectedIndex(i);
        }
    }
    
    private void popularCbModelo() {
        for (int i = 0; i < cbModelo.getItemCount(); i++) {
            if (cbModelo.getItemAt(i).getId() == versao.getModelo().getId())
                cbModelo.setSelectedIndex(i);
        }
    }
    
    private void popularCampos() {
        if (versao != null) {
            textField.setText(Float.toString(versao.getId()));
            popularCbMarca();
            popularCbModelo();
            txtMotor.setText(versao.getNomeMotor());
            txtCilindradas.setText(versao.getCilindradas().toString());
            txtValvulas.setText(Integer.toString(versao.getValvulas()));
            txtPortas.setText(Integer.toString(versao.getPortas()));
            cbCambio.setSelectedItem(versao.getCambio());
            cbCombustivel.setSelectedItem(versao.getCombustivel());
        }
    }
    
    private void excluirVersao() {
        if (versao != null) {
            if (JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir a versao " + versao.getModelo().toString() + ": " + gerarStringVersao(versao) + "?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (versaoController.destroy(versao))
                	JOptionPane.showMessageDialog(this, "Versao excluída com sucesso");
                atualizarTableVersoes();
                novaVersao();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma versao para excluí-la");
        }
    }
	
	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 626);
		setLocationRelativeTo(null);
		setModal(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		panelDados = new JPanel();
		panelDados.setBorder(new TitledBorder(null, "Dados da Vers\u00E3o", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		panelTable = new JPanel();
		panelTable.setBorder(new TitledBorder(null, "Vers\u00F5es Cadastradas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		scrollPane = new JScrollPane();
		GroupLayout gl_panelTable = new GroupLayout(panelTable);
		gl_panelTable.setHorizontalGroup(
			gl_panelTable.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTable.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panelTable.setVerticalGroup(
			gl_panelTable.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTable.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
					.addContainerGap())
		);
		panelTable.setLayout(gl_panelTable);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		contentPane.add(panelDados);
		
		panelFormulario = new JPanel();
		panelFormulario.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		panelWest = new JPanel();
		
		panelEast = new JPanel();
		
		lblMarca = new JLabel("Marca");
		
		cbMarca = new JComboBox<Marca>();
		
		lblModelo = new JLabel("Modelo");
		
		cbModelo = new JComboBox<Modelo>();
		
		lblCambio = new JLabel("Câmbio");
		
		// TODO: lembrar de alterar isso aqui
		
		cbCambio = new JComboBox<Cambio>(Cambio.values());
		cbCambio.setSelectedIndex(-1);
		
		lblCombustivel = new JLabel("Combustível");
		
		cbCombustivel = new JComboBox<Combustivel>(Combustivel.values());
		cbCombustivel.setSelectedIndex(-1);
		GroupLayout gl_panelEast = new GroupLayout(panelEast);
		gl_panelEast.setHorizontalGroup(
			gl_panelEast.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelEast.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelEast.createParallelGroup(Alignment.LEADING)
						.addComponent(cbMarca, 0, 300, Short.MAX_VALUE)
						.addComponent(lblMarca)
						.addComponent(lblModelo)
						.addComponent(lblCambio)
						.addComponent(lblCombustivel)
						.addComponent(cbModelo, 0, 300, Short.MAX_VALUE)
						.addComponent(cbCambio, 0, 300, Short.MAX_VALUE)
						.addComponent(cbCombustivel, 0, 300, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panelEast.setVerticalGroup(
			gl_panelEast.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelEast.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblMarca)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbMarca, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblModelo)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbModelo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblCambio)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbCambio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblCombustivel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbCombustivel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(13, Short.MAX_VALUE))
		);
		panelEast.setLayout(gl_panelEast);
		
		lblId = new JLabel("ID");
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setColumns(10);
		
		lblMotor = new JLabel("Motor");
		
		txtMotor = new JTextField();
		txtMotor.setColumns(10);
		
		lblCilindradas = new JLabel("Cilindradas");
		
		txtCilindradas = new JTextField();
		txtCilindradas.setColumns(10);
		
		lblPortas = new JLabel("Portas");
		
		txtPortas = new JTextField();
		txtPortas.setColumns(10);
		
		txtValvulas = new JTextField();
		txtValvulas.setColumns(10);
		
		lblValvulas = new JLabel("Válvulas");
		GroupLayout gl_panelWest = new GroupLayout(panelWest);
		gl_panelWest.setHorizontalGroup(
			gl_panelWest.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelWest.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelWest.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelWest.createSequentialGroup()
							.addGroup(gl_panelWest.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(lblId, Alignment.LEADING)
								.addComponent(textField, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblMotor, Alignment.LEADING)
								.addComponent(lblCilindradas, Alignment.LEADING)
								.addComponent(lblPortas, Alignment.LEADING)
								.addComponent(txtPortas, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panelWest.createParallelGroup(Alignment.LEADING)
								.addComponent(lblValvulas)
								.addComponent(txtValvulas, GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE))
							.addContainerGap())
						.addGroup(Alignment.TRAILING, gl_panelWest.createSequentialGroup()
							.addGroup(gl_panelWest.createParallelGroup(Alignment.TRAILING)
								.addComponent(txtMotor, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
								.addComponent(txtCilindradas, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
							.addContainerGap())))
		);
		gl_panelWest.setVerticalGroup(
			gl_panelWest.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelWest.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblId)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblMotor)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtMotor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblCilindradas)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtCilindradas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelWest.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPortas)
						.addComponent(lblValvulas))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelWest.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtPortas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtValvulas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(13, Short.MAX_VALUE))
		);
		panelWest.setLayout(gl_panelWest);
		
		panelBotoes = new JPanel();
		GroupLayout gl_panelDados = new GroupLayout(panelDados);
		gl_panelDados.setHorizontalGroup(
			gl_panelDados.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelDados.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelDados.createParallelGroup(Alignment.TRAILING)
						.addComponent(panelFormulario, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE)
						.addComponent(panelBotoes, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panelDados.setVerticalGroup(
			gl_panelDados.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDados.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelBotoes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelFormulario, GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
					.addContainerGap())
		);
		panelFormulario.setLayout(new GridLayout(0, 2, 0, 0));
		panelFormulario.add(panelWest);
		panelFormulario.add(panelEast);
		panelBotoes.setLayout(new GridLayout(1, 0, 0, 0));
		
		btnNovo = new JButton("Novo");
		Image iconFile = new ImageIcon(this.getClass().getResource("/file.png")).getImage();
		btnNovo.setIcon(new ImageIcon(iconFile));
		panelBotoes.add(btnNovo);
		
		btnLimpar = new JButton("Limpar");
		Image iconBroom = new ImageIcon(this.getClass().getResource("/broom.png")).getImage();
		btnLimpar.setIcon(new ImageIcon(iconBroom));
		panelBotoes.add(btnLimpar);
		
		btnExcluir = new JButton("Deletar");
		Image iconDeleteForever = new ImageIcon(this.getClass().getResource("/delete-forever.png")).getImage();
		btnExcluir.setIcon(new ImageIcon(iconDeleteForever));
		panelBotoes.add(btnExcluir);
		
		btnSalvar = new JButton("Salvar");
		Image iconContentSave = new ImageIcon(this.getClass().getResource("/content-save.png")).getImage();
		btnSalvar.setIcon(new ImageIcon(iconContentSave));
		panelBotoes.add(btnSalvar);
		panelDados.setLayout(gl_panelDados);
		contentPane.add(panelTable);
	}

	private void initListeners() {
		cbMarca.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Marca m = (Marca)cbMarca.getSelectedItem();
				cbModelo.removeAllItems();
				popularCbModelos(m);
			}
		});
		
		btnLimpar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		
		btnNovo.addActionListener(new ActionListener() {
					
			public void actionPerformed(ActionEvent e) {
				novaVersao();
			}
		});
		
		btnSalvar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				salvarVersao();
			}
		});
		
		btnExcluir.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				excluirVersao();
			}
		});
	}
}
