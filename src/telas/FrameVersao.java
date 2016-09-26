/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import modelos.Marca;
import modelos.Modelo;
import modelos.Versao;
import modelos.enums.Cambio;
import modelos.enums.Combustivel;
import persistencia.MarcaControlador;
import persistencia.ModeloControlador;
import persistencia.VersaoControlador;

/**
 *
 * @author Jonathan
 */
public class FrameVersao extends javax.swing.JFrame {

    private boolean frameAbriu;
    private boolean listenerAtivo = false;
    private boolean listenerListaAtivo;
    private MarcaControlador marcaControlador;
    private ModeloControlador modeloControlador;
    private VersaoControlador versaoControlador;
    
    private Versao versao;
    private List<Versao> lista;
    
    public FrameVersao() {
        initComponents();
        setLocationRelativeTo(null);
        
        modeloControlador = new ModeloControlador();
        marcaControlador = new MarcaControlador();
        versaoControlador = new VersaoControlador();
        frameAbriu = true;
        listenerListaAtivo = true;
        
        atualizarListaVersoes();
        popularCbListaMarcas();
        cbCambio.setSelectedIndex(-1);
        cbCombustivel.setSelectedIndex(-1);
    }

    private JTable criarListaVersoes() {
        
        if (frameAbriu) {
            lista = versaoControlador.recuperarLista();
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
            model.setValueAt(lista.get(i).getCodigo(), i, 0);
            model.setValueAt(lista.get(i).getModelo().getMarca().getNome(), i, 1);
            model.setValueAt(lista.get(i).getModelo().getNome(), i, 2);
            model.setValueAt(lista.get(i).getModelo().getAno(), i, 3);
            model.setValueAt(gerarStringVersao(lista.get(i)), i, 4);
        }
        
        JTable retorno = new JTable(model);
        retorno.getColumnModel().getColumn(0).setPreferredWidth(35);
        retorno.getColumnModel().getColumn(1).setPreferredWidth(80);
        retorno.getColumnModel().getColumn(2).setPreferredWidth(80);
        retorno.getColumnModel().getColumn(3).setPreferredWidth(60);
        retorno.getColumnModel().getColumn(4).setPreferredWidth(345);
        retorno.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        retorno.getTableHeader().setResizingAllowed(false);
        retorno.getTableHeader().setReorderingAllowed(false);
        
        retorno.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (listenerListaAtivo) {
                    versao = lista.get(retorno.getSelectedRow());
                    popularCampos();
                }
            }
        });
        
        return retorno;
    }
    
    private void atualizarListaVersoes() {
        scrollLista.setViewportView(listVersoes = criarListaVersoes());
    }
    
    private void popularCbListaMarcas() {
        for (Marca marca : marcaControlador.recuperarLista()) {
            cbMarca.addItem(marca);
        }
        cbMarca.setSelectedIndex(-1);
        cbMarca.setMaximumRowCount(10);
        listenerAtivo = true;
    }
    
    private void popularCbListaModelos(Marca marcaFiltro) {
        if (listenerAtivo) {
            for (Modelo modelo : modeloControlador.recuperarLista(marcaFiltro)) {
                cbModelo.addItem(modelo);
            }
        }
    }
    
    private boolean isFormularioValido() {
        if (cbModelo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione um modelo");
            return false;
        } else if (tfMotor.getText().length() <= 0) {
            JOptionPane.showMessageDialog(this, "Insira um nome válido para o motor");
            return false;
        } else if (tfCilindradas.getText().length() <= 0) {
            JOptionPane.showMessageDialog(this, "Selecione um número válido de Cilindradas (CC)");
            return false;
        } else if (tfValvulas.getText().length() <= 0) {
            JOptionPane.showMessageDialog(this, "Insira um número válido de válvulas");
            return false;
        } else if (tfPortas.getText().length() <= 0) {
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
        tfMotor.setText("");
        tfCilindradas.setText("");
        tfValvulas.setText("");
        tfPortas.setText("");
        cbCambio.setSelectedIndex(-1);
        cbCombustivel.setSelectedIndex(-1);
    }
    
    private void novaVersao() {
        limpar();
        tfCodigo.setText("");
        listenerListaAtivo = false;
        listVersoes.getSelectionModel().clearSelection();
        listenerListaAtivo = true;
        versao = null;
    }
    
    private int gerarNovoCodigo() {
        if (lista.isEmpty())
            return 1;
        else
            return (lista.get(lista.size() - 1).getCodigo()) + 1;
    }
    
    private Versao criarVersao() {
        Versao versao = new Versao();
        versao.setCodigo(gerarNovoCodigo());
        versao.setCilindradas(Float.parseFloat(tfCilindradas.getText()));
        versao.setNomeMotor(tfMotor.getText());
        versao.setValvulas(Integer.parseInt(tfValvulas.getText()));
        versao.setPortas(Integer.parseInt(tfPortas.getText()));
        versao.setCambio((Cambio)cbCambio.getSelectedItem());
        versao.setCombustivel((Combustivel)cbCombustivel.getSelectedItem());
        versao.setModelo((Modelo)cbModelo.getSelectedItem());
        return versao;
    }
    
    private void salvarVersao() {
        if (isFormularioValido()) {
            if (versao == null) {
                lista.add(criarVersao());
                JOptionPane.showMessageDialog(this, "Versão adicionada com sucesso");
            } else {
                editarVersao();
                JOptionPane.showMessageDialog(this, "Versão " + versao.getModelo().toString() + ": " + gerarStringVersao(versao) + " atualizada com sucesso");
            }
            atualizarListaVersoes();
            novaVersao();
        }
    }
    
    private void editarVersao() {
        versao.setCodigo(Integer.parseInt(tfCodigo.getText()));
        versao.setCilindradas(Float.parseFloat(tfCilindradas.getText()));
        versao.setNomeMotor(tfMotor.getText());
        versao.setValvulas(Integer.parseInt(tfValvulas.getText()));
        versao.setPortas(Integer.parseInt(tfPortas.getText()));
        versao.setCambio((Cambio)cbCambio.getSelectedItem());
        versao.setCombustivel((Combustivel)cbCombustivel.getSelectedItem());
        versao.setModelo((Modelo)cbModelo.getSelectedItem());
        lista.set(listVersoes.getSelectedRow(), versao);
    }
    
    private void persistirLista() {
        versaoControlador.gravarLista(lista);
    }
    
    private void popularCbMarca() {
        for (int i = 0; i < cbMarca.getItemCount(); i++) {
            if (cbMarca.getItemAt(i).getCodigo() == versao.getModelo().getMarca().getCodigo())
                cbMarca.setSelectedIndex(i);
        }
    }
    
    private void popularCbModelo() {
        for (int i = 0; i < cbModelo.getItemCount(); i++) {
            if (cbModelo.getItemAt(i).getCodigo() == versao.getModelo().getCodigo())
                cbModelo.setSelectedIndex(i);
        }
    }
    
    private void popularCampos() {
        if (versao != null) {
            tfCodigo.setText(Integer.toString(versao.getCodigo()));
            popularCbMarca();
            popularCbModelo();
            tfMotor.setText(versao.getNomeMotor());
            tfCilindradas.setText(versao.getCilindradas().toString());
            tfValvulas.setText(Integer.toString(versao.getValvulas()));
            tfPortas.setText(Integer.toString(versao.getPortas()));
            cbCambio.setSelectedItem(versao.getCambio());
            cbCombustivel.setSelectedItem(versao.getCombustivel());
        }
    }
    
    private void excluirVersao() {
        if (versao != null) {
            if (JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir a versao " + versao.getModelo().toString() + ": " + gerarStringVersao(versao) + "?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                lista.remove(listVersoes.getSelectedRow());
                JOptionPane.showMessageDialog(this, "Versao excluída com sucesso");
                atualizarListaVersoes();
                novaVersao();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma versao para excluí-la");
        }
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
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        panelConsulta = new javax.swing.JPanel();
        scrollLista = new javax.swing.JScrollPane();
        listVersoes = new javax.swing.JTable();
        panelCadastro = new javax.swing.JPanel();
        panelCadWest = new javax.swing.JPanel();
        tfCodigo = new javax.swing.JTextField();
        lbCodigo = new javax.swing.JLabel();
        cbMarca = new javax.swing.JComboBox<>();
        lbMarca = new javax.swing.JLabel();
        cbModelo = new javax.swing.JComboBox<>();
        lbModelo = new javax.swing.JLabel();
        panelCadEast = new javax.swing.JPanel();
        tfMotor = new javax.swing.JTextField();
        lbMotor = new javax.swing.JLabel();
        tfCilindradas = new javax.swing.JTextField();
        lbCilindradas = new javax.swing.JLabel();
        tfValvulas = new javax.swing.JTextField();
        lbValvulas = new javax.swing.JLabel();
        lbPortas = new javax.swing.JLabel();
        tfPortas = new javax.swing.JTextField();
        cbCambio = new javax.swing.JComboBox<>();
        lbCambio = new javax.swing.JLabel();
        cbCombustivel = new javax.swing.JComboBox<>();
        lbCombustivel = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Versão");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        panelConsulta.setBorder(javax.swing.BorderFactory.createTitledBorder("Lista de Versões Cadastradas"));

        listVersoes.setModel(new javax.swing.table.DefaultTableModel(
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
        scrollLista.setViewportView(listVersoes);

        javax.swing.GroupLayout panelConsultaLayout = new javax.swing.GroupLayout(panelConsulta);
        panelConsulta.setLayout(panelConsultaLayout);
        panelConsultaLayout.setHorizontalGroup(
            panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollLista)
                .addContainerGap())
        );
        panelConsultaLayout.setVerticalGroup(
            panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollLista, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelCadastro.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações da Versão"));

        tfCodigo.setEnabled(false);

        lbCodigo.setText("Código:");

        cbMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMarcaActionPerformed(evt);
            }
        });

        lbMarca.setText("Marca:");

        lbModelo.setText("Modelo:");

        javax.swing.GroupLayout panelCadWestLayout = new javax.swing.GroupLayout(panelCadWest);
        panelCadWest.setLayout(panelCadWestLayout);
        panelCadWestLayout.setHorizontalGroup(
            panelCadWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadWestLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCadWestLayout.createSequentialGroup()
                        .addComponent(lbCodigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCadWestLayout.createSequentialGroup()
                        .addGroup(panelCadWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbMarca)
                            .addComponent(lbModelo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addGroup(panelCadWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbMarca, 0, 208, Short.MAX_VALUE)
                            .addComponent(cbModelo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panelCadWestLayout.setVerticalGroup(
            panelCadWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadWestLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbCodigo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbMarca))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbModelo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lbMotor.setText("Motor:");

        lbCilindradas.setText("CC:");

        lbValvulas.setText("Válvulas:");

        lbPortas.setText("Portas:");

        cbCambio.setModel(new DefaultComboBoxModel<>(Cambio.values()));

        lbCambio.setText("Câmbio:");

        cbCombustivel.setModel(new DefaultComboBoxModel<>(Combustivel.values())
        );

        lbCombustivel.setText("Combustível:");

        javax.swing.GroupLayout panelCadEastLayout = new javax.swing.GroupLayout(panelCadEast);
        panelCadEast.setLayout(panelCadEastLayout);
        panelCadEastLayout.setHorizontalGroup(
            panelCadEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCadEastLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbMotor)
                    .addComponent(lbCilindradas)
                    .addComponent(lbValvulas)
                    .addComponent(lbCambio)
                    .addComponent(lbCombustivel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(panelCadEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCadEastLayout.createSequentialGroup()
                        .addComponent(tfValvulas, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbPortas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfPortas, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE))
                    .addComponent(tfMotor, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tfCilindradas, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cbCambio, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbCombustivel, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelCadEastLayout.setVerticalGroup(
            panelCadEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadEastLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfMotor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbMotor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfCilindradas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbCilindradas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfValvulas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfPortas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbValvulas)
                    .addComponent(lbPortas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCambio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbCambio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadEastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCombustivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbCombustivel))
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

        btnExcluir.setText("Excluír");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCadastroLayout = new javax.swing.GroupLayout(panelCadastro);
        panelCadastro.setLayout(panelCadastroLayout);
        panelCadastroLayout.setHorizontalGroup(
            panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCadastroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelCadWest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelCadastroLayout.createSequentialGroup()
                        .addComponent(btnExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCadastroLayout.createSequentialGroup()
                        .addComponent(btnSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelCadEast, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panelCadastroLayout.setVerticalGroup(
            panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadastroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panelCadWest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelCadEast, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(btnNovo)
                    .addComponent(btnLimpar)
                    .addComponent(btnExcluir))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMarcaActionPerformed
        Marca m = (Marca)cbMarca.getSelectedItem();
        cbModelo.removeAllItems();
        popularCbListaModelos(m);
    }//GEN-LAST:event_cbMarcaActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limpar();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        novaVersao();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        salvarVersao();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        excluirVersao();
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
            java.util.logging.Logger.getLogger(FrameVersao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameVersao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameVersao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameVersao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameVersao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<Cambio> cbCambio;
    private javax.swing.JComboBox<Combustivel> cbCombustivel;
    private javax.swing.JComboBox<Marca> cbMarca;
    private javax.swing.JComboBox<Modelo> cbModelo;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lbCambio;
    private javax.swing.JLabel lbCilindradas;
    private javax.swing.JLabel lbCodigo;
    private javax.swing.JLabel lbCombustivel;
    private javax.swing.JLabel lbMarca;
    private javax.swing.JLabel lbModelo;
    private javax.swing.JLabel lbMotor;
    private javax.swing.JLabel lbPortas;
    private javax.swing.JLabel lbValvulas;
    private javax.swing.JTable listVersoes;
    private javax.swing.JPanel panelCadEast;
    private javax.swing.JPanel panelCadWest;
    private javax.swing.JPanel panelCadastro;
    private javax.swing.JPanel panelConsulta;
    private javax.swing.JScrollPane scrollLista;
    private javax.swing.JTextField tfCilindradas;
    private javax.swing.JTextField tfCodigo;
    private javax.swing.JTextField tfMotor;
    private javax.swing.JTextField tfPortas;
    private javax.swing.JTextField tfValvulas;
    // End of variables declaration//GEN-END:variables
}
