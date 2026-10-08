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

    // generarCuadroVerdeLimonYBandera(imagen);

    // generarImagenRoja(imagen);

    // generarImagenAumentoTemperatura(imagen);

    generarHistogramas(imagen);

    // Image imagenGris = convertirEscalaGrises(imagen);
    // generarHistogramasGris(imagenGris);

    //BufferedImage bufferedImage = HerramientasImagen.toBufferedImage(imagen);
    //run(bufferedImage);
    //Image imagen2 = HerramientasImagen.toImage(bufferedImage);
    //JFrameImagen jFrameImagen2 = new JFrameImagen(imagen2, "Imagen 2");
    //generarHistogramas(imagen2);

    Image imagenContraste = aumentarContraste(imagen, 2);

    generarHistogramas(imagenContraste);
}

public static void generarCuadroVerdeLimonYBandera(Image imagen) {
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
}

public static void generarImagenRoja(Image imagen) {
    BufferedImage bufferedImage3 = HerramientasImagen.toBufferedImage(imagen);
    for(int i = 0; i < bufferedImage3.getWidth(); i++) {
        for (int j = 0; j < bufferedImage3.getHeight(); j++) {
            bufferedImage3.setRGB(i, j, 16711680); // #FF0000 pasado a entero
        }
    }

    Image imagenModificada3 = HerramientasImagen.toImage(bufferedImage3);
    JFrameImagen jFrameImagen3 = new JFrameImagen(imagenModificada3, "Imagen Roja");
}

public static void generarImagenAumentoTemperatura(Image imagen) {
    BufferedImage bufferedImageAumentoTemp = HerramientasImagen.toBufferedImage(imagen);
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

public static void generarHistogramas(Image imagen) {
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

public static Image generarImagenHistogramaGris(int[] histograma, Color color, String titulo) {
    int anchoGrafica  = 256;   // 256 valores posibles
    int altoGrafica   = 300;   // altura fija

    // Crear imagen en blanco (fondo blanco)
    BufferedImage imagenHist = new BufferedImage(anchoGrafica, altoGrafica, BufferedImage.TYPE_BYTE_GRAY);

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

public static void generarHistogramasGris(Image imagen) {
    BufferedImage bufferedImage5 = HerramientasImagen.toBufferedImage(imagen);

    int[] histograma = new int[256];

    for (int i = 0; i < bufferedImage5.getWidth(); i++) {
        for (int j = 0; j < bufferedImage5.getHeight(); j++) {
            int pixel = bufferedImage5.getRGB(i, j);
            int gris =  pixel        & 0xFF;
            histograma[gris]++;
        }
    }

    Image imagenHistogramaR = generarImagenHistograma(histograma, new Color(100, 100, 100), "Rojo");
    JFrameImagen jFrameImagenHistogramaR = new JFrameImagen(imagenHistogramaR, "Histograma R");
}

public static Image generarImagenHistograma(int[] histograma, Color color, String titulo) {
    int anchoGrafica  = 512;   // 256 valores posibles
    int altoGrafica   = 550;   // altura fija

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

    for (int x = 0; x < 512; x = x + 2) {
        int alturaBarra = (int) (((double) histograma[x/2] / maxFrecuencia) * (altoGrafica - 1));
        for (int y = 0; y < alturaBarra; y++) {
            int posY = (altoGrafica - 1) - y;
            imagenHist.setRGB(x, posY, color.getRGB());
            imagenHist.setRGB(x + 1, posY, color.getRGB());
        }
    }

    return HerramientasImagen.toImage(imagenHist);
}

public static Image convertirEscalaGrises(Image image) {
    BufferedImage bufferedImage = HerramientasImagen.toBufferedImage(image);
    BufferedImage bufferedImageGray = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
    int colorImagen, r, g, b, gray;

    for(int i = 0; i < bufferedImage.getWidth(); i++) {
        for (int j = 0; j < bufferedImage.getHeight(); j++) {
            colorImagen = bufferedImage.getRGB(i, j);
            r = (colorImagen >> 16) & 0xFF;
            g = (colorImagen >> 8) & 0xFF;
            b = (colorImagen) & 0xFF;

            gray = Math.round((float) (r + g + b) / 3);
            int argb = (0xFF << 24) | (gray << 16) | (gray << 8) | gray;
            bufferedImageGray.setRGB(i, j, argb);
        }
    }

    Image imagenGris = HerramientasImagen.toImage(bufferedImageGray);

    JFrameImagen jFrameImagen = new JFrameImagen(imagenGris, "Imagen Gris");

    return imagenGris;
}

public static void run(BufferedImage ip) {
    int w = ip.getWidth();
    int h = ip.getHeight();

    for (int v = 0; v < h; v++) {
        for (int u = 0; u < w; u++) {
            int a = ip.getRGB(u,v);
            int b = (int) Math.min((a * 1.5 + 0.5), 255);
            ip.setRGB(u, v, b);
        }
    }
}

public static Image aumentarContraste(Image imagen, double factor) {
    BufferedImage bufferedImage = HerramientasImagen.toBufferedImage(imagen);

    int colorOriginal, r, g, b;
    int rNuevo, gNuevo, bNuevo;
    int puntoMedio = 128;

    for (int i = 0; i < bufferedImage.getWidth(); i++) {
        for (int j = 0; j < bufferedImage.getHeight(); j++) {
            colorOriginal = bufferedImage.getRGB(i, j);

            r = (colorOriginal >> 16) & 0xFF;
            g = (colorOriginal >> 8)  & 0xFF;
            b =  colorOriginal        & 0xFF;

            // Aplicar fórmula de contraste
            rNuevo = (int) (factor * (r - puntoMedio) + puntoMedio);
            gNuevo = (int) (factor * (g - puntoMedio) + puntoMedio);
            bNuevo = (int) (factor * (b - puntoMedio) + puntoMedio);

            // Saturar al rango [0, 255]
            rNuevo = Math.max(0, Math.min(255, rNuevo));
            gNuevo = Math.max(0, Math.min(255, gNuevo));
            bNuevo = Math.max(0, Math.min(255, bNuevo));

            int nuevoColor = (0xFF << 24) | (rNuevo << 16) | (gNuevo << 8) | bNuevo;
            bufferedImage.setRGB(i, j, nuevoColor);
        }
    }

    Image imagenModificada = HerramientasImagen.toImage(bufferedImage);
    JFrameImagen jFrame = new JFrameImagen(imagenModificada, "Imagen con Contraste x" + factor);

    return imagenModificada;
}