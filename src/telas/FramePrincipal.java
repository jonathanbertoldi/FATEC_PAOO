/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

/**
 *
 * @author Jonathan
 */
public class FramePrincipal extends javax.swing.JFrame {

    /**
     * Creates new form FramePrincipal
     */
    public FramePrincipal() {
        initComponents();
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMenu = new javax.swing.JPanel();
        btnMenuMarca = new javax.swing.JButton();
        btnMenuModelo = new javax.swing.JButton();
        btnMenuVersao = new javax.swing.JButton();
        lbMarcas = new javax.swing.JLabel();
        lbModelos = new javax.swing.JLabel();
        lbVersoes = new javax.swing.JLabel();
        btnMenuCarro = new javax.swing.JButton();
        btnMenuCliente = new javax.swing.JButton();
        lbCarros = new javax.swing.JLabel();
        lbClientes = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java Motors");
        setResizable(false);

        btnMenuMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuMarcaActionPerformed(evt);
            }
        });

        btnMenuModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuModeloActionPerformed(evt);
            }
        });

        btnMenuVersao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuVersaoActionPerformed(evt);
            }
        });

        lbMarcas.setText("Marcas");

        lbModelos.setText("Modelos");

        lbVersoes.setText("Versões");

        btnMenuCarro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuCarroActionPerformed(evt);
            }
        });

        btnMenuCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuClienteActionPerformed(evt);
            }
        });

        lbCarros.setText("Carros");

        lbClientes.setText("Clientes");

        javax.swing.GroupLayout panelMenuLayout = new javax.swing.GroupLayout(panelMenu);
        panelMenu.setLayout(panelMenuLayout);
        panelMenuLayout.setHorizontalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMenuLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnMenuModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnMenuVersao, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbMarcas, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbModelos, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbVersoes, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(panelMenuLayout.createSequentialGroup()
                        .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnMenuMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnMenuCarro, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnMenuCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(lbCarros, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lbClientes, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelMenuLayout.setVerticalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnMenuCarro, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbCarros)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMenuCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbClientes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMenuMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbMarcas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMenuModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbModelos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMenuVersao, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbVersoes)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(705, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMenuMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuMarcaActionPerformed
        FrameMarca fmarca = new FrameMarca();
        fmarca.setVisible(true);
    }//GEN-LAST:event_btnMenuMarcaActionPerformed

    private void btnMenuModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuModeloActionPerformed
        FrameModelo fmodelo = new FrameModelo();
        fmodelo.setVisible(true);
    }//GEN-LAST:event_btnMenuModeloActionPerformed

    private void btnMenuVersaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuVersaoActionPerformed
        FrameVersao fversao = new FrameVersao();
        fversao.setVisible(true);
    }//GEN-LAST:event_btnMenuVersaoActionPerformed

    private void btnMenuClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuClienteActionPerformed
        FrameCliente fcliente = new FrameCliente();
        fcliente.setVisible(true);
    }//GEN-LAST:event_btnMenuClienteActionPerformed

    private void btnMenuCarroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuCarroActionPerformed
        FrameCarro fcarro = new FrameCarro();
        fcarro.setVisible(true);
    }//GEN-LAST:event_btnMenuCarroActionPerformed

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FramePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FramePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FramePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FramePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FramePrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMenuCarro;
    private javax.swing.JButton btnMenuCliente;
    private javax.swing.JButton btnMenuMarca;
    private javax.swing.JButton btnMenuModelo;
    private javax.swing.JButton btnMenuVersao;
    private javax.swing.JLabel lbCarros;
    private javax.swing.JLabel lbClientes;
    private javax.swing.JLabel lbMarcas;
    private javax.swing.JLabel lbModelos;
    private javax.swing.JLabel lbVersoes;
    private javax.swing.JPanel panelMenu;
    // End of variables declaration//GEN-END:variables
}