package telas;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import modelos.Marca;
import persistencia.FileControlador;
import persistencia.MarcaControlador;
import telas.renderers.MarcaRenderer;

/**
 *
 * @author Jonathan
 */
public class FrameMarca extends javax.swing.JDialog {

    private JList<Marca> listMarcas;
    private boolean frameAbriu;
    private boolean listenerAtivo;
    private MarcaControlador marcaControlador;
    
    private Marca marca;
    private List<Marca> lista;
    private String pastaImg = FileControlador.urlImagens() + "marca/";
    
    
    public FrameMarca() {
        initComponents();
        setLocationRelativeTo(null);
        setModal(true);
        
        marcaControlador = new MarcaControlador();
        frameAbriu = true;
        listenerAtivo = true;
        atualizarListaMarcas();
    }
    
    private JList<Marca> criarListaMarcas() {
        DefaultListModel<Marca> model = new DefaultListModel<>();
        List<Marca> marcas;
        
        if (frameAbriu){
            lista = marcaControlador.recuperarLista(); 
            marcas = lista;
            frameAbriu = false;
        } else {
            marcas = this.lista;
        }
        
        for (Marca m : marcas) {
            model.addElement(m);
        }
        
        JList<Marca> retorno = new JList<>(model);
        retorno.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        retorno.setCellRenderer(new MarcaRenderer());
        
        retorno.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && listenerAtivo) {
                    marca = retorno.getSelectedValue();
                    popularCampos();
                }
            }
        });
        
        return retorno;
    }
    
    private void atualizarListaMarcas() {
        scrollLista.setViewportView(listMarcas = criarListaMarcas());
    }
    
    // validação do formulário
    private boolean isFormularioValido(){
        if (tfNome.getText().length() < 1){
            JOptionPane.showMessageDialog(this, "Insira um nome válido para a marca");
            return false;
        } else if (tfPais.getText().length() < 1) {
            JOptionPane.showMessageDialog(this, "Insira um país válido para a marca");
            return false;
        } else {
            return true;
        }
    }
    
    private boolean isImagemValida(File file) {
        String nome = file.getName().substring(file.getName().lastIndexOf(".")+1);
        return nome.equals("jpg") || nome.equals("jpeg") || nome.equals("png") || nome.equals("bmp");
    }
    
    private void subirImg() {
        try {
            JFileChooser fc = new JFileChooser();
            fc.showDialog(this, "Abrir");
            File f = fc.getSelectedFile();
            if (isImagemValida(f)){
                BufferedImage bi = ImageIO.read(f);
                picLogo.setIcon(new ImageIcon(bi.getScaledInstance(194, 194, Image.SCALE_DEFAULT)));
            } else {
                JOptionPane.showMessageDialog(this, "Formato de arquivo inválido.\nDeve ser .jpg, .jpeg, .png ou .bmp");
            }
        } catch (IOException e) {
            System.out.println("Deu bosta na hora de subir a img chefe.");
        } catch (Exception e) {
            System.out.println("Deu bosta na hora de subir a img chefe.");
        }
    }
    
    private void limparImg() {
        picLogo.setIcon(new ImageIcon(getClass().getResource("/sem_foto.jpg")));
    }
    
    private void limparTudo() {
        limparImg();
        tfNome.setText("");
        tfPais.setText("");
    }
    
    private void novaMarca() {
        marca = null;
        tfCodigo.setText("");
        listenerAtivo = false;
        listMarcas.clearSelection();
        listenerAtivo = true;
        limparTudo();
    }
    
    private void salvarImg(String nome) {
        try {
            BufferedImage bi = new BufferedImage(
                    picLogo.getIcon().getIconWidth(),
                    picLogo.getIcon().getIconHeight(),
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = bi.createGraphics();
            picLogo.getIcon().paintIcon(null, g, 0, 0);
            g.dispose();
            ImageIO.write(bi, "jpg", new File(pastaImg + nome + ".jpg"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private int gerarNovoCodigo() {
        if (lista.isEmpty())
            return 1;
        else
            return (lista.get(lista.size() - 1).getCodigo()) + 1;
    }
    
    private Marca criarMarca() {
        Marca m = new Marca();
        m.setCodigo(gerarNovoCodigo());
        m.setNome(tfNome.getText());
        m.setPais(tfPais.getText());
        m.setLogo(pastaImg + m.getNome() + ".jpg");
        salvarImg(m.getNome());
        return m;
    }
    
    private void salvarMarca() {
        if (isFormularioValido()) {
            if (marca == null) {
                lista.add(criarMarca());
                JOptionPane.showMessageDialog(this, "Marca adicionada com sucesso");
            } else {
                editarMarca();
                JOptionPane.showMessageDialog(this, "Marca " + marca.getNome() + " atualizada com sucesso");
            }
            atualizarListaMarcas();
            novaMarca();
        }
    }
    
    private void apagarImgVelha() {
        try {
            File f = new File(marca.getLogo());
            f.delete();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void editarMarca() {
        apagarImgVelha();
        marca.setCodigo(Integer.parseInt(tfCodigo.getText()));
        marca.setNome(tfNome.getText());
        marca.setPais(tfPais.getText());
        marca.setLogo(pastaImg + marca.getNome() + ".jpg");
        salvarImg(marca.getNome());
        lista.set(listMarcas.getSelectedIndex(), marca);
    }
    
    private void persistirLista() {
        marcaControlador.gravarLista(lista);
    }
    
    private void abrirImg() {
        try {
            BufferedImage bi = ImageIO.read(new File(marca.getLogo()));
            picLogo.setIcon(new ImageIcon(bi.getScaledInstance(194, 194, Image.SCALE_DEFAULT)));
        } catch (IOException e) {
            System.out.println("Deu bosta na hora de subir a img chefe.");
        }
    }
    
    private void popularCampos() {
        if (marca != null) {
            tfCodigo.setText(Integer.toString(marca.getCodigo()));
            tfNome.setText(marca.getNome());
            tfPais.setText(marca.getPais());
            abrirImg();
        }
    }
    
    private void excluirMarca() {
        if (marca != null) {
            if (JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir a marca " + marca.getNome() + "?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                lista.remove(listMarcas.getSelectedIndex());
                JOptionPane.showMessageDialog(this, "Marca excluída com sucesso");
                atualizarListaMarcas();
                novaMarca();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma marca para excluí-la");
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
        lbCodigo = new javax.swing.JLabel();
        tfCodigo = new javax.swing.JTextField();
        lbNome = new javax.swing.JLabel();
        lbPaisOrigem = new javax.swing.JLabel();
        lbLogo = new javax.swing.JLabel();
        tfNome = new javax.swing.JTextField();
        tfPais = new javax.swing.JTextField();
        picLogo = new javax.swing.JLabel();
        btnLimparPic = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnPic = new javax.swing.JButton();
        panelConsulta = new javax.swing.JPanel();
        scrollLista = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Marca");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        panelCadastro.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações da Marca"));

        lbCodigo.setText("Código:");

        tfCodigo.setEnabled(false);

        lbNome.setText("Nome:");

        lbPaisOrigem.setText("País de Origem:");

        lbLogo.setText("Logo:");

        picLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sem_foto.jpg"))); // NOI18N
        picLogo.setText("jLabel4");

        btnLimparPic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/telas/recursos/broom-52_40x40.png"))); // NOI18N
        btnLimparPic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparPicActionPerformed(evt);
            }
        });

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

        btnPic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/telas/recursos/camera-identification-64_40x40.png"))); // NOI18N
        btnPic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPicActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCadastroLayout = new javax.swing.GroupLayout(panelCadastro);
        panelCadastro.setLayout(panelCadastroLayout);
        panelCadastroLayout.setHorizontalGroup(
            panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadastroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCadastroLayout.createSequentialGroup()
                        .addComponent(lbCodigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCadastroLayout.createSequentialGroup()
                        .addComponent(btnNovo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalvar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCadastroLayout.createSequentialGroup()
                        .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCadastroLayout.createSequentialGroup()
                                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbPaisOrigem)
                                    .addComponent(lbLogo)
                                    .addComponent(lbNome))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCadastroLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnPic, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnLimparPic, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfNome)
                            .addComponent(picLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfPais))))
                .addContainerGap())
        );
        panelCadastroLayout.setVerticalGroup(
            panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadastroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbCodigo)
                    .addComponent(tfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbNome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbPaisOrigem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(picLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelCadastroLayout.createSequentialGroup()
                        .addComponent(lbLogo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLimparPic, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPic, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(btnLimpar)
                    .addComponent(btnExcluir)
                    .addComponent(btnNovo))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        panelConsulta.setBorder(javax.swing.BorderFactory.createTitledBorder("Lista de Marcas Cadastradas"));

        javax.swing.GroupLayout panelConsultaLayout = new javax.swing.GroupLayout(panelConsulta);
        panelConsulta.setLayout(panelConsultaLayout);
        panelConsultaLayout.setHorizontalGroup(
            panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollLista, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelConsultaLayout.setVerticalGroup(
            panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollLista)
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
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnPicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPicActionPerformed
        subirImg();
    }//GEN-LAST:event_btnPicActionPerformed

    private void btnLimparPicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparPicActionPerformed
        limparImg();
    }//GEN-LAST:event_btnLimparPicActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limparTudo();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        novaMarca();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        salvarMarca();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        persistirLista();
    }//GEN-LAST:event_formWindowClosing

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        excluirMarca();
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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrameMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameMarca().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnLimparPic;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPic;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel lbCodigo;
    private javax.swing.JLabel lbLogo;
    private javax.swing.JLabel lbNome;
    private javax.swing.JLabel lbPaisOrigem;
    private javax.swing.JPanel panelCadastro;
    private javax.swing.JPanel panelConsulta;
    private javax.swing.JLabel picLogo;
    private javax.swing.JScrollPane scrollLista;
    private javax.swing.JTextField tfCodigo;
    private javax.swing.JTextField tfNome;
    private javax.swing.JTextField tfPais;
    // End of variables declaration//GEN-END:variables
}
