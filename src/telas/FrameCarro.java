package telas;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import modelos.Carro;
import modelos.Cliente;
import modelos.Marca;
import modelos.Modelo;
import modelos.Versao;
import modelos.enums.Status;
import persistencia.CarroControlador;
import persistencia.ClienteControlador;
import persistencia.FileControlador;
import persistencia.MarcaControlador;
import persistencia.ModeloControlador;
import persistencia.VersaoControlador;
import telas.renderers.ModeloRenderer;

/**
 *
 * @author Jonathan
 */
public class FrameCarro extends javax.swing.JDialog {
    
    private MarcaControlador marcaControlador;
    private ModeloControlador modeloControlador;
    private VersaoControlador versaoControlador;
    private ClienteControlador clienteControlador;
    private CarroControlador carroControlador;
    
    private boolean frameAbriu;
    private boolean listenerMarca;
    private boolean listenerModelo;
    private boolean listenerCliente;
    private boolean listenerLista;
    
    private String pastaImg = FileControlador.urlImagens() + "carro/";
    private ImageIcon[] listaPics = {null, null, null};
    private int picSelecionada;
    
    private Carro carro;
    private List<Carro> lista;
    
    public FrameCarro() {
        initComponents();
        tfDataAnuncio.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        setLocationRelativeTo(null);
        setModal(true);
        
        frameAbriu = true;
        listenerLista = true;
        
        modeloControlador = new ModeloControlador();
        marcaControlador = new MarcaControlador();
        versaoControlador = new VersaoControlador();
        clienteControlador = new ClienteControlador();
        carroControlador = new CarroControlador();
        
        atualizarListaCarros();
        popularCbMarca();
        popularCbAnunciante();
        
        limparImg();
        
        listenerMarca = true;
        listenerCliente = true;
    }
    
    private JTable criarListaVersoes() {
        if (frameAbriu) {
            lista = carroControlador.recuperarLista();
            frameAbriu = false;
        }
        
        String[] headers = {"Cod.", "Marca", "Modelo", "Versão", "KM", "Anunciante", "Status"};
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
            model.setValueAt(lista.get(i).getStatus(), i, 6);
        }
        
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
                if (!e.getValueIsAdjusting() && listenerLista) {
                    carro = lista.get(retorno.getSelectedRow());
                    limparComprador();
                    popularCampos();
                    if (carro.getStatus() == Status.VENDIDO) {
                        disableTudo();
                    } else {
                        enableTudo();
                    }
                }
            }
        });
        return retorno;
    }
    
    private void disableTudo() {
        cbMarca.setEnabled(false);
        cbModelo.setEnabled(false);
        cbVersao.setEnabled(false);
        tfQuilometragem.setEnabled(false);
        tfCor.setEnabled(false);
        cbAnunciante.setEnabled(false);
        btnFoto.setEnabled(false);
    }
    
    private void enableTudo() {
        cbMarca.setEnabled(true);
        cbModelo.setEnabled(true);
        cbVersao.setEnabled(true);
        tfQuilometragem.setEnabled(true);
        tfCor.setEnabled(true);
        cbAnunciante.setEnabled(true);
        btnFoto.setEnabled(true);
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
    
    private void popularCbAnunciante() {
        for (Cliente cliente : clienteControlador.recuperarLista()) {
            cbAnunciante.addItem(cliente);
        }
        cbAnunciante.setSelectedIndex(-1);
    }
    
    private String enderecoCompleto(Cliente cliente) {
        String retorno = cliente.getEndereco().getRua() + ", " +
                cliente.getEndereco().getNumero();
        return retorno;
    } 
    
    private void popularDadosAnunciante() {
        Cliente c = (Cliente)cbAnunciante.getSelectedItem();
        tfCpf.setText(c.getCpf());
        tfEndereço.setText(enderecoCompleto(c));
        tfEmail.setText(c.getEmail());
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
                picCarro.setIcon(new ImageIcon(bi.getScaledInstance(194, 194, Image.SCALE_DEFAULT)));
                
                if (picSelecionada == 1)
                    listaPics[0] = (ImageIcon)picCarro.getIcon();
                else if (picSelecionada == 2)
                    listaPics[1] = (ImageIcon)picCarro.getIcon();
                else
                    listaPics[2] = (ImageIcon)picCarro.getIcon();
                
            } else {
                JOptionPane.showMessageDialog(this, "Formato de arquivo inválido.\nDeve ser .jpg, .jpeg, .png ou .bmp");
            }
        } catch (IOException e) {
            System.out.println("Deu bosta na hora de subir a img chefe.");
        } catch (Exception e) {
            System.out.println("Deu bosta na hora de subir a img chefe.");
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
    
    private boolean isFormularioValido() {
        if (cbVersao.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Favor selecionar uma Marca/Modelo/Versão.");
            return false;
        } else if (tfQuilometragem.getText().length() <= 0) {
            JOptionPane.showMessageDialog(this, "Insira uma quilometragem válida");
            return false;
        } else if (tfCor.getText().length() <= 0) {
            JOptionPane.showMessageDialog(this, "Insira uma cor válida");
            return false;
        } else if (cbAnunciante.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Favor selecionar um cliente válido");
            return false;
        } else {
            return true;
        }
    }
    
    private void limparImg() {
        picCarro.setIcon(new ImageIcon(getClass().getResource("/sem_foto.jpg")));
        for (int i = 0; i < 3; i++) {
            listaPics[i] = (ImageIcon)picCarro.getIcon();
        }
        picSelecionada = 1;
    }
    
    private void limpar() {
        listenerCliente = false;
        listenerMarca = false;
        listenerModelo = false;
        cbAnunciante.setSelectedIndex(-1);
        tfCpf.setText("");
        tfEndereço.setText("");
        tfEmail.setText("");
        cbVersao.setSelectedIndex(-1);
        cbModelo.setSelectedIndex(-1);
        cbMarca.setSelectedIndex(-1);
        listenerCliente = true;
        listenerMarca = true;
        listenerModelo = true;
        tfQuilometragem.setText("");
        tfCor.setText("");
        limparImg();
    }
    
    private void limparComprador() {
        tfComprador.setText("");
        tfCpfComprador.setText("");
        tfEmailComprador.setText("");
        tfEnderecoComprador.setText("");
        tfDataVenda.setText("");
    }
    
    private void novoCarro() {
        limpar();
        tfCodigo.setText("");
        tfDataAnuncio.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        limparComprador();
        listenerLista = false;
        listCarros.getSelectionModel().clearSelection();
        listenerLista = true;
        carro = null;
    }
    
    private String gerarPastaCarro(Carro carro) {
        String retorno = carro.getCodigo() + "_" + 
                carro.getVersao().getModelo().getNome() + "_" +
                carro.getVersao().getModelo().getAno() + "_" +
                carro.getAnunciante().getNome();
        return retorno;
    }
    
    private void salvarImgs(Carro carro) {
        try {
            Integer i = 0;
            File f = new File(pastaImg + gerarPastaCarro(carro));
            f.mkdir();
            for (ImageIcon icon : listaPics) {
                BufferedImage bi = new BufferedImage(
                        icon.getIconWidth(),
                        icon.getIconHeight(),
                        BufferedImage.TYPE_INT_RGB);
                Graphics g = bi.createGraphics();
                icon.paintIcon(null, g, 0, 0);
                g.dispose();
                ImageIO.write(bi, "jpg", new File(pastaImg + gerarPastaCarro(carro) + "/" + i + ".jpg"));
                i++;
            }
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
    
    private String[] pegarCaminhoFotos(Carro carro) {
        String[] retorno = {"", "", ""};
        int i = 0;
        for (ImageIcon icon : listaPics) {
            retorno[i] = pastaImg + gerarPastaCarro(carro) + "/" + i + ".jpg";
            i++;
        }
        return retorno;
    }
    
    private Carro criarCarro() {
        Carro carro = new Carro();
        carro.setCodigo(gerarNovoCodigo());
        carro.setVersao((Versao)cbVersao.getSelectedItem());
        carro.setKm(Float.parseFloat(tfQuilometragem.getText()));
        carro.setCor(tfCor.getText());
        carro.setDataAnuncio(LocalDate.now());
        carro.setAnunciante((Cliente) cbAnunciante.getSelectedItem());
        carro.setFotos(pegarCaminhoFotos(carro));
        carro.setStatus(Status.A_VENDA);
        salvarImgs(carro);
        return carro;
    }
    
    private void salvarCarro() {
        if (isFormularioValido()) {
            if (carro == null) {
                lista.add(criarCarro());
                JOptionPane.showMessageDialog(this, "Carro adicionado com sucesso");
            } else {
                editarCarro();
                JOptionPane.showMessageDialog(this, "Carro " + carro.getCodigo()+ " atualizado com sucesso");
            }
            atualizarListaCarros();
            novoCarro();
        }
    }
    
    private void editarCarro() {
        carro.setVersao((Versao)cbVersao.getSelectedItem());
        carro.setKm(Float.parseFloat(tfQuilometragem.getText()));
        carro.setCor(tfCor.getText());
        carro.setFotos(pegarCaminhoFotos(carro));
        carro.setAnunciante((Cliente) cbAnunciante.getSelectedItem());
        carro.setStatus(Status.A_VENDA);
        lista.set(listCarros.getSelectedRow(), carro);
        salvarImgs(carro);
    }
    
    private void persistirLista() {
        carroControlador.gravarLista(lista);
    }
    
    private void carregarImgs() {
        try {
            int i = 0;
            for (String caminho : carro.getFotos()) {
                BufferedImage bi = ImageIO.read(new File(caminho));
                listaPics[i] = new ImageIcon(bi.getScaledInstance(194, 194, Image.SCALE_DEFAULT));
                i++;
            }
            picCarro.setIcon(listaPics[0]);
            picSelecionada = 1;
        } catch (IOException e) {
            System.out.println("Deu bosta na hora de subir a img chefe.");
        }
    }
    
    private void setarCbMarca() {
        for (int i = 0; i < cbMarca.getItemCount(); i++) {
            if (cbMarca.getItemAt(i).getCodigo() == carro.getVersao().getModelo().getMarca().getCodigo())
                cbMarca.setSelectedIndex(i);
        }
    }
    
    private void setarCbModelo() {
        for (int i = 0; i < cbModelo.getItemCount(); i++) {
            if (cbModelo.getItemAt(i).getCodigo() == carro.getVersao().getModelo().getCodigo())
                cbModelo.setSelectedIndex(i);
        }
    }
    
    private void setarCbVersao() {
        for (int i = 0; i < cbVersao.getItemCount(); i++) {
            if (cbVersao.getItemAt(i).getCodigo() == carro.getVersao().getCodigo())
                cbVersao.setSelectedIndex(i);
        }
    }
    
    private void setarCbAnunciante() {
        for (int i = 0; i < cbAnunciante.getItemCount(); i++) {
            if (cbAnunciante.getItemAt(i).getCodigo() == carro.getAnunciante().getCodigo())
                cbAnunciante.setSelectedIndex(i);
        }
    }
    
    private void popularCampos() {
        if (carro != null) {
            carregarImgs();
            setarCbMarca();
            setarCbModelo();
            setarCbVersao();
            tfCodigo.setText(carro.getCodigo().toString());
            tfQuilometragem.setText(carro.getKm().toString());
            tfCor.setText(carro.getCor());
            setarCbAnunciante();
            tfCpf.setText(carro.getAnunciante().getCpf());
            tfEndereço.setText(enderecoCompleto(carro.getAnunciante()));
            tfEmail.setText(carro.getAnunciante().getEmail());
            tfDataAnuncio.setText(carro.getDataAnuncio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            if (carro.getComprador() != null) {
                tfComprador.setText("ID: " + Integer.toString(carro.getComprador().getCodigo()) + " - " +carro.getComprador().getNome());
                tfCpfComprador.setText(carro.getComprador().getCpf());
                tfEnderecoComprador.setText(enderecoCompleto(carro.getComprador()));
                tfEmailComprador.setText(carro.getComprador().getEmail());
                tfDataVenda.setText(carro.getDataVenda().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        }
    }
    
    private void excluirCarro() {
        if (carro != null) {
            if (JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o carro " + carro.getCodigo() + "?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                lista.remove(listCarros.getSelectedRow());
                JOptionPane.showMessageDialog(this, "Carro excluído com sucesso");
                atualizarListaCarros();
                novoCarro();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um carro para excluí-lo");
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
        picCarro = new javax.swing.JLabel();
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
        btnPic1 = new javax.swing.JButton();
        btnPic2 = new javax.swing.JButton();
        btnPic3 = new javax.swing.JButton();
        panelConsulta = new javax.swing.JPanel();
        scrollLista = new javax.swing.JScrollPane();
        listCarros = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Carro");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        panelCadastro.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações do Carro"));

        picCarro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sem_foto.jpg"))); // NOI18N

        btnFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/telas/recursos/camera-identification-64_40x40.png"))); // NOI18N
        btnFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFotoActionPerformed(evt);
            }
        });

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

        cbAnunciante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAnuncianteActionPerformed(evt);
            }
        });

        lbAnunciante.setText("Anunciante:");

        tfCpf.setEnabled(false);

        lbCPF.setText("CPF:");

        tfEndereço.setEnabled(false);

        lbEndereco.setText("Endereço:");

        tfEmail.setEnabled(false);

        lbEmail.setText("E-Mail:");

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnLimpar.setText("Limpar");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        btnNovo.setText("Novo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout panelCadastroLayout = new javax.swing.GroupLayout(panelCadastro);
        panelCadastro.setLayout(panelCadastroLayout);
        panelCadastroLayout.setHorizontalGroup(
            panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadastroLayout.createSequentialGroup()
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCadastroLayout.createSequentialGroup()
                        .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelCadastroLayout.createSequentialGroup()
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
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelCadastroLayout.createSequentialGroup()
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
                        .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(picCarro, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                            .addGroup(panelCadastroLayout.createSequentialGroup()
                                .addComponent(btnFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnPic1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPic2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPic3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelCadastroLayout.createSequentialGroup()
                                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbDataAnuncio)
                                    .addComponent(lbDataVenda))
                                .addGap(18, 18, 18)
                                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfDataVenda)
                                    .addComponent(tfDataAnuncio)))))
                    .addGroup(panelCadastroLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalvar)))
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
                        .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPic1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPic2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPic3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(lbEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbComprador)
                    .addComponent(tfComprador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbDataVenda)
                    .addComponent(tfDataVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(lbEmailComprador))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(btnLimpar)
                    .addComponent(btnNovo)
                    .addComponent(btnExcluir))
                .addContainerGap())
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
                .addComponent(scrollLista, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
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

    private void cbAnuncianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAnuncianteActionPerformed
        if (listenerCliente) {
            popularDadosAnunciante();
        }
    }//GEN-LAST:event_cbAnuncianteActionPerformed

    private void btnFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFotoActionPerformed
        subirImg();
    }//GEN-LAST:event_btnFotoActionPerformed

    private void btnPic1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPic1ActionPerformed
        trocarPicSelecionada(1);
    }//GEN-LAST:event_btnPic1ActionPerformed

    private void btnPic2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPic2ActionPerformed
        trocarPicSelecionada(2);
    }//GEN-LAST:event_btnPic2ActionPerformed

    private void btnPic3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPic3ActionPerformed
        trocarPicSelecionada(3);
    }//GEN-LAST:event_btnPic3ActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limpar();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        novoCarro();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        salvarCarro();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        excluirCarro();
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
    private javax.swing.JComboBox<Cliente> cbAnunciante;
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
