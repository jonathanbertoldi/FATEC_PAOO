package br.com.sp.fatec.javamotors.view;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import br.com.sp.fatec.javamotors.controller.MarcaController;
import br.com.sp.fatec.javamotors.controller.ModeloController;
import br.com.sp.fatec.javamotors.model.Marca;
import br.com.sp.fatec.javamotors.model.Modelo;

import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class FrameModelo extends JDialog {

	private JPanel contentPane;
	private JPanel panelDados;
	private JPanel panelTable;
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
	private JLabel lblAno;
	private JLabel lblMarca;
	private JComboBox<Marca> cbMarca;
	private JScrollPane scrollPane;
	private JComboBox<Long> cbAno;
	private JTable tableModelos;
	
	private boolean tableListenerAtivo;
	private ModeloController modeloController;
	
	private Modelo modelo;
	private List<Modelo> modelos;
	

	/**
	 * Create the frame.
	 */
	public FrameModelo() {
		initialize();
		initListeners();
		
		tableListenerAtivo = true;
		modeloController = new ModeloController();
		
		popularCbAno();
		popularCbMarcas();
		
		atualizarTableModelos();
	}
	
	private void popularCbAno() {
		for (int i = LocalDate.now().getYear(); i >= 1930; i--) {
            cbAno.addItem(new Long(i));
        }
		cbAno.setSelectedIndex(-1);
	}
	
	private void popularCbMarcas() {
		for (Marca marca : new MarcaController().index()) {
			cbMarca.addItem(marca);
		}
		cbMarca.setSelectedIndex(-1);
	}
	
	private void atualizarTableModelos() {
		tableModelos = criarTableModelos();
		scrollPane.setViewportView(tableModelos);
	}
	
	private JTable criarTableModelos() {
		modelos = modeloController.index();
		
		String[] headers = {"Cod.", "Nome", "Ano", "Marca"};
        TableModel model = new DefaultTableModel(headers, modelos.size()){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
        };
        
        for (int i = 0; i < modelos.size(); i++) {
            model.setValueAt(modelos.get(i).getId(), i, 0);
            model.setValueAt(modelos.get(i).getNome(), i, 1);
            model.setValueAt(modelos.get(i).getAno(), i, 2);
            model.setValueAt(modelos.get(i).getMarca().getNome(), i, 3);
        }
        
        final JTable retorno = new JTable(model);
        retorno.getColumnModel().getColumn(0).setPreferredWidth(35);
        retorno.getColumnModel().getColumn(1).setPreferredWidth(123);
        retorno.getColumnModel().getColumn(2).setPreferredWidth(50);
        retorno.getColumnModel().getColumn(3).setPreferredWidth(80);
        retorno.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        retorno.getTableHeader().setResizingAllowed(false);
        retorno.getTableHeader().setReorderingAllowed(false);
        
        
        retorno.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting() && tableListenerAtivo) {
                    modelo = modelos.get(retorno.getSelectedRow());
                    popularCampos();
                }
			}
		});
        return retorno;
	}
	
	private boolean isFormularioValido() {
        if (txtNome.getText().length() < 1){
           JOptionPane.showMessageDialog(this, "Insira um nome válido para o modelo");
           return false;
        } else {
            return true;
        }
    }
    
    private void limpar() {
        txtNome.setText("");
        cbAno.setSelectedIndex(-1);
        cbMarca.setSelectedIndex(-1);
    }
    
    private void novoModelo() {
        limpar();
        txtId.setText("");
        tableListenerAtivo = false;
        tableModelos.getSelectionModel().clearSelection();
        tableListenerAtivo = true;
        modelo = null;
    }
    
    private Modelo criarModelo() {
        Modelo modelo = new Modelo();
        modelo.setNome(txtNome.getText());
        modelo.setAno((Long)cbAno.getSelectedItem());
        modelo.setMarca((Marca)cbMarca.getSelectedItem());
        return modelo;
    }
    
    private void salvarModelo() {
        if (isFormularioValido()) {
            if (modelo == null) {
                if (modeloController.create(criarModelo()))	
                	JOptionPane.showMessageDialog(this, "Modelo adicionado com sucesso");
            } else {
                editarModelo();
                if (modeloController.update(modelo))
                	JOptionPane.showMessageDialog(this, "Modelo " + modelo.getNome() + " atualizado com sucesso");
            }
            atualizarTableModelos();
            novoModelo();
        }
    }
    
    private void editarModelo() {
        modelo.setId(Long.parseLong(txtId.getText()));
        modelo.setNome(txtNome.getText());
        modelo.setAno((Long)cbAno.getSelectedItem());
        modelo.setMarca((Marca)cbMarca.getSelectedItem());
    }
    
    private boolean compararMarca(int pos) {
        if (cbMarca.getItemAt(pos).getId() == modelo.getMarca().getId() &&
                cbMarca.getItemAt(pos).getNome().equals(modelo.getMarca().getNome()) &&
                cbMarca.getItemAt(pos).getPais().equals(modelo.getMarca().getPais()) &&
                cbMarca.getItemAt(pos).getLogo().equals(modelo.getMarca().getLogo()))
            return true;
        return false;
    }
    
    private void popularCampos() {
        if (modelo != null) {
            txtId.setText(Long.toString(modelo.getId()));
            txtNome.setText(modelo.getNome());
            cbAno.setSelectedItem(modelo.getAno());
            for (int i = 0; i < cbMarca.getItemCount(); i++) {
                if (compararMarca(i)) {
                    cbMarca.setSelectedIndex(i);
                }
            }
            cbMarca.setSelectedItem(modelo.getMarca());
        }
    }
    
    private void excluirModelo() {
        if (modelo != null) {
            if (JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o modelo " + modelo.getNome() + "?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (modeloController.destroy(modelo))
                	JOptionPane.showMessageDialog(this, "Modelo excluído com sucesso");
                atualizarTableModelos();
                novoModelo();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um modelo para excluí-lo");
        }
    }
	
	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 650, 334);
		setLocationRelativeTo(null);
		setModal(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		panelDados = new JPanel();
		panelDados.setBorder(new TitledBorder(null, "Dados do Modelo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		panelTable = new JPanel();
		panelTable.setBorder(new TitledBorder(null, "Modelos Cadastrados", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		scrollPane = new JScrollPane();
		GroupLayout gl_panelTable = new GroupLayout(panelTable);
		gl_panelTable.setHorizontalGroup(
			gl_panelTable.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTable.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panelTable.setVerticalGroup(
			gl_panelTable.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTable.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
					.addContainerGap())
		);
		panelTable.setLayout(gl_panelTable);
		contentPane.setLayout(new GridLayout(0, 2, 0, 0));
		
		panelBotoes = new JPanel();
		
		panelFormulario = new JPanel();
		panelFormulario.setBorder(new LineBorder(Color.LIGHT_GRAY));
		GroupLayout gl_panelDados = new GroupLayout(panelDados);
		gl_panelDados.setHorizontalGroup(
			gl_panelDados.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelDados.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelDados.createParallelGroup(Alignment.TRAILING)
						.addComponent(panelFormulario, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
						.addComponent(panelBotoes, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panelDados.setVerticalGroup(
			gl_panelDados.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDados.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelBotoes, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelFormulario, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		lblId = new JLabel("ID");
		
		txtId = new JTextField();
		txtId.setEnabled(false);
		txtId.setColumns(10);
		
		lblNome = new JLabel("Nome");
		
		txtNome = new JTextField();
		txtNome.setColumns(10);
		
		lblAno = new JLabel("Ano");
		
		lblMarca = new JLabel("Marca");
		
		cbMarca = new JComboBox();
		
		cbAno = new JComboBox();
		GroupLayout gl_panelFormulario = new GroupLayout(panelFormulario);
		gl_panelFormulario.setHorizontalGroup(
			gl_panelFormulario.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelFormulario.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelFormulario.createParallelGroup(Alignment.LEADING)
						.addComponent(txtNome, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
						.addComponent(lblId)
						.addComponent(txtId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNome)
						.addComponent(lblAno)
						.addComponent(lblMarca)
						.addComponent(cbMarca, 0, 258, Short.MAX_VALUE)
						.addComponent(cbAno, 0, 258, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panelFormulario.setVerticalGroup(
			gl_panelFormulario.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelFormulario.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblId)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNome)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblAno)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbAno, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblMarca)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbMarca, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(12, Short.MAX_VALUE))
		);
		panelFormulario.setLayout(gl_panelFormulario);
		
		btnNovo = new JButton("");
		Image iconFile = new ImageIcon(this.getClass().getResource("/file.png")).getImage();
		btnNovo.setIcon(new ImageIcon(iconFile));
		
		btnLimpar = new JButton("");
		Image iconBroom = new ImageIcon(this.getClass().getResource("/broom.png")).getImage();
		btnLimpar.setIcon(new ImageIcon(iconBroom));
		
		btnExcluir = new JButton("");
		Image iconDeleteForever = new ImageIcon(this.getClass().getResource("/delete-forever.png")).getImage();
		btnExcluir.setIcon(new ImageIcon(iconDeleteForever));
		
		btnSalvar = new JButton("");
		Image iconContentSave = new ImageIcon(this.getClass().getResource("/content-save.png")).getImage();
		btnSalvar.setIcon(new ImageIcon(iconContentSave));
		panelBotoes.setLayout(new GridLayout(0, 4, 0, 0));
		panelBotoes.add(btnNovo);
		panelBotoes.add(btnLimpar);
		panelBotoes.add(btnExcluir);
		panelBotoes.add(btnSalvar);
		panelDados.setLayout(gl_panelDados);
		contentPane.add(panelDados);
		contentPane.add(panelTable);
	}

	private void initListeners() {
		btnLimpar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		
		btnNovo.addActionListener(new ActionListener() {
					
			public void actionPerformed(ActionEvent e) {
				novoModelo();
			}
		});
		
		btnSalvar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				salvarModelo();
			}
		});
		
		btnExcluir.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				excluirModelo();
			}
		});
	}
}
