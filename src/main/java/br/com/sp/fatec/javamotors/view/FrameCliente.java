package br.com.sp.fatec.javamotors.view;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;

import br.com.sp.fatec.javamotors.controller.ClienteController;
import br.com.sp.fatec.javamotors.model.Cliente;
import br.com.sp.fatec.javamotors.model.Endereco;
import br.com.sp.fatec.javamotors.model.Estado;
import br.com.sp.fatec.javamotors.model.Sexo;

import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class FrameCliente extends JDialog {

	private JPanel contentPane;
	private JPanel panelDados;
	private JPanel panel_1;
	private JPanel panelBotoes;
	private JPanel panelFormulario;
	private JButton btnNovo;
	private JButton btnLimpar;
	private JButton btnExcluir;
	private JButton btnSalvar;
	private JPanel panelEast;
	private JPanel panel_2;
	private JLabel lblId;
	private JTextField txtId;
	private JLabel lblNome;
	private JTextField txtNome;
	private JLabel lblCpf;
	private JFormattedTextField frmtdtxtfldCpf;
	private JLabel lblDataDeNascimento;
	private JFormattedTextField frmtdtxtfldDatadenascimento;
	private JLabel lblSexo;
	private JComboBox<Sexo> cbSexo;
	private JLabel lblEmail;
	private JTextField txtEmail;
	private JLabel lblEndereco;
	private JTextField txtEndereco;
	private JLabel lblNumero;
	private JTextField txtNumero;
	private JFormattedTextField frmtdtxtfldCep;
	private JLabel lblCep;
	private JLabel lblCidade;
	private JTextField txtCidade;
	private JLabel lblEstado;
	private JComboBox<Estado> cbEstado;
	private JScrollPane scrollPane;
	private JTable tableCliente;
	
	private boolean frameAbriu;
    private boolean listenerAtivo;
    private ClienteController clienteController;
    
    private Cliente cliente;
    private List<Cliente> lista;
	
	/**
	 * Create the frame.
	 */
	public FrameCliente() {
		initialize();
		initListeners();
		
		clienteController = new ClienteController();
		frameAbriu = true;
        listenerAtivo = true;
        
        atualizarTableCliente();
	}
	
	private void atualizarTableCliente() {
		tableCliente = criarTableCliente();
		scrollPane.setViewportView(tableCliente);
	}
	
	private JTable criarTableCliente() {
		if (frameAbriu)
			frameAbriu = false;
		
		lista = clienteController.index();
		
		String[] headers = {"Cod.", "Nome", "CPF", "Endereço"};
        TableModel model = new DefaultTableModel(headers, lista.size()){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (int i = 0; i < lista.size(); i++) {
            model.setValueAt(lista.get(i).getId(), i, 0);
            model.setValueAt(lista.get(i).getNome(), i, 1);
            model.setValueAt(lista.get(i).getCpf(), i, 2);
            model.setValueAt(enderecoCompleto(lista.get(i)), i, 3);
        }
        
        final JTable retorno = new JTable(model);
        retorno.getColumnModel().getColumn(0).setPreferredWidth(35);
        retorno.getColumnModel().getColumn(1).setPreferredWidth(180);
        retorno.getColumnModel().getColumn(2).setPreferredWidth(100);
        retorno.getColumnModel().getColumn(3).setPreferredWidth(285);
        retorno.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        retorno.getTableHeader().setResizingAllowed(false);
        retorno.getTableHeader().setReorderingAllowed(false);
        
        retorno.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting() && listenerAtivo) {
                    cliente = lista.get(retorno.getSelectedRow());
                    popularCampos();
                }
			}
		});
        
        return retorno;
	}
	
	private String enderecoCompleto(Cliente cliente) {
        String retorno = cliente.getEndereco().getEndereco() + ", " +
                cliente.getEndereco().getNumero() + " - " +
                cliente.getEndereco().getCidade() + " / " + 
                cliente.getEndereco().getEstado();
        return retorno;
    }
	
	private boolean isFormularioValido() {
        if (txtNome.getText().length() <= 1) {
            JOptionPane.showMessageDialog(this, "Insira um nome válido");
            return false;
        } else if (frmtdtxtfldCpf.getText().length() < 11) {
            JOptionPane.showMessageDialog(this, "Insira um CPF válido");
            return false;
        } else if (frmtdtxtfldDatadenascimento.getText().length() < 8) {
            JOptionPane.showMessageDialog(this, "Insira uma data de nascimento válida");
            return false;
        } else if (cbSexo.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um sexo");
            return false;
        } else if (txtEmail.getText().length() <= 5) {
            JOptionPane.showMessageDialog(this, "Insira um e-mail válido");
            return false;
        } else if (txtEndereco.getText().length() <= 3) {
            JOptionPane.showMessageDialog(this, "Insira um endereço válido");
            return false;
        } else if (txtNumero.getText().length() <= 0) {
            JOptionPane.showMessageDialog(this, "Insira um número de endereço válido");
            return false;
        } else if (frmtdtxtfldCep.getText().length() < 8) {
            JOptionPane.showMessageDialog(this, "Insira um CEP válido");
            return false;
        } else if (txtCidade.getText().length() <= 3) {
            JOptionPane.showMessageDialog(this, "Insira uma cidade válida");
            return false;
        } else if (cbEstado.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um estado válido");
            return false;
        } else {
            return true;
        }
    }
    
    private void limpar() {
        txtNome.setText("");
        frmtdtxtfldCpf.setText("");
        frmtdtxtfldDatadenascimento.setText("");
        cbSexo.setSelectedIndex(-1);
        txtEmail.setText("");
        txtEndereco.setText("");
        txtNumero.setText("");
        frmtdtxtfldCep.setText("");
        txtCidade.setText("");
        cbEstado.setSelectedIndex(-1);
    }
    
    private void novoCliente() {
        limpar();
        txtId.setText("");
        listenerAtivo = false;
        tableCliente.getSelectionModel().clearSelection();
        listenerAtivo = true;
        cliente = null;
    }
    
    private Cliente criarCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome(txtNome.getText());
        cliente.setCpf(frmtdtxtfldCpf.getText());
        cliente.setDataNascimento(LocalDate.parse(frmtdtxtfldDatadenascimento.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        cliente.setSexo((Sexo)cbSexo.getSelectedItem());
        cliente.setEmail(txtEmail.getText());
        Endereco endereco = new Endereco();
        endereco.setEndereco(txtEndereco.getText());
        endereco.setNumero(Long.parseLong(txtNumero.getText()));
        endereco.setCep(frmtdtxtfldCep.getText());
        endereco.setCidade(txtCidade.getText());
        endereco.setEstado((Estado)cbEstado.getSelectedItem());
        cliente.setEndereco(endereco);
        return cliente;
    }
    
    private void salvarCliente() {
    	System.out.println(frmtdtxtfldCpf.getText());
        if (isFormularioValido()) {
            if (cliente == null) {
                if (clienteController.create(criarCliente()))
                	JOptionPane.showMessageDialog(this, "Cliente adicionado com sucesso");
            } else {
                editarCliente();
                if (clienteController.update(cliente))
                	JOptionPane.showMessageDialog(this, "Cliente " + cliente.getNome() + " atualizado com sucesso");
            }
            atualizarTableCliente();
            novoCliente();
        }
    }
    
    private void editarCliente() {
    	cliente.setId(Long.parseLong(txtId.getText()));
        cliente.setNome(txtNome.getText());
        cliente.setCpf(frmtdtxtfldCpf.getText());
        cliente.setDataNascimento(LocalDate.parse(frmtdtxtfldDatadenascimento.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        cliente.setSexo((Sexo)cbSexo.getSelectedItem());
        cliente.setEmail(txtEmail.getText());
        Endereco endereco = new Endereco();
        endereco.setEndereco(txtEndereco.getText());
        endereco.setNumero(Long.parseLong(txtNumero.getText()));
        endereco.setCep(frmtdtxtfldCep.getText());
        endereco.setCidade(txtCidade.getText());
        endereco.setEstado((Estado)cbEstado.getSelectedItem());
        cliente.setEndereco(endereco);
    }
    
    private void popularCampos() {
        if (cliente != null) {
            txtId.setText(Long.toString(cliente.getId()));
            txtNome.setText(cliente.getNome());
            frmtdtxtfldCpf.setText(cliente.getCpf());
            frmtdtxtfldDatadenascimento.setText(cliente.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            cbSexo.setSelectedItem(cliente.getSexo());
            txtEmail.setText(cliente.getEmail());
            txtEndereco.setText(cliente.getEndereco().getEndereco());
            txtNumero.setText(Long.toString(cliente.getEndereco().getNumero()));
            frmtdtxtfldCep.setText(cliente.getEndereco().getCep());
            txtCidade.setText(cliente.getEndereco().getCidade());
            cbEstado.setSelectedItem(cliente.getEndereco().getEstado());
        }
    }
    
    private void excluirCliente() {
        if (cliente != null) {
            if (JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o cliente " + cliente.getNome() + "?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (clienteController.destroy(cliente))
                	JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso");
                atualizarTableCliente();
                novoCliente();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para excluí-lo");
        }
    }

	
	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 750, 716);
		setModal(true);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		panelDados = new JPanel();
		panelDados.setBorder(new TitledBorder(null, "Dados do Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Clientes Cadastrados", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		scrollPane = new JScrollPane();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 692, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		panelBotoes = new JPanel();
		
		panelFormulario = new JPanel();
		panelFormulario.setBorder(new LineBorder(Color.LIGHT_GRAY));
		GroupLayout gl_panelDados = new GroupLayout(panelDados);
		gl_panelDados.setHorizontalGroup(
			gl_panelDados.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelDados.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelDados.createParallelGroup(Alignment.TRAILING)
						.addComponent(panelFormulario, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panelBotoes, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panelDados.setVerticalGroup(
			gl_panelDados.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDados.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelBotoes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelFormulario, GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
					.addContainerGap())
		);
		panelBotoes.setLayout(new GridLayout(1, 0, 0, 0));
		
		btnNovo = new JButton("Novo");
		Image iconFile = new ImageIcon(this.getClass().getResource("/file.png")).getImage();
		btnNovo.setIcon(new ImageIcon(iconFile));
		panelBotoes.add(btnNovo);
		
		btnLimpar = new JButton("Limpar");
		Image iconBroom = new ImageIcon(this.getClass().getResource("/broom.png")).getImage();
		btnLimpar.setIcon(new ImageIcon(iconBroom));
		panelBotoes.add(btnLimpar);
		
		btnExcluir = new JButton("Excluir");
		Image iconDeleteForever = new ImageIcon(this.getClass().getResource("/delete-forever.png")).getImage();
		btnExcluir.setIcon(new ImageIcon(iconDeleteForever));
		panelBotoes.add(btnExcluir);
		
		btnSalvar = new JButton("Salvar");
		Image iconContentSave = new ImageIcon(this.getClass().getResource("/content-save.png")).getImage();
		btnSalvar.setIcon(new ImageIcon(iconContentSave));
		panelBotoes.add(btnSalvar);
		
		panelEast = new JPanel();
		
		panel_2 = new JPanel();
		
		lblEndereco = new JLabel("Endereço");
		
		txtEndereco = new JTextField();
		txtEndereco.setColumns(10);
		
		lblNumero = new JLabel("Número");
		
		txtNumero = new JTextField();
		txtNumero.setColumns(10);
		
		MaskFormatter cpfMask = null;
		MaskFormatter dataMask = null;
		MaskFormatter cepMask = null;
		try {
			cpfMask = new MaskFormatter("###.###.###-##");
			dataMask = new MaskFormatter("##/##/####");
			cepMask = new MaskFormatter("#####-###");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		frmtdtxtfldCep = new JFormattedTextField(cepMask);
		
		lblCep = new JLabel("CEP");
		
		lblCidade = new JLabel("Cidade");
		
		txtCidade = new JTextField();
		txtCidade.setColumns(10);
		
		lblEstado = new JLabel("Estado");
		
		cbEstado = new JComboBox<Estado>();
		cbEstado.setModel(new DefaultComboBoxModel<Estado>(Estado.values()));
		cbEstado.setSelectedIndex(-1);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(txtCidade, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
						.addComponent(txtEndereco, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
						.addComponent(lblEndereco)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(txtNumero, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNumero))
							.addGap(9)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(lblCep)
								.addComponent(frmtdtxtfldCep)))
						.addComponent(lblCidade)
						.addComponent(lblEstado)
						.addComponent(cbEstado, 0, 325, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_2.createSequentialGroup()
					.addContainerGap(58, Short.MAX_VALUE)
					.addComponent(lblEndereco)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtEndereco, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNumero)
						.addComponent(lblCep))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtNumero, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(frmtdtxtfldCep, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblCidade)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtCidade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblEstado)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbEstado, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panel_2.setLayout(gl_panel_2);
		panelFormulario.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblId = new JLabel("ID");
		
		txtId = new JTextField();
		txtId.setEnabled(false);
		txtId.setColumns(10);
		
		lblNome = new JLabel("Nome");
		
		txtNome = new JTextField();
		txtNome.setColumns(10);
		
		lblCpf = new JLabel("CPF");
		
		
		
		frmtdtxtfldCpf = new JFormattedTextField(cpfMask);
		
		lblDataDeNascimento = new JLabel("Data de Nascimento");
		
		frmtdtxtfldDatadenascimento = new JFormattedTextField(dataMask);
		
		lblSexo = new JLabel("Sexo");
		
		cbSexo = new JComboBox<Sexo>();
		cbSexo.setModel(new DefaultComboBoxModel<Sexo>(Sexo.values()));
		cbSexo.setSelectedIndex(-1);
		
		lblEmail = new JLabel("E-Mail");
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		GroupLayout gl_panelEast = new GroupLayout(panelEast);
		gl_panelEast.setHorizontalGroup(
			gl_panelEast.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelEast.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelEast.createParallelGroup(Alignment.LEADING)
						.addComponent(txtEmail, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
						.addComponent(txtNome, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
						.addComponent(frmtdtxtfldCpf, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
						.addGroup(gl_panelEast.createSequentialGroup()
							.addGroup(gl_panelEast.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblId)
								.addComponent(txtId, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNome)
								.addComponent(lblCpf)
								.addComponent(lblDataDeNascimento)
								.addComponent(frmtdtxtfldDatadenascimento, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
							.addGap(7)
							.addGroup(gl_panelEast.createParallelGroup(Alignment.LEADING)
								.addComponent(cbSexo, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblSexo)))
						.addComponent(lblEmail))
					.addContainerGap())
		);
		gl_panelEast.setVerticalGroup(
			gl_panelEast.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelEast.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblId)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNome)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblCpf)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(frmtdtxtfldCpf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelEast.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDataDeNascimento)
						.addComponent(lblSexo))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelEast.createParallelGroup(Alignment.BASELINE)
						.addComponent(frmtdtxtfldDatadenascimento, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbSexo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblEmail)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(29, Short.MAX_VALUE))
		);
		panelEast.setLayout(gl_panelEast);
		panelFormulario.add(panelEast);
		panelFormulario.add(panel_2);
		panelDados.setLayout(gl_panelDados);
		contentPane.add(panelDados);
		contentPane.add(panel_1);
	}

	private void initListeners() {
		btnNovo.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				novoCliente();
			}
		});
		
		btnLimpar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		
		btnExcluir.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				excluirCliente();
			}
		});
		
		btnSalvar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				salvarCliente();
			}
		});
	}
}
