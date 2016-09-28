/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import modelos.Cliente;
import modelos.Endereco;
import modelos.enums.Estado;
import modelos.enums.Sexo;
import persistencia.ClienteControlador;

/**
 *
 * @author Jonathan
 */
public class FrameCliente extends javax.swing.JDialog {

    private boolean frameAbriu;
    private boolean listenerAtivo;
    private ClienteControlador clienteControlador;
    
    private Cliente cliente;
    private List<Cliente> lista;
    
    public FrameCliente() {
        initComponents();
        cbSexo.setSelectedIndex(-1);
        cbEstado.setSelectedIndex(-1);
        setLocationRelativeTo(null);
        setModal(true);
        
        clienteControlador = new ClienteControlador();
        frameAbriu = true;
        listenerAtivo = true;
        
        atualizarListaClientes();
    }
    
    private JTable criarListaClientes() {
        if (frameAbriu) {
            lista = clienteControlador.recuperarLista();
            frameAbriu = false;
        }
        
        String[] headers = {"Cod.", "Nome", "CPF", "Endereço"};
        TableModel model = new DefaultTableModel(headers, lista.size()){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (int i = 0; i < lista.size(); i++) {
            model.setValueAt(lista.get(i).getCodigo(), i, 0);
            model.setValueAt(lista.get(i).getNome(), i, 1);
            model.setValueAt(lista.get(i).getCpf(), i, 2);
            model.setValueAt(enderecoCompleto(lista.get(i)), i, 3);
        }
        
        JTable retorno = new JTable(model);
        retorno.getColumnModel().getColumn(0).setPreferredWidth(35);
        retorno.getColumnModel().getColumn(1).setPreferredWidth(180);
        retorno.getColumnModel().getColumn(2).setPreferredWidth(100);
        retorno.getColumnModel().getColumn(3).setPreferredWidth(285);
        retorno.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        retorno.getTableHeader().setResizingAllowed(false);
        retorno.getTableHeader().setReorderingAllowed(false);
        
        retorno.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
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
        String retorno = cliente.getEndereco().getRua() + ", " +
                cliente.getEndereco().getNumero() + " - " +
                cliente.getEndereco().getCidade() + " / " + 
                cliente.getEndereco().getEstado();
        return retorno;
    } 
    
    private void atualizarListaClientes() {
        scrollLista.setViewportView(listClientes = criarListaClientes());
    }
    
    private boolean isFormularioValido() {
        if (tfNome.getText().length() <= 1) {
            JOptionPane.showMessageDialog(this, "Insira um nome válido");
            return false;
        } else if (ftfCpf.getText().length() < 11) {
            JOptionPane.showMessageDialog(this, "Insira um CPF válido");
            return false;
        } else if (ftfDataNascimento.getText().length() < 8) {
            JOptionPane.showMessageDialog(this, "Insira uma data de nascimento válida");
            return false;
        } else if (cbSexo.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um sexo");
            return false;
        } else if (tfEmail.getText().length() <= 5) {
            JOptionPane.showMessageDialog(this, "Insira um e-mail válido");
            return false;
        } else if (tfEndereco.getText().length() <= 3) {
            JOptionPane.showMessageDialog(this, "Insira um endereço válido");
            return false;
        } else if (tfNumero.getText().length() <= 0) {
            JOptionPane.showMessageDialog(this, "Insira um número de endereço válido");
            return false;
        } else if (ftfCep.getText().length() < 8) {
            JOptionPane.showMessageDialog(this, "Insira um CEP válido");
            return false;
        } else if (tfCidade.getText().length() <= 3) {
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
        tfNome.setText("");
        ftfCpf.setText("");
        ftfDataNascimento.setText("");
        cbSexo.setSelectedIndex(-1);
        tfEmail.setText("");
        tfEndereco.setText("");
        tfNumero.setText("");
        ftfCep.setText("");
        tfCidade.setText("");
        cbEstado.setSelectedIndex(-1);
    }
    
    private void novoCliente() {
        limpar();
        tfCodigo.setText("");
        listenerAtivo = false;
        listClientes.getSelectionModel().clearSelection();
        listenerAtivo = true;
        cliente = null;
    }
    
    private int gerarNovoCodigo() {
        if (lista.isEmpty())
            return 1;
        else
            return (lista.get(lista.size() - 1).getCodigo()) + 1;
    }
    
    private Cliente criarCliente() {
        Cliente cliente = new Cliente();
        cliente.setCodigo(gerarNovoCodigo());
        cliente.setNome(tfNome.getText());
        cliente.setCpf(ftfCpf.getText());
        cliente.setDataNascimento(LocalDate.parse(ftfDataNascimento.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        cliente.setSexo((Sexo)cbSexo.getSelectedItem());
        cliente.setEmail(tfEmail.getText());
        Endereco endereco = new Endereco();
        endereco.setRua(tfEndereco.getText());
        endereco.setNumero(Long.parseLong(tfNumero.getText()));
        endereco.setCep(ftfCep.getText());
        endereco.setCidade(tfCidade.getText());
        endereco.setEstado((Estado)cbEstado.getSelectedItem());
        cliente.setEndereco(endereco);
        return cliente;
    }
    
    private void salvarCliente() {
        if (isFormularioValido()) {
            if (cliente == null) {
                lista.add(criarCliente());
                JOptionPane.showMessageDialog(this, "Cliente adicionado com sucesso");
            } else {
                editarCliente();
                JOptionPane.showMessageDialog(this, "Cliente " + cliente.getNome() + " atualizado com sucesso");
            }
            atualizarListaClientes();
            novoCliente();
        }
    }
    
    private void editarCliente() {
        cliente.setNome(tfNome.getText());
        cliente.setCpf(ftfCpf.getText());
        cliente.setDataNascimento(LocalDate.parse(ftfDataNascimento.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        cliente.setSexo((Sexo)cbSexo.getSelectedItem());
        cliente.setEmail(tfEmail.getText());
        Endereco endereco = new Endereco();
        endereco.setRua(tfEndereco.getText());
        endereco.setNumero(Long.parseLong(tfNumero.getText()));
        endereco.setCep(ftfCep.getText());
        endereco.setCidade(tfCidade.getText());
        endereco.setEstado((Estado)cbEstado.getSelectedItem());
        cliente.setEndereco(endereco);
        lista.set(listClientes.getSelectedRow(), cliente);
    }
    
    private void persistirLista() {
        clienteControlador.gravarLista(lista);
    }
    
    private void popularCampos() {
        if (cliente != null) {
            tfCodigo.setText(Integer.toString(cliente.getCodigo()));
            tfNome.setText(cliente.getNome());
            ftfCpf.setText(cliente.getCpf());
            ftfDataNascimento.setText(cliente.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            cbSexo.setSelectedItem(cliente.getSexo());
            tfEmail.setText(cliente.getEmail());
            tfEndereco.setText(cliente.getEndereco().getRua());
            tfNumero.setText(Long.toString(cliente.getEndereco().getNumero()));
            ftfCep.setText(cliente.getEndereco().getCep());
            tfCidade.setText(cliente.getEndereco().getCidade());
            cbEstado.setSelectedItem(cliente.getEndereco().getEstado());
        }
    }
    
    private void excluirCliente() {
        if (cliente != null) {
            if (JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o cliente " + cliente.getNome() + "?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                lista.remove(listClientes.getSelectedRow());
                JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso");
                atualizarListaClientes();
                novoCliente();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para excluí-lo");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelCadastro = new javax.swing.JPanel();
        panelCadastroWest = new javax.swing.JPanel();
        tfCodigo = new javax.swing.JTextField();
        lbCodigo = new javax.swing.JLabel();
        tfNome = new javax.swing.JTextField();
        lbNome = new javax.swing.JLabel();
        ftfCpf = new javax.swing.JFormattedTextField();
        lbCpf = new javax.swing.JLabel();
        ftfDataNascimento = new javax.swing.JFormattedTextField();
        lbDataNascimento = new javax.swing.JLabel();
        tfEmail = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cbSexo = new javax.swing.JComboBox<>();
        lbSexo = new javax.swing.JLabel();
        panelCadastroEast = new javax.swing.JPanel();
        tfEndereco = new javax.swing.JTextField();
        lbEndereco = new javax.swing.JLabel();
        tfNumero = new javax.swing.JTextField();
        lbNumero = new javax.swing.JLabel();
        lbCep = new javax.swing.JLabel();
        tfCidade = new javax.swing.JTextField();
        lbCidade = new javax.swing.JLabel();
        cbEstado = new javax.swing.JComboBox<>();
        lbEstado = new javax.swing.JLabel();
        ftfCep = new javax.swing.JFormattedTextField();
        btnSalvar = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        panelConsulta = new javax.swing.JPanel();
        scrollLista = new javax.swing.JScrollPane();
        listClientes = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Clientes");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        panelCadastro.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações do Cliente"));

        tfCodigo.setEnabled(false);

        lbCodigo.setText("Código:");

        lbNome.setText("Nome:");

        try {
            ftfCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        lbCpf.setText("CPF:");

        try {
            ftfDataNascimento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        lbDataNascimento.setText("Data de Nascimento:");

        jLabel1.setText("E-Mail:");

        cbSexo.setModel(new DefaultComboBoxModel<>(Sexo.values())
        );

        lbSexo.setText("Sexo:");

        javax.swing.GroupLayout panelCadastroWestLayout = new javax.swing.GroupLayout(panelCadastroWest);
        panelCadastroWest.setLayout(panelCadastroWestLayout);
        panelCadastroWestLayout.setHorizontalGroup(
            panelCadastroWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadastroWestLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadastroWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCadastroWestLayout.createSequentialGroup()
                        .addComponent(lbCodigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCadastroWestLayout.createSequentialGroup()
                        .addGroup(panelCadastroWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbNome)
                            .addComponent(lbCpf))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelCadastroWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(tfNome)
                            .addComponent(ftfCpf, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCadastroWestLayout.createSequentialGroup()
                        .addComponent(lbDataNascimento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                        .addComponent(ftfDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCadastroWestLayout.createSequentialGroup()
                        .addGroup(panelCadastroWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(lbSexo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelCadastroWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbSexo, javax.swing.GroupLayout.Alignment.TRAILING, 0, 195, Short.MAX_VALUE)
                            .addComponent(tfEmail, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        panelCadastroWestLayout.setVerticalGroup(
            panelCadastroWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadastroWestLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadastroWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbCodigo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadastroWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbNome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadastroWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ftfCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbCpf))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadastroWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ftfDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbDataNascimento))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadastroWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbSexo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadastroWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lbEndereco.setText("Endereço:");

        lbNumero.setText("Número:");

        lbCep.setText("CEP:");

        lbCidade.setText("Cidade:");

        cbEstado.setModel(new DefaultComboBoxModel<>(Estado.values()));

        lbEstado.setText("Estado:");

        try {
            ftfCep.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout panelCadastroEastLayout = new javax.swing.GroupLayout(panelCadastroEast);
        panelCadastroEast.setLayout(panelCadastroEastLayout);
        panelCadastroEastLayout.setHorizontalGroup(
            panelCadastroEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadastroEastLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadastroEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCadastroEastLayout.createSequentialGroup()
                        .addGroup(panelCadastroEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbCep)
                            .addComponent(lbNumero))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelCadastroEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ftfCep, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(tfNumero)))
                    .addGroup(panelCadastroEastLayout.createSequentialGroup()
                        .addGroup(panelCadastroEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbEndereco)
                            .addComponent(lbCidade)
                            .addComponent(lbEstado))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addGroup(panelCadastroEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbEstado, 0, 195, Short.MAX_VALUE)
                            .addComponent(tfCidade, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tfEndereco, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        panelCadastroEastLayout.setVerticalGroup(
            panelCadastroEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadastroEastLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadastroEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbEndereco))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadastroEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbNumero))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadastroEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbCep)
                    .addComponent(ftfCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadastroEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbCidade))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadastroEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbEstado))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnNovo.setText("Novo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnLimpar.setText("Limpar");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCadastroLayout = new javax.swing.GroupLayout(panelCadastro);
        panelCadastro.setLayout(panelCadastroLayout);
        panelCadastroLayout.setHorizontalGroup(
            panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadastroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelCadastroWest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelCadastroLayout.createSequentialGroup()
                        .addComponent(btnExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCadastroEast, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelCadastroLayout.createSequentialGroup()
                        .addComponent(btnSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelCadastroLayout.setVerticalGroup(
            panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadastroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelCadastroWest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelCadastroEast, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(btnNovo)
                    .addComponent(btnLimpar)
                    .addComponent(btnExcluir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelConsulta.setBorder(javax.swing.BorderFactory.createTitledBorder("Lista de Clientes Cadastrados"));

        listClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scrollLista.setViewportView(listClientes);

        javax.swing.GroupLayout panelConsultaLayout = new javax.swing.GroupLayout(panelConsulta);
        panelConsulta.setLayout(panelConsultaLayout);
        panelConsultaLayout.setHorizontalGroup(
            panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollLista, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelConsultaLayout.setVerticalGroup(
            panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollLista, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limpar();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        novoCliente();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        salvarCliente();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        excluirCliente();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        persistirLista();
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrameCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<Estado> cbEstado;
    private javax.swing.JComboBox<Sexo> cbSexo;
    private javax.swing.JFormattedTextField ftfCep;
    private javax.swing.JFormattedTextField ftfCpf;
    private javax.swing.JFormattedTextField ftfDataNascimento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lbCep;
    private javax.swing.JLabel lbCidade;
    private javax.swing.JLabel lbCodigo;
    private javax.swing.JLabel lbCpf;
    private javax.swing.JLabel lbDataNascimento;
    private javax.swing.JLabel lbEndereco;
    private javax.swing.JLabel lbEstado;
    private javax.swing.JLabel lbNome;
    private javax.swing.JLabel lbNumero;
    private javax.swing.JLabel lbSexo;
    private javax.swing.JTable listClientes;
    private javax.swing.JPanel panelCadastro;
    private javax.swing.JPanel panelCadastroEast;
    private javax.swing.JPanel panelCadastroWest;
    private javax.swing.JPanel panelConsulta;
    private javax.swing.JScrollPane scrollLista;
    private javax.swing.JTextField tfCidade;
    private javax.swing.JTextField tfCodigo;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfEndereco;
    private javax.swing.JTextField tfNome;
    private javax.swing.JTextField tfNumero;
    // End of variables declaration//GEN-END:variables
}
