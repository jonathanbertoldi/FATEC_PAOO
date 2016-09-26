package telas.renderers;

import java.awt.BorderLayout;
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
import modelos.Modelo;

/**
 *
 * @author Jonathan
 */
public class ModeloRenderer extends JPanel implements ListCellRenderer<Marca>{
    
    private JLabel lbIcon = new JLabel();
    private JLabel lbNome = new JLabel();
    
    public ModeloRenderer() {
        setLayout(new BorderLayout(2,2));
        
        JPanel panelText = new JPanel(new GridLayout(0, 1));
        panelText.add(lbNome);
        add(lbIcon, BorderLayout.WEST);
        add(panelText, BorderLayout.CENTER);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Marca> list, Marca value, int index, boolean isSelected, boolean cellHasFocus) {
        try {
            if (value != null) {
            File f = new File(value.getLogo());
            BufferedImage bi = ImageIO.read(f);
            lbIcon.setIcon(new ImageIcon(bi.getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
            lbNome.setText(value.getNome());
            
            lbIcon.setOpaque(true);
            lbNome.setOpaque(true);
            
            if (isSelected) {
                lbIcon.setBackground(list.getSelectionBackground());
                lbNome.setBackground(list.getSelectionBackground());
            } else {
                lbIcon.setBackground(list.getBackground());
                lbNome.setBackground(list.getBackground());
            }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return this;
    }
    
    
}
