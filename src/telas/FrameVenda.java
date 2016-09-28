package telas;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import modelos.Carro;
import modelos.Cliente;
import persistencia.ClienteControlador;
import persistencia.ContratoControlador;

/**
 *
 * @author Jonathan
 */
public class FrameVenda extends javax.swing.JDialog {

    private boolean listenerComprador;
    
    public boolean vendidoSucesso;
    
    private ClienteControlador clienteControlador;
    private ContratoControlador contratoControlador;
    
    private Carro carro;
    
    public FrameVenda(Carro carro) {
        this.carro = carro;
        
        initComponents();
        setModal(true);
        setLocationRelativeTo(null);
        
        clienteControlador = new ClienteControlador();
        contratoControlador = new ContratoControlador();
        
        popularCbComprador();
        taAnunciante.setText(dadosCliente(carro.getAnunciante()));
        taCarro.setText(dadosCarro(carro));
        
        listenerComprador = true;
        vendidoSucesso = false;
    }
    
    private String dadosCliente(Cliente cliente) {
        String retorno = "Cod\n  " + cliente.getCodigo() + "\n" +
                "Nome\n  " + cliente.getNome()+ "\n" +
                "CPF\n  " + cliente.getCpf()+ "\n" +
                "Data de Nascimento\n  " + cliente.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+ "\n" +
                "Endereço\n  " + cliente.getEndereco().getRua()+ ", " + cliente.getEndereco().getNumero() + "\n" +
                "CEP\n  " + cliente.getEndereco().getCep() + "\n" +
                "Cidade\n  " + cliente.getEndereco().getCidade()+ "\n" +
                "Estado\n  " + cliente.getEndereco().getEstado()+ "\n";
        return retorno;
    }
    
    private String dadosCarro(Carro carro) {
        String retorno = "Cod\n  " + carro.getCodigo() + "\n" +
                "Marca\n  " + carro.getVersao().getModelo().getMarca()+ "\n" +
                "Modelo\n  " + carro.getVersao().getModelo().getNome()+ "\n" +
                "Ano\n  " + carro.getVersao().getModelo().getAno() + "\n" +
                "Versão\n  " + carro.getVersao().toString() + "\n" +
                "KM\n  " + carro.getKm() + "\n" +
                "Cor\n  " + carro.getCor() + "\n";
        return retorno;
    }
    
    private void popularCbComprador() {
        for (Cliente cliente : clienteControlador.recuperarLista()) {
            cbComprador.addItem(cliente);
        }
        cbComprador.setSelectedIndex(-1);
    }
    
    private void gerarContrato() {
        if (cbComprador.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para ser o comprador");
        } else if (carro.getAnunciante().getCodigo() == cbComprador.getItemAt(cbComprador.getSelectedIndex()).getCodigo()) {
            JOptionPane.showMessageDialog(this, "O Cliente comprador deve ser diferente do Cliente anunciante\nSelecione um cliente diferente para ser o comprador");
        } else {
            try {
                JOptionPane.showMessageDialog(this, "Contrato criado com sucesso!\nRevise todos os dados e assine nos campos informados antes de concretizar a venda.");
                btnVender.setEnabled(true);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMain = new javax.swing.JPanel();
        lbAnunciante = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taAnunciante = new javax.swing.JTextArea();
        lbCarro = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taCarro = new javax.swing.JTextArea();
        lbComprador = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        taComprador = new javax.swing.JTextArea();
        cbComprador = new javax.swing.JComboBox<>();
        btnContrato = new javax.swing.JButton();
        btnVender = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Informar Comprador");
        setResizable(false);

        panelMain.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados da venda"));

        lbAnunciante.setText("Anunciante:");

        taAnunciante.setColumns(20);
        taAnunciante.setRows(5);
        taAnunciante.setEnabled(false);
        jScrollPane1.setViewportView(taAnunciante);

        lbCarro.setText("Carro:");

        taCarro.setColumns(20);
        taCarro.setRows(5);
        taCarro.setEnabled(false);
        jScrollPane2.setViewportView(taCarro);

        lbComprador.setText("Comprador:");

        taComprador.setColumns(20);
        taComprador.setRows(5);
        taComprador.setEnabled(false);
        jScrollPane3.setViewportView(taComprador);

        cbComprador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCompradorActionPerformed(evt);
            }
        });

        btnContrato.setText("Gerar Contrato de Venda");
        btnContrato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContratoActionPerformed(evt);
            }
        });

        btnVender.setText("Realizar Venda");
        btnVender.setEnabled(false);
        btnVender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVenderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelMainLayout = new javax.swing.GroupLayout(panelMain);
        panelMain.setLayout(panelMainLayout);
        panelMainLayout.setHorizontalGroup(
            panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbAnunciante)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbCarro)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                    .addComponent(btnContrato, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelMainLayout.createSequentialGroup()
                        .addComponent(lbComprador)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbComprador, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelMainLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelMainLayout.setVerticalGroup(
            panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbComprador)
                    .addComponent(cbComprador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbCarro)
                    .addComponent(lbAnunciante))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnContrato)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVender)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbCompradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCompradorActionPerformed
        if (listenerComprador) {
            taComprador.setText(dadosCliente((Cliente)cbComprador.getSelectedItem()));
            carro.setComprador((Cliente)cbComprador.getSelectedItem());
        }
    }//GEN-LAST:event_cbCompradorActionPerformed

    private void btnContratoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContratoActionPerformed
        gerarContrato();
    }//GEN-LAST:event_btnContratoActionPerformed

    private void btnVenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVenderActionPerformed
        if (JOptionPane.showConfirmDialog(this, "Deseja concretizar a venda? ", "Confirmar Venda", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            carro.setDataVenda(LocalDate.now());
            JOptionPane.showMessageDialog(this, "Venda realizada com sucesso!");
            vendidoSucesso = true;
            this.setVisible(false);
        }
    }//GEN-LAST:event_btnVenderActionPerformed

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
            java.util.logging.Logger.getLogger(FrameVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameVenda(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnContrato;
    private javax.swing.JButton btnVender;
    private javax.swing.JComboBox<Cliente> cbComprador;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbAnunciante;
    private javax.swing.JLabel lbCarro;
    private javax.swing.JLabel lbComprador;
    private javax.swing.JPanel panelMain;
    private javax.swing.JTextArea taAnunciante;
    private javax.swing.JTextArea taCarro;
    private javax.swing.JTextArea taComprador;
    // End of variables declaration//GEN-END:variables
}
