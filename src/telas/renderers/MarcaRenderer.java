package telas.renderers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import modelos.Marca;

/**
 *
 * @author Jonathan
 */
public class MarcaRenderer extends JPanel implements ListCellRenderer<Marca>{
    private JLabel lbCod = new JLabel();
    private JLabel lbIcon = new JLabel();
    private JLabel lbNome = new JLabel();
    private JLabel lbPais = new JLabel();
    
    public MarcaRenderer(){
        setLayout(new BorderLayout(2,2));
        
        JPanel panelText = new JPanel(new GridLayout(0, 1));
        panelText.add(lbCod);
        panelText.add(lbNome);
        panelText.add(lbPais);
        add(lbIcon, BorderLayout.WEST);
        add(panelText, BorderLayout.CENTER);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Marca> list, Marca value, int index, boolean isSelected, boolean cellHasFocus) {
        try {
            File f = new File(value.getLogo());
            BufferedImage bi = ImageIO.read(f);
            lbIcon.setIcon(new ImageIcon(bi.getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
            lbCod.setText(Integer.toString(value.getCodigo()));
            lbNome.setText(value.getNome());
            lbNome.setForeground(Color.BLUE);
            lbPais.setText(value.getPais());
            
            lbCod.setOpaque(true);
            lbIcon.setOpaque(true);
            lbNome.setOpaque(true);
            lbPais.setOpaque(true);
            
            if (isSelected) {
                lbCod.setBackground(list.getSelectionBackground());
                lbIcon.setBackground(list.getSelectionBackground());
                lbNome.setBackground(list.getSelectionBackground());
                lbPais.setBackground(list.getSelectionBackground());
            } else {
                lbCod.setBackground(list.getBackground());
                lbIcon.setBackground(list.getBackground());
                lbNome.setBackground(list.getBackground());
                lbPais.setBackground(list.getBackground());
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return this;
    }
}
