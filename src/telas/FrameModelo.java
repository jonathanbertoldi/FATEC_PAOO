/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import modelos.Marca;
import modelos.Modelo;
import persistencia.MarcaControlador;
import persistencia.ModeloControlador;
import telas.renderers.ModeloRenderer;

/**
 *
 * @author Jonathan
 */
public class FrameModelo extends javax.swing.JDialog {

    private boolean frameAbriu;
    private boolean listenerAtivo;
    private ModeloControlador modeloControlador;
    private MarcaControlador marcaControlador;
    
    private Modelo modelo;
    private List<Modelo> lista;
    
    public FrameModelo() {
        initComponents();
        setLocationRelativeTo(null);
        setModal(true);
        
        modeloControlador = new ModeloControlador();
        marcaControlador = new MarcaControlador();
        frameAbriu = true;
        listenerAtivo = true;
        
        criarListaMarcas();
        popularCbAno();
        cbAno.setSelectedIndex(-1);
        atualizarListaModelos();
    }
    
    private void criarListaMarcas() {
        for (Marca marca : marcaControlador.recuperarLista()) {
            cbMarca.addItem(marca);
        }
        cbMarca.setRenderer(new ModeloRenderer());
        cbMarca.setMaximumRowCount(3);
        //cbMarca.setSelectedIndex(0);
    }
    
    private void popularCbAno(){
        for (int i = 2016; i >= 1930; i--) {
            cbAno.addItem(new Long(i));
        }
    }
    
    private JTable criarListaModelos(){
        
        if (frameAbriu) {
            lista = modeloControlador.recuperarLista();
            frameAbriu = false;
        }
        
        String[] headers = {"Cod.", "Nome", "Ano", "Marca"};
        TableModel model = new DefaultTableModel(headers, lista.size()){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
        };
        
        for (int i = 0; i < lista.size(); i++) {
            model.setValueAt(lista.get(i).getCodigo(), i, 0);
            model.setValueAt(lista.get(i).getNome(), i, 1);
            model.setValueAt(lista.get(i).getAno(), i, 2);
            model.setValueAt(lista.get(i).getMarca().getNome(), i, 3);
        }
        
        JTable retorno = new JTable(model);
        retorno.getColumnModel().getColumn(0).setPreferredWidth(35);
        retorno.getColumnModel().getColumn(1).setPreferredWidth(123);
        retorno.getColumnModel().getColumn(2).setPreferredWidth(50);
        retorno.getColumnModel().getColumn(3).setPreferredWidth(80);
        retorno.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        retorno.getTableHeader().setResizingAllowed(false);
        retorno.getTableHeader().setReorderingAllowed(false);
        
        
        retorno.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && listenerAtivo) {
                    modelo = lista.get(retorno.getSelectedRow());
                    popularCampos();
                }
            }
        });
        
        
        return retorno;
    }
    
    private void atualizarListaModelos() {
        scrollLista.setViewportView(listModelo = criarListaModelos());
    }
    
    private boolean isFormularioValido() {
        if (tfNome.getText().length() < 1){
           JOptionPane.showMessageDialog(this, "Insira um nome válido para o modelo");
           return false;
        } else {
            return true;
        }
    }
    
    private void limpar() {
        tfNome.setText("");
        cbAno.setSelectedIndex(-1);
    }
    
    private void novoModelo() {
        limpar();
        tfCodigo.setText("");
        listenerAtivo = false;
        listModelo.getSelectionModel().clearSelection();
        listenerAtivo = true;
        modelo = null;
    }
    
    private int gerarNovoCodigo() {
        if (lista.isEmpty())
            return 1;
        else
            return (lista.get(lista.size() - 1).getCodigo()) + 1;
    }
    
    private Modelo criarModelo() {
        Modelo modelo = new Modelo();
        modelo.setCodigo(gerarNovoCodigo());
        modelo.setNome(tfNome.getText());
        modelo.setAno((Long)cbAno.getSelectedItem());
        modelo.setMarca((Marca)cbMarca.getSelectedItem());
        return modelo;
    }
    
    private void salvarModelo() {
        if (isFormularioValido()) {
            if (modelo == null) {
                lista.add(criarModelo());
                JOptionPane.showMessageDialog(this, "Modelo adicionado com sucesso");
            } else {
                editarModelo();
                JOptionPane.showMessageDialog(this, "Modelo " + modelo.getNome() + " atualizado com sucesso");
            }
            atualizarListaModelos();
            novoModelo();
        }
    }
    
    private void editarModelo() {
        modelo.setCodigo(Integer.parseInt(tfCodigo.getText()));
        modelo.setNome(tfNome.getText());
        modelo.setAno((Long)cbAno.getSelectedItem());
        modelo.setMarca((Marca)cbMarca.getSelectedItem());
        lista.set(listModelo.getSelectedRow(), modelo);
    }
    
    private void persistirLista() {
        modeloControlador.gravarLista(lista);
    }
    
    private boolean compararMarca(int pos) {
        if (cbMarca.getItemAt(pos).getCodigo() == modelo.getMarca().getCodigo() &&
                cbMarca.getItemAt(pos).getNome().equals(modelo.getMarca().getNome()) &&
                cbMarca.getItemAt(pos).getPais().equals(modelo.getMarca().getPais()) &&
                cbMarca.getItemAt(pos).getLogo().equals(modelo.getMarca().getLogo()))
            return true;
        return false;
    }
    
    private void popularCampos() {
        if (modelo != null) {
            tfCodigo.setText(Integer.toString(modelo.getCodigo()));
            tfNome.setText(modelo.getNome());
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
                lista.remove(listModelo.getSelectedRow());
                JOptionPane.showMessageDialog(this, "Modelo excluído com sucesso");
                atualizarListaModelos();
                novoModelo();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um modelo para excluí-lo");
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
        tfCodigo = new javax.swing.JTextField();
        lbCodigo = new javax.swing.JLabel();
        tfNome = new javax.swing.JTextField();
        lbNome = new javax.swing.JLabel();
        lbAno = new javax.swing.JLabel();
        cbMarca = new javax.swing.JComboBox<>();
        lbMarca = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        cbAno = new javax.swing.JComboBox<>();
        panelConsulta = new javax.swing.JPanel();
        scrollLista = new javax.swing.JScrollPane();
        listModelo = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Modelo");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        panelCadastro.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações do Modelo"));

        tfCodigo.setEnabled(false);

        lbCodigo.setText("Código");

        lbNome.setText("Nome:");

        lbAno.setText("Ano:");

        lbMarca.setText("Marca:");

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
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

        btnNovo.setText("Novo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCadastroLayout = new javax.swing.GroupLayout(panelCadastro);
        panelCadastro.setLayout(panelCadastroLayout);
        panelCadastroLayout.setHorizontalGroup(
            panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadastroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCadastroLayout.createSequentialGroup()
                        .addComponent(lbCodigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCadastroLayout.createSequentialGroup()
                        .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbAno)
                            .addComponent(lbNome))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfNome)
                            .addComponent(cbAno, 0, 188, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCadastroLayout.createSequentialGroup()
                        .addGap(0, 7, Short.MAX_VALUE)
                        .addComponent(btnNovo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalvar))
                    .addGroup(panelCadastroLayout.createSequentialGroup()
                        .addComponent(lbMarca)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelCadastroLayout.setVerticalGroup(
            panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadastroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbCodigo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbNome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbAno))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbMarca))
                .addGap(18, 18, 18)
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(btnLimpar)
                    .addComponent(btnExcluir)
                    .addComponent(btnNovo))
                .addContainerGap())
        );

        panelConsulta.setBorder(javax.swing.BorderFactory.createTitledBorder("Lista de Modelos Cadastrados"));

        scrollLista.setMaximumSize(new java.awt.Dimension(288, 208));
        scrollLista.setPreferredSize(new java.awt.Dimension(288, 208));

        listModelo.setModel(new javax.swing.table.DefaultTableModel(
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
        scrollLista.setViewportView(listModelo);

        javax.swing.GroupLayout panelConsultaLayout = new javax.swing.GroupLayout(panelConsulta);
        panelConsulta.setLayout(panelConsultaLayout);
        panelConsultaLayout.setHorizontalGroup(
            panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollLista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelConsultaLayout.setVerticalGroup(
            panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelConsultaLayout.createSequentialGroup()
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
                .addComponent(panelConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limpar();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        novoModelo();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        salvarModelo();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        persistirLista();
    }//GEN-LAST:event_formWindowClosing

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        excluirModelo();
    }//GEN-LAST:event_btnExcluirActionPerformed

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
            java.util.logging.Logger.getLogger(FrameModelo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameModelo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameModelo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameModelo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameModelo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<Long> cbAno;
    private javax.swing.JComboBox<Marca> cbMarca;
    private javax.swing.JLabel lbAno;
    private javax.swing.JLabel lbCodigo;
    private javax.swing.JLabel lbMarca;
    private javax.swing.JLabel lbNome;
    private javax.swing.JTable listModelo;
    private javax.swing.JPanel panelCadastro;
    private javax.swing.JPanel panelConsulta;
    private javax.swing.JScrollPane scrollLista;
    private javax.swing.JTextField tfCodigo;
    private javax.swing.JTextField tfNome;
    // End of variables declaration//GEN-END:variables
}
