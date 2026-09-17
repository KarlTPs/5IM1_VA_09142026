import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

void main() {
    try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch(Exception _) {

    }

    Image imagen = HerramientasImagen.abrirImagen();
    JFrameImagen jFrameImagen = new JFrameImagen(imagen, "Imagen");

    /*
    Antes de enviar a JFrame, cambiarlo a BufferedImage
     */

    BufferedImage bufferedImage = HerramientasImagen.toBufferedImage(imagen);
    BufferedImage bufferedImage1 = HerramientasImagen.toBufferedImage(imagen);
    Color verdeLimon = new Color(137, 243, 54);
    Color verdeBandera = new Color(20, 172, 8);
    Color blanco = new Color(255, 255, 255);
    Color rojo = new Color(189, 22, 22);
    Color cafe = new Color(156, 94, 23);

    int xref = 75;
    int yref = 75;
    int radio = 15;

    for(int i = 0; i < 150; i++)
    {
        for(int j = 0; j < 150; j++)
        {
            bufferedImage.setRGB(i, j, verdeLimon.getRGB());

            if(i < 50) {
                bufferedImage1.setRGB(i, j, verdeBandera.getRGB());
            }
            else if(i < 100) {
                bufferedImage1.setRGB(i, j, blanco.getRGB());
            }
            else {
                bufferedImage1.setRGB(i, j, rojo.getRGB());
            }

            double distance = Math.sqrt(Math.abs(xref - i)*Math.abs(xref - i) + Math.abs(yref - j)*Math.abs(yref - j));
            if(distance < radio) {
                bufferedImage1.setRGB(i, j, cafe.getRGB());
            }
        }
    }

    Image imagenModificada = HerramientasImagen.toImage(bufferedImage);
    JFrameImagen jFrameImagen1 = new JFrameImagen(imagenModificada, "Imagen Verde Limon");

    Image imagenModificada1 = HerramientasImagen.toImage(bufferedImage1);
    JFrameImagen jFrameImagen2 = new JFrameImagen(imagenModificada1, "Imagen Bandera");
}

