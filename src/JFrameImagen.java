import javax.swing.*;
import java.awt.*;

public class JFrameImagen extends JFrame {
    private JLabel jLabel;

    public JFrameImagen(Image aux, String titulo) {
        jLabel = new JLabel();

        ImageIcon e = new ImageIcon(aux);
        jLabel.setIcon(e);
        add(jLabel);

        setTitle(titulo);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(e.getIconWidth(), e.getIconHeight());

        setVisible(true);
    }
}
