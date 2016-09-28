/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import modelos.Carro;
import modelos.Cliente;
import persistencia.CarroControlador;

/**
 *
 * @author Jonathan
 */
public class FramePrincipal extends javax.swing.JFrame {
    
    private boolean frameAbriu;
    private boolean listenerLista;
    
    private CarroControlador carroControlador;
    
    private ImageIcon[] listaPics = {null, null, null};
    private int picSelecionada;
    
    private Carro carro;
    private List<Carro> lista;
    
    public FramePrincipal() {
        initComponents();
        setLocationRelativeTo(null);
        
        frameAbriu = true;
        listenerLista = true;
        
        carroControlador = new CarroControlador();
        
        setarNoImg();
        atualizarListaCarros();
    }
    
    private void setarNoImg() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/sem_foto.jpg"));
        BufferedImage bi = new BufferedImage(
                        icon.getIconWidth(),
                        icon.getIconHeight(),
                        BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        icon.paintIcon(null, g, 0, 0);
        g.dispose();
        picCarro.setIcon(new ImageIcon(bi.getScaledInstance(280, 190, Image.SCALE_DEFAULT)));
    }
    
    private void atualizarListaCarros() {
        scrollCarros.setViewportView(listCarros = criarListaCarros());
    }
    
    private JTable criarListaCarros() {
        if (frameAbriu) {
            lista = carroControlador.recuperarListaAVenda();
            // frameAbriu = false;
        }
        
        String[] headers = {"Cod.", "Marca", "Modelo", "Versão", "KM", "Anunciante"};
        TableModel model = new DefaultTableModel(headers, lista.size()){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (int i = 0; i < lista.size(); i++) {
            model.setValueAt(lista.get(i).getCodigo(), i, 0);
            model.setValueAt(lista.get(i).getVersao().getModelo().getMarca(), i, 1);
            model.setValueAt(lista.get(i).getVersao().getModelo(), i, 2);
            model.setValueAt(lista.get(i).getVersao().toString(), i, 3);
            model.setValueAt(lista.get(i).getKm(), i, 4);
            model.setValueAt(lista.get(i).getAnunciante().toString(), i, 5);
        }
        
        JTable retorno = new JTable(model);
        retorno.getColumnModel().getColumn(0).setPreferredWidth(35);
        retorno.getColumnModel().getColumn(1).setPreferredWidth(100);
        retorno.getColumnModel().getColumn(2).setPreferredWidth(100);
        retorno.getColumnModel().getColumn(3).setPreferredWidth(265);
        retorno.getColumnModel().getColumn(4).setPreferredWidth(100);
        retorno.getColumnModel().getColumn(5).setPreferredWidth(178);
        retorno.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        retorno.getTableHeader().setResizingAllowed(false);
        retorno.getTableHeader().setReorderingAllowed(false);
        
        retorno.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && listenerLista) {
                    carro = lista.get(retorno.getSelectedRow());
                    popularCampos();
                }
            }
        });
        return retorno;
    }
    
    private String enderecoCompleto(Cliente cliente) {
        String retorno = cliente.getEndereco().getRua() + ", " +
                cliente.getEndereco().getNumero();
        return retorno;
    } 
    
    private void carregarImgs() {
        try {
            int i = 0;
            for (String caminho : carro.getFotos()) {
                BufferedImage bi = ImageIO.read(new File(caminho));
                listaPics[i] = new ImageIcon(bi.getScaledInstance(280, 190, Image.SCALE_DEFAULT));
                i++;
            }
            picCarro.setIcon(listaPics[0]);
            picSelecionada = 1;
        } catch (IOException e) {
            System.out.println("Deu bosta na hora de subir a img chefe.");
        }
    }
    
    private void popularCampos() {
        if (carro != null) {
            carregarImgs();
            tfMarca.setText(carro.getVersao().getModelo().getMarca().getNome());
            tfModelo.setText(carro.getVersao().getModelo().getNome()+" - "+carro.getVersao().getModelo().getAno());
            tfVersao.setText(carro.getVersao().toString());
            tfCodigo.setText(carro.getCodigo().toString());
            tfKm.setText(carro.getKm().toString());
            tfAnunciante.setText("ID: " + carro.getAnunciante().getCodigo() + " - " + carro.getAnunciante().getNome());
            tfCpf.setText(carro.getAnunciante().getCpf());
            tfEndereço.setText(enderecoCompleto(carro.getAnunciante()));
            tfEmail.setText(carro.getAnunciante().getEmail());
            tfDataAnuncio.setText(carro.getDataAnuncio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
    }
    
    private void trocarPicSelecionada(int i) {
        if (i == 1) {
            picSelecionada = 1;
            picCarro.setIcon(listaPics[0]);
        } else if (i == 2) {
            picSelecionada = 2;
            picCarro.setIcon(listaPics[1]);
        } else {
            picSelecionada = 3;
            picCarro.setIcon(listaPics[2]);
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
        panelCarros = new javax.swing.JPanel();
        scrollCarros = new javax.swing.JScrollPane();
        listCarros = new javax.swing.JTable();
        panelInfoCarro = new javax.swing.JPanel();
        picCarro = new javax.swing.JLabel();
        btnPic1 = new javax.swing.JButton();
        btnPic2 = new javax.swing.JButton();
        btnPic3 = new javax.swing.JButton();
        lbCodigo = new javax.swing.JLabel();
        tfCodigo = new javax.swing.JTextField();
        tfMarca = new javax.swing.JTextField();
        lbMarca = new javax.swing.JLabel();
        tfModelo = new javax.swing.JTextField();
        lbModelo = new javax.swing.JLabel();
        tfVersao = new javax.swing.JTextField();
        lbVersao = new javax.swing.JLabel();
        lbKm = new javax.swing.JLabel();
        tfKm = new javax.swing.JTextField();
        lbAnunciante = new javax.swing.JLabel();
        lbCPF = new javax.swing.JLabel();
        tfCpf = new javax.swing.JTextField();
        lbEndereco = new javax.swing.JLabel();
        tfEndereço = new javax.swing.JTextField();
        lbEmail = new javax.swing.JLabel();
        tfEmail = new javax.swing.JTextField();
        tfAnunciante = new javax.swing.JTextField();
        lbDataAnuncio = new javax.swing.JLabel();
        tfDataAnuncio = new javax.swing.JTextField();
        btnVender = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java Motors");
        setResizable(false);

        btnMenuMarca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/telas/recursos/BMW-64.png"))); // NOI18N
        btnMenuMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuMarcaActionPerformed(evt);
            }
        });

        btnMenuModelo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/telas/recursos/Accounting-64.png"))); // NOI18N
        btnMenuModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuModeloActionPerformed(evt);
            }
        });

        btnMenuVersao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/telas/recursos/Automotive-64.png"))); // NOI18N
        btnMenuVersao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuVersaoActionPerformed(evt);
            }
        });

        lbMarcas.setText("Marcas");

        lbModelos.setText("Modelos");

        lbVersoes.setText("Versões");

        btnMenuCarro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/telas/recursos/Car-64.png"))); // NOI18N
        btnMenuCarro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuCarroActionPerformed(evt);
            }
        });

        btnMenuCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/telas/recursos/Address Book-64.png"))); // NOI18N
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
                    .addComponent(btnMenuCarro, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbCarros))
                .addGap(112, 112, 112)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMenuCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbClientes))
                .addGap(112, 112, 112)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMenuMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbMarcas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMenuModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbModelos))
                .addGap(100, 100, 100)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMenuVersao, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbVersoes))
                .addContainerGap())
        );
        panelMenuLayout.setVerticalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnMenuCarro, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMenuCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMenuMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMenuModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMenuVersao, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbCarros)
                    .addComponent(lbClientes)
                    .addComponent(lbMarcas)
                    .addComponent(lbModelos)
                    .addComponent(lbVersoes))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelCarros.setBorder(javax.swing.BorderFactory.createTitledBorder("Carros a venda"));

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
        scrollCarros.setViewportView(listCarros);

        javax.swing.GroupLayout panelCarrosLayout = new javax.swing.GroupLayout(panelCarros);
        panelCarros.setLayout(panelCarrosLayout);
        panelCarrosLayout.setHorizontalGroup(
            panelCarrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCarrosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollCarros)
                .addContainerGap())
        );
        panelCarrosLayout.setVerticalGroup(
            panelCarrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCarrosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollCarros, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelInfoCarro.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações do carro"));

        btnPic1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/telas/recursos/1-64.png"))); // NOI18N
        btnPic1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPic1ActionPerformed(evt);
            }
        });

        btnPic2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/telas/recursos/2-64.png"))); // NOI18N
        btnPic2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPic2ActionPerformed(evt);
            }
        });

        btnPic3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/telas/recursos/3-64.png"))); // NOI18N
        btnPic3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPic3ActionPerformed(evt);
            }
        });

        lbCodigo.setText("Código:");

        tfCodigo.setEnabled(false);

        tfMarca.setEnabled(false);

        lbMarca.setText("Marca:");

        tfModelo.setEnabled(false);

        lbModelo.setText("Modelo:");

        tfVersao.setEnabled(false);

        lbVersao.setText("Versão:");

        lbKm.setText("KM:");

        tfKm.setEnabled(false);

        lbAnunciante.setText("Anunciante:");

        lbCPF.setText("CPF:");

        tfCpf.setEnabled(false);

        lbEndereco.setText("Endereço:");

        tfEndereço.setEnabled(false);

        lbEmail.setText("E-Mail:");

        tfEmail.setEnabled(false);

        tfAnunciante.setEnabled(false);

        lbDataAnuncio.setText("Data de Anuncio:");

        tfDataAnuncio.setEnabled(false);

        btnVender.setIcon(new javax.swing.ImageIcon(getClass().getResource("/telas/recursos/Key Exchange-64.png"))); // NOI18N
        btnVender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVenderActionPerformed(evt);
            }
        });

        jLabel1.setText("Realizar Venda");

        javax.swing.GroupLayout panelInfoCarroLayout = new javax.swing.GroupLayout(panelInfoCarro);
        panelInfoCarro.setLayout(panelInfoCarroLayout);
        panelInfoCarroLayout.setHorizontalGroup(
            panelInfoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoCarroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoCarroLayout.createSequentialGroup()
                        .addComponent(btnPic1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPic2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPic3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(picCarro, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelInfoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoCarroLayout.createSequentialGroup()
                        .addGroup(panelInfoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbMarca)
                            .addComponent(tfModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbCodigo)
                            .addComponent(tfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbModelo)
                            .addComponent(lbVersao))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelInfoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelInfoCarroLayout.createSequentialGroup()
                                .addComponent(lbEmail)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(panelInfoCarroLayout.createSequentialGroup()
                                .addGroup(panelInfoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lbAnunciante)
                                    .addComponent(tfAnunciante)
                                    .addComponent(lbCPF)
                                    .addComponent(tfCpf)
                                    .addComponent(lbEndereco)
                                    .addComponent(tfEndereço, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1))))
                    .addGroup(panelInfoCarroLayout.createSequentialGroup()
                        .addGroup(panelInfoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelInfoCarroLayout.createSequentialGroup()
                                .addComponent(tfVersao, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelInfoCarroLayout.createSequentialGroup()
                                .addGroup(panelInfoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfKm, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbKm))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelInfoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbDataAnuncio)
                                    .addComponent(tfDataAnuncio, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addComponent(btnVender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelInfoCarroLayout.setVerticalGroup(
            panelInfoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoCarroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoCarroLayout.createSequentialGroup()
                        .addComponent(picCarro, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelInfoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelInfoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnPic1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnPic3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnPic2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(panelInfoCarroLayout.createSequentialGroup()
                        .addGroup(panelInfoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelInfoCarroLayout.createSequentialGroup()
                                .addComponent(lbCodigo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbMarca)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelInfoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tfMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelInfoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbModelo)
                                    .addComponent(lbEndereco)))
                            .addGroup(panelInfoCarroLayout.createSequentialGroup()
                                .addComponent(lbAnunciante)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfAnunciante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbCPF)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelInfoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfEndereço, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelInfoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbVersao)
                            .addComponent(lbEmail))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelInfoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfVersao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelInfoCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelInfoCarroLayout.createSequentialGroup()
                                .addComponent(lbKm)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfKm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelInfoCarroLayout.createSequentialGroup()
                                .addComponent(lbDataAnuncio)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfDataAnuncio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelInfoCarroLayout.createSequentialGroup()
                        .addComponent(btnVender, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelCarros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelInfoCarro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelCarros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelInfoCarro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        atualizarListaCarros();
    }//GEN-LAST:event_btnMenuCarroActionPerformed

    private void btnPic1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPic1ActionPerformed
        trocarPicSelecionada(1);
    }//GEN-LAST:event_btnPic1ActionPerformed

    private void btnPic2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPic2ActionPerformed
        trocarPicSelecionada(2);
    }//GEN-LAST:event_btnPic2ActionPerformed

    private void btnPic3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPic3ActionPerformed
        trocarPicSelecionada(3);
    }//GEN-LAST:event_btnPic3ActionPerformed

    private void btnVenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVenderActionPerformed
        if (carro != null) {
            FrameVenda fvenda = new FrameVenda(carro);
            fvenda.setVisible(true);
            if (fvenda.vendidoSucesso) {
                carroControlador.vender(fvenda.getCarroVendido());
                atualizarListaCarros();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um carro para vende-lo");
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
    private javax.swing.JButton btnPic1;
    private javax.swing.JButton btnPic2;
    private javax.swing.JButton btnPic3;
    private javax.swing.JButton btnVender;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lbAnunciante;
    private javax.swing.JLabel lbCPF;
    private javax.swing.JLabel lbCarros;
    private javax.swing.JLabel lbClientes;
    private javax.swing.JLabel lbCodigo;
    private javax.swing.JLabel lbDataAnuncio;
    private javax.swing.JLabel lbEmail;
    private javax.swing.JLabel lbEndereco;
    private javax.swing.JLabel lbKm;
    private javax.swing.JLabel lbMarca;
    private javax.swing.JLabel lbMarcas;
    private javax.swing.JLabel lbModelo;
    private javax.swing.JLabel lbModelos;
    private javax.swing.JLabel lbVersao;
    private javax.swing.JLabel lbVersoes;
    private javax.swing.JTable listCarros;
    private javax.swing.JPanel panelCarros;
    private javax.swing.JPanel panelInfoCarro;
    private javax.swing.JPanel panelMenu;
    private javax.swing.JLabel picCarro;
    private javax.swing.JScrollPane scrollCarros;
    private javax.swing.JTextField tfAnunciante;
    private javax.swing.JTextField tfCodigo;
    private javax.swing.JTextField tfCpf;
    private javax.swing.JTextField tfDataAnuncio;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfEndereço;
    private javax.swing.JTextField tfKm;
    private javax.swing.JTextField tfMarca;
    private javax.swing.JTextField tfModelo;
    private javax.swing.JTextField tfVersao;
    // End of variables declaration//GEN-END:variables
}
