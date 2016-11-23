package br.com.sp.fatec.javamotors.view;

import java.awt.BorderLayout;
import java.awt.Desktop;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.TitledBorder;

import br.com.sp.fatec.javamotors.controller.ClienteController;
import br.com.sp.fatec.javamotors.controller.ContratoController;
import br.com.sp.fatec.javamotors.model.Carro;
import br.com.sp.fatec.javamotors.model.Cliente;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JDialog;

public class FrameVenda extends JDialog {

	private JPanel contentPane;
	private JPanel panelDados;
	private JLabel lblAnunciante;
	private JPanel panelInformacoes;
	private JPanel panelBotoes;
	private JButton btnGerarContratoDe;
	private JButton btnRealizarVenda;
	private JTextArea txtrAnunciante;
	private JTextArea txtrCarro;
	private JTextArea txtrComprador;
	private JLabel lblCarro;
	private JLabel lblComprador;
	private JComboBox<Cliente> cbComprador;
	
	private boolean listenerComprador;
    
    public boolean vendidoSucesso;
    
    private ClienteController clienteControlador;
    private ContratoController contratoControlador;
    
    private Carro carro;

	/**
	 * Create the frame.
	 */
	public FrameVenda(Carro carro) {
		this.carro = carro;
		
		initialize();
		initListeners();
		
		clienteControlador = new ClienteController();
        contratoControlador = new ContratoController();
        
        popularCbComprador();
        txtrAnunciante.setText(dadosCliente(carro.getAnunciante()));
        txtrCarro.setText(dadosCarro(carro));
        
        listenerComprador = true;
        vendidoSucesso = false;
	}
	
	private String dadosCliente(Cliente cliente) {
        String retorno = "Cod\n  " + cliente.getId() + "\n" +
                "Nome\n  " + cliente.getNome()+ "\n" +
                "CPF\n  " + cliente.getCpf()+ "\n" +
                "Data de Nascimento\n  " + cliente.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+ "\n" +
                "Endereço\n  " + cliente.getEndereco().getEndereco()+ ", " + cliente.getEndereco().getNumero() + "\n" +
                "CEP\n  " + cliente.getEndereco().getCep() + "\n" +
                "Cidade\n  " + cliente.getEndereco().getCidade()+ "\n" +
                "Estado\n  " + cliente.getEndereco().getEstado()+ "\n";
        return retorno;
    }
    
    private String dadosCarro(Carro carro) {
        String retorno = "Cod\n  " + carro.getId() + "\n" +
                "Marca\n  " + carro.getVersao().getModelo().getMarca()+ "\n" +
                "Modelo\n  " + carro.getVersao().getModelo().getNome()+ "\n" +
                "Ano\n  " + carro.getVersao().getModelo().getAno() + "\n" +
                "Versão\n  " + carro.getVersao().toString() + "\n" +
                "KM\n  " + carro.getKm() + "\n" +
                "Cor\n  " + carro.getCor() + "\n";
        return retorno;
    }
    
    private void popularCbComprador() {
        for (Cliente cliente : clienteControlador.index()) {
            cbComprador.addItem(cliente);
        }
        cbComprador.setSelectedIndex(-1);
    }
    
    private void gerarContrato() {
        if (cbComprador.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para ser o comprador");
        } else if (carro.getAnunciante().getId() == cbComprador.getItemAt(cbComprador.getSelectedIndex()).getId()) {
            JOptionPane.showMessageDialog(this, "O Cliente comprador deve ser diferente do Cliente anunciante\nSelecione um cliente diferente para ser o comprador");
        } else {
            try {
                JOptionPane.showMessageDialog(this, "Contrato criado com sucesso!\nRevise todos os dados e assine nos campos informados antes de concretizar a venda.");
                btnRealizarVenda.setEnabled(true);
                String url = contratoControlador.gravarContrato(carro);
                File f = new File(url);
                Desktop.getDesktop().browse(f.toURI());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public Carro getCarroVendido() {
        return this.carro;
    }
	
	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModal(true);
		setLocationRelativeTo(null);
		setBounds(100, 100, 1130, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		panelDados = new JPanel();
		panelDados.setBorder(new TitledBorder(null, "Dados da Venda", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelDados, GroupLayout.DEFAULT_SIZE, 1084, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelDados, GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		lblAnunciante = new JLabel("Anunciante");
		
		panelInformacoes = new JPanel();
		
		panelBotoes = new JPanel();
		
		lblCarro = new JLabel("Carro");
		
		lblComprador = new JLabel("Comprador");
		
		cbComprador = new JComboBox<Cliente>();
		GroupLayout gl_panelDados = new GroupLayout(panelDados);
		gl_panelDados.setHorizontalGroup(
			gl_panelDados.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelDados.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelDados.createParallelGroup(Alignment.LEADING)
						.addComponent(panelInformacoes, GroupLayout.DEFAULT_SIZE, 1052, Short.MAX_VALUE)
						.addComponent(panelBotoes, GroupLayout.DEFAULT_SIZE, 1052, Short.MAX_VALUE)
						.addGroup(gl_panelDados.createSequentialGroup()
							.addComponent(lblAnunciante)
							.addGap(299)
							.addComponent(lblCarro)
							.addPreferredGap(ComponentPlacement.RELATED, 323, Short.MAX_VALUE)
							.addComponent(lblComprador)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(cbComprador, GroupLayout.PREFERRED_SIZE, 286, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panelDados.setVerticalGroup(
			gl_panelDados.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDados.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelDados.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCarro)
						.addComponent(lblComprador)
						.addComponent(cbComprador, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblAnunciante))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelInformacoes, GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelBotoes, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		btnGerarContratoDe = new JButton("Gerar Contrato de Venda");
		
		btnRealizarVenda = new JButton("Realizar Venda");
		btnRealizarVenda.setEnabled(false);
		panelBotoes.setLayout(new GridLayout(0, 1, 0, 0));
		panelBotoes.add(btnGerarContratoDe);
		panelBotoes.add(btnRealizarVenda);
		
		txtrAnunciante = new JTextArea();
		txtrAnunciante.setLineWrap(true);
		txtrAnunciante.setText("Anunciante");
		
		txtrCarro = new JTextArea();
		txtrCarro.setLineWrap(true);
		txtrCarro.setText("Carro");
		
		txtrComprador = new JTextArea();
		txtrComprador.setLineWrap(true);
		txtrComprador.setText("Comprador");
		panelInformacoes.setLayout(new GridLayout(0, 3, 0, 0));
		panelInformacoes.add(txtrAnunciante);
		panelInformacoes.add(txtrCarro);
		panelInformacoes.add(txtrComprador);
		panelDados.setLayout(gl_panelDados);
		contentPane.setLayout(gl_contentPane);
	}

	private void initListeners() {
		cbComprador.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (listenerComprador) {
		            txtrComprador.setText(dadosCliente((Cliente)cbComprador.getSelectedItem()));
		            carro.setComprador((Cliente)cbComprador.getSelectedItem());
		        }
			}
		});
		
		btnGerarContratoDe.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				gerarContrato();
			}
		});
		
		btnRealizarVenda.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				btnRealizarVendaListener();
			}
		});
	}
	
	private void btnRealizarVendaListener() {
		if (JOptionPane.showConfirmDialog(null, "Deseja concretizar a venda? ", "Confirmar Venda", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            carro.setDataVenda(LocalDate.now());
            JOptionPane.showMessageDialog(null, "Venda realizada com sucesso!");
            vendidoSucesso = true;
            this.setVisible(false);
        }
	}
}
