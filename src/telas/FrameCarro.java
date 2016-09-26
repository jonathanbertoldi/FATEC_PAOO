package telas;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import modelos.Marca;
import modelos.Modelo;
import modelos.Versao;
import persistencia.CarroControlador;
import persistencia.ClienteControlador;
import persistencia.MarcaControlador;
import persistencia.ModeloControlador;
import persistencia.VersaoControlador;
import telas.renderers.ModeloRenderer;

/**
 *
 * @author Jonathan
 */
public class FrameCarro extends javax.swing.JFrame {
    
    private MarcaControlador marcaControlador;
    private ModeloControlador modeloControlador;
    private VersaoControlador versaoControlador;
    private ClienteControlador clienteControlador;
    private CarroControlador carroControlador;
    
    private boolean listenerMarca;
    private boolean listenerModelo;
    
    public FrameCarro() {
        initComponents();
        setLocationRelativeTo(null);
        
        modeloControlador = new ModeloControlador();
        marcaControlador = new MarcaControlador();
        versaoControlador = new VersaoControlador();
        clienteControlador = new ClienteControlador();
        carroControlador = new CarroControlador();
        
        atualizarListaCarros();
        popularCbMarca();
        
        listenerMarca = true;
    }
    
    private JTable criarListaVersoes() {
        String[] headers = {"Cod.", "Marca", "Modelo", "Versão", "KM", "Anunciante", "Status"};
        TableModel model = new DefaultTableModel(headers, 3){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable retorno = new JTable(model);
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
            @Override
            public void valueChanged(ListSelectionEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        return retorno;
    }
    
    private void atualizarListaCarros() {
        scrollLista.setViewportView(listCarros = criarListaVersoes());
    }
    
    private void popularCbMarca() {
        for (Marca marca : marcaControlador.recuperarLista()) {
            cbMarca.addItem(marca);
        }
        cbMarca.setRenderer(new ModeloRenderer());
        cbMarca.setMaximumRowCount(10);
    }
    
    private void popularCbModelo() {
        listenerModelo = false;
        cbVersao.removeAllItems();
        cbModelo.removeAllItems();
        for (Modelo modelo : modeloControlador.recuperarLista((Marca)cbMarca.getSelectedItem())) {
            cbModelo.addItem(modelo);
        }
        cbModelo.setSelectedIndex(-1);
        listenerModelo = true;
    }
    
    private void popularCbVersao() {
        cbVersao.removeAllItems();
        for (Versao versao : versaoControlador.recuperarLista((Modelo)cbModelo.getSelectedItem())) {
            cbVersao.addItem(versao);
        }
        cbVersao.setSelectedIndex(-1);
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
        picCarro = new javax.swing.JLabel();
        btnPic1 = new javax.swing.JButton();
        btnPic2 = new javax.swing.JButton();
        btnPic3 = new javax.swing.JButton();
        btnFoto = new javax.swing.JButton();
        tfCodigo = new javax.swing.JTextField();
        lbCodigo = new javax.swing.JLabel();
        cbModelo = new javax.swing.JComboBox<>();
        cbMarca = new javax.swing.JComboBox<>();
        lbMarca = new javax.swing.JLabel();
        lbModelo = new javax.swing.JLabel();
        cbVersao = new javax.swing.JComboBox<>();
        lbVersao = new javax.swing.JLabel();
        tfQuilometragem = new javax.swing.JTextField();
        lbQuilometragem = new javax.swing.JLabel();
        tfCor = new javax.swing.JTextField();
        lbCor = new javax.swing.JLabel();
        tfDataAnuncio = new javax.swing.JTextField();
        lbDataAnuncio = new javax.swing.JLabel();
        cbAnunciante = new javax.swing.JComboBox<>();
        lbAnunciante = new javax.swing.JLabel();
        tfCpf = new javax.swing.JTextField();
        lbCPF = new javax.swing.JLabel();
        tfEndereço = new javax.swing.JTextField();
        lbEndereco = new javax.swing.JLabel();
        tfEmail = new javax.swing.JTextField();
        lbEmail = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        lbComprador = new javax.swing.JLabel();
        lbCpfComprador = new javax.swing.JLabel();
        tfCpfComprador = new javax.swing.JTextField();
        lbEnderecoComprador = new javax.swing.JLabel();
        tfEnderecoComprador = new javax.swing.JTextField();
        lbEmailComprador = new javax.swing.JLabel();
        tfEmailComprador = new javax.swing.JTextField();
        tfDataVenda = new javax.swing.JTextField();
        lbDataVenda = new javax.swing.JLabel();
        tfComprador = new javax.swing.JTextField();
        panelConsulta = new javax.swing.JPanel();
        scrollLista = new javax.swing.JScrollPane();
        listCarros = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Carro");
        setResizable(false);

        panelCadastro.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações do Carro"));

        picCarro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sem_foto.jpg"))); // NOI18N

        btnPic1.setText("1");

        btnPic2.setText("2");

        btnPic3.setText("3");

        tfCodigo.setEnabled(false);

        lbCodigo.setText("Código:");

        cbModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbModeloActionPerformed(evt);
            }
        });

        cbMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMarcaActionPerformed(evt);
            }
        });

        lbMarca.setText("Marca:");

        lbModelo.setText("Modelo:");

        lbVersao.setText("Versão:");

        lbQuilometragem.setText("KM:");

        lbCor.setText("Cor:");

        tfDataAnuncio.setEnabled(false);

        lbDataAnuncio.setText("Data de Anúncio:");

        lbAnunciante.setText("Anunciante:");

        tfCpf.setEnabled(false);

        lbCPF.setText("CPF:");

        tfEndereço.setEnabled(false);

        lbEndereco.setText("Endereço:");

        tfEmail.setEnabled(false);

        lbEmail.setText("E-Mail:");

        btnSalvar.setText("Salvar");

        btnExcluir.setText("Excluir");

        btnLimpar.setText("Limpar");

        btnNovo.setText("Novo");

        lbComprador.setText("Comprador:");

        lbCpfComprador.setText("CPF:");

        tfCpfComprador.setEnabled(false);

        lbEnderecoComprador.setText("Endereço:");

        tfEnderecoComprador.setEnabled(false);

        lbEmailComprador.setText("E-Mail:");

        tfEmailComprador.setEnabled(false);

        tfDataVenda.setEnabled(false);

        lbDataVenda.setText("Data de Venda:");

        tfComprador.setEnabled(false);

        javax.swing.GroupLayout panelCadastroLayout = new javax.swing.GroupLayout(panelCadastro);
        panelCadastro.setLayout(panelCadastroLayout);
        panelCadastroLayout.setHorizontalGroup(
            panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadastroLayout.createSequentialGroup()
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCadastroLayout.createSequentialGroup()
                        .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCadastroLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbMarca)
                                    .addComponent(lbCodigo))
                                .addGap(37, 37, 37))
                            .addGroup(panelCadastroLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbModelo)
                                    .addComponent(lbVersao)
                                    .addComponent(lbQuilometragem)
                                    .addComponent(lbCor)
                                    .addComponent(lbAnunciante)
                                    .addComponent(lbCPF)
                                    .addComponent(lbEndereco)
                                    .addComponent(lbEmail))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfEmail)
                            .addComponent(tfEndereço)
                            .addComponent(tfCpf)
                            .addComponent(tfCor)
                            .addComponent(cbMarca, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tfCodigo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbModelo, javax.swing.GroupLayout.Alignment.TRAILING, 0, 200, Short.MAX_VALUE)
                            .addComponent(cbVersao, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tfQuilometragem, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cbAnunciante, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panelCadastroLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbComprador)
                            .addComponent(lbCpfComprador)
                            .addComponent(lbEnderecoComprador)
                            .addComponent(lbEmailComprador))
                        .addGap(16, 16, 16)
                        .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfEnderecoComprador, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tfCpfComprador)
                            .addComponent(tfEmailComprador)
                            .addComponent(tfComprador))))
                .addGap(18, 18, 18)
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(picCarro, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelCadastroLayout.createSequentialGroup()
                        .addComponent(btnFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(btnPic1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPic2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPic3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelCadastroLayout.createSequentialGroup()
                        .addComponent(lbDataAnuncio)
                        .addGap(18, 18, 18)
                        .addComponent(tfDataAnuncio))
                    .addGroup(panelCadastroLayout.createSequentialGroup()
                        .addComponent(lbDataVenda)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tfDataVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCadastroLayout.createSequentialGroup()
                        .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnLimpar)
                            .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnExcluir, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnSalvar, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        panelCadastroLayout.setVerticalGroup(
            panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadastroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCadastroLayout.createSequentialGroup()
                        .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(picCarro, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbCodigo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPic3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPic2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPic1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbDataAnuncio)
                            .addComponent(tfDataAnuncio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelCadastroLayout.createSequentialGroup()
                        .addComponent(tfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbMarca))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbModelo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbVersao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbVersao))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfQuilometragem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbQuilometragem))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfCor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbCor))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbAnunciante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbAnunciante))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbCPF))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfEndereço, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbEndereco))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbEmail)
                    .addComponent(btnNovo)
                    .addComponent(btnExcluir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbComprador)
                    .addComponent(btnSalvar)
                    .addComponent(btnLimpar)
                    .addComponent(tfComprador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfCpfComprador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbCpfComprador))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfEnderecoComprador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbEnderecoComprador))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfEmailComprador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbEmailComprador)
                    .addComponent(tfDataVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbDataVenda))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelConsulta.setBorder(javax.swing.BorderFactory.createTitledBorder("Lista de carros cadastrados a venda"));

        listCarros.setModel(new javax.swing.table.DefaultTableModel(
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
        scrollLista.setViewportView(listCarros);

        javax.swing.GroupLayout panelConsultaLayout = new javax.swing.GroupLayout(panelConsulta);
        panelConsulta.setLayout(panelConsultaLayout);
        panelConsultaLayout.setHorizontalGroup(
            panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollLista, javax.swing.GroupLayout.DEFAULT_SIZE, 778, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelConsultaLayout.setVerticalGroup(
            panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollLista, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMarcaActionPerformed
        if (listenerMarca) {
            popularCbModelo();
        }
    }//GEN-LAST:event_cbMarcaActionPerformed

    private void cbModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbModeloActionPerformed
        if (listenerModelo) {
            popularCbVersao();
        }
    }//GEN-LAST:event_cbModeloActionPerformed

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
            java.util.logging.Logger.getLogger(FrameCarro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameCarro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameCarro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameCarro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameCarro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnFoto;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPic1;
    private javax.swing.JButton btnPic2;
    private javax.swing.JButton btnPic3;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> cbAnunciante;
    private javax.swing.JComboBox<Marca> cbMarca;
    private javax.swing.JComboBox<Modelo> cbModelo;
    private javax.swing.JComboBox<Versao> cbVersao;
    private javax.swing.JLabel lbAnunciante;
    private javax.swing.JLabel lbCPF;
    private javax.swing.JLabel lbCodigo;
    private javax.swing.JLabel lbComprador;
    private javax.swing.JLabel lbCor;
    private javax.swing.JLabel lbCpfComprador;
    private javax.swing.JLabel lbDataAnuncio;
    private javax.swing.JLabel lbDataVenda;
    private javax.swing.JLabel lbEmail;
    private javax.swing.JLabel lbEmailComprador;
    private javax.swing.JLabel lbEndereco;
    private javax.swing.JLabel lbEnderecoComprador;
    private javax.swing.JLabel lbMarca;
    private javax.swing.JLabel lbModelo;
    private javax.swing.JLabel lbQuilometragem;
    private javax.swing.JLabel lbVersao;
    private javax.swing.JTable listCarros;
    private javax.swing.JPanel panelCadastro;
    private javax.swing.JPanel panelConsulta;
    private javax.swing.JLabel picCarro;
    private javax.swing.JScrollPane scrollLista;
    private javax.swing.JTextField tfCodigo;
    private javax.swing.JTextField tfComprador;
    private javax.swing.JTextField tfCor;
    private javax.swing.JTextField tfCpf;
    private javax.swing.JTextField tfCpfComprador;
    private javax.swing.JTextField tfDataAnuncio;
    private javax.swing.JTextField tfDataVenda;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfEmailComprador;
    private javax.swing.JTextField tfEnderecoComprador;
    private javax.swing.JTextField tfEndereço;
    private javax.swing.JTextField tfQuilometragem;
    // End of variables declaration//GEN-END:variables
}