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

    /* Para hacer un recuadro verde limon y una bandera */

    BufferedImage bufferedImage1 = HerramientasImagen.toBufferedImage(imagen);
    BufferedImage bufferedImage2 = HerramientasImagen.toBufferedImage(imagen);
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
            bufferedImage1.setRGB(i, j, verdeLimon.getRGB());

            if(i < 50) {
                bufferedImage2.setRGB(i, j, verdeBandera.getRGB());
            }
            else if(i < 100) {
                bufferedImage2.setRGB(i, j, blanco.getRGB());
            }
            else {
                bufferedImage2.setRGB(i, j, rojo.getRGB());
            }

            double distance = Math.sqrt(Math.abs(xref - i)*Math.abs(xref - i) + Math.abs(yref - j)*Math.abs(yref - j));
            if(distance < radio) {
                bufferedImage2.setRGB(i, j, cafe.getRGB());
            }
        }
    }

    Image imagenModificada1 = HerramientasImagen.toImage(bufferedImage1);
    JFrameImagen jFrameImagen1 = new JFrameImagen(imagenModificada1, "Imagen Verde Limon");

    Image imagenModificada2 = HerramientasImagen.toImage(bufferedImage2);
    JFrameImagen jFrameImagen2 = new JFrameImagen(imagenModificada2, "Imagen Bandera");

    /* Para cambiar a rojo todo */

    BufferedImage bufferedImage3 = HerramientasImagen.toBufferedImage(imagen);
    for(int i = 0; i < bufferedImage3.getWidth(); i++) {
        for (int j = 0; j < bufferedImage3.getHeight(); j++) {
            bufferedImage3.setRGB(i, j, 16711680); // #FF0000 pasado a entero
        }
    }

    Image imagenModificada3 = HerramientasImagen.toImage(bufferedImage3);
    JFrameImagen jFrameImagen3 = new JFrameImagen(imagenModificada3, "Imagen Roja");

    /* Para subir o reducir temperatura (aumentar/disminuir 50 en rojo y contrario en azul) */

    BufferedImage bufferedImageAumentoTemp = HerramientasImagen.toBufferedImage(imagen);
    BufferedImage bufferedImageDisminuirTemp = HerramientasImagen.toBufferedImage(imagen);
    int colorImagenAum, rAum, gAum, bAum;
    for(int i = 0; i < bufferedImageAumentoTemp.getWidth(); i++) {
        for (int j = 0; j < bufferedImageAumentoTemp.getHeight(); j++) {
            colorImagenAum = bufferedImageAumentoTemp.getRGB(i, j);
            rAum = (colorImagenAum >> 16) & 0xFF;
            gAum = (colorImagenAum >> 8) & 0xFF;
            bAum = (colorImagenAum) & 0xFF;

            rAum = Math.min(255, rAum + 50);
            bAum = Math.max(0, bAum - 50);

            colorImagenAum = (rAum << 16) | (gAum << 8) | bAum;



            bufferedImageAumentoTemp.setRGB(i, j, colorImagenAum);
        }
    }

    Image imagenModificada4 = HerramientasImagen.toImage(bufferedImageAumentoTemp);
    JFrameImagen jFrameImagen4 = new JFrameImagen(imagenModificada4, "Aumento de temperatura");
}

