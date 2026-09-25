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

    BufferedImage bufferedImage5 = HerramientasImagen.toBufferedImage(imagen);

    int[] histogramaR = new int[256];
    int[] histogramaG = new int[256];
    int[] histogramaB = new int[256];

    for (int i = 0; i < bufferedImage5.getWidth(); i++) {
        for (int j = 0; j < bufferedImage5.getHeight(); j++) {
            int pixel = bufferedImage5.getRGB(i, j);
            int r = (pixel >> 16) & 0xFF;
            int g = (pixel >> 8)  & 0xFF;
            int b =  pixel        & 0xFF;
            histogramaR[r]++;
            histogramaG[g]++;
            histogramaB[b]++;
        }
    }

    Image imagenHistogramaR = generarImagenHistograma(histogramaR, new Color(255, 0, 0), "Rojo");
    JFrameImagen jFrameImagenHistogramaR = new JFrameImagen(imagenHistogramaR, "Histograma R");

    Image imagenHistogramaG = generarImagenHistograma(histogramaG, new Color(0, 255, 0), "Verde");
    JFrameImagen jFrameImagenHistogramaG = new JFrameImagen(imagenHistogramaG, "Histograma G");

    Image imagenHistogramaB = generarImagenHistograma(histogramaB, new Color(0, 0, 255), "Azul");
    JFrameImagen jFrameImagenHistogramaB = new JFrameImagen(imagenHistogramaB, "Histograma B");
}

public static Image generarImagenHistograma(int[] histograma, Color color, String titulo) {
    int anchoGrafica  = 256;   // 256 valores posibles
    int altoGrafica   = 300;   // altura fija

    // Crear imagen en blanco (fondo blanco)
    BufferedImage imagenHist = new BufferedImage(anchoGrafica, altoGrafica, BufferedImage.TYPE_INT_RGB);

    Color blanco = Color.WHITE;
    for (int i = 0; i < anchoGrafica; i++) {
        for (int j = 0; j < altoGrafica; j++) {
            imagenHist.setRGB(i, j, blanco.getRGB());
        }
    }

    int maxFrecuencia = 0;
    for (int i = 0; i < 256; i++) {
        if (histograma[i] > maxFrecuencia) {
            maxFrecuencia = histograma[i];
        }
    }

    if (maxFrecuencia == 0) maxFrecuencia = 1;

    for (int x = 0; x < 256; x++) {
        int alturaBarra = (int) (((double) histograma[x] / maxFrecuencia) * (altoGrafica - 1));
        for (int y = 0; y < alturaBarra; y++) {
            int posY = (altoGrafica - 1) - y;
            imagenHist.setRGB(x, posY, color.getRGB());
        }
    }

    return HerramientasImagen.toImage(imagenHist);
}

