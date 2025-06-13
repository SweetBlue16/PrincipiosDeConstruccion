package practicasprofesionaleslis.utilidades;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.*;

public class PDFUtils {
    private static PDDocument documentoPDF;
    private static PDFRenderer renderizadorPDF;
    private static int paginaActual = 0;
    private static int totalPaginas = 0;
    private static ImageView visorImagen;

    public static void cargarDesdeRecursos(String rutaRelativa, ImageView visor) {
        InputStream entrada = PDFUtils.class.getResourceAsStream(rutaRelativa);
        if (entrada == null) {
            return;
        }
        cargarDesdeStream(entrada, visor);
    }

    public static void cargarDesdeStream(InputStream entrada, ImageView visor) {
        try {
            File archivoTemporal = File.createTempFile("pdf_temporal", ".pdf");
            try (FileOutputStream salida = new FileOutputStream(archivoTemporal)) {
                byte[] buffer = new byte[1024];
                int bytesLeidos;
                while ((bytesLeidos = entrada.read(buffer)) != -1) {
                    salida.write(buffer, 0, bytesLeidos);
                }
            }

            documentoPDF = PDDocument.load(archivoTemporal);
            renderizadorPDF = new PDFRenderer(documentoPDF);
            paginaActual = 0;
            totalPaginas = documentoPDF.getNumberOfPages();
            visorImagen = visor;

            mostrarPaginaActual();
            archivoTemporal.deleteOnExit();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mostrarPaginaActual() {
        if (documentoPDF == null || renderizadorPDF == null || visorImagen == null) return;
        try {
            BufferedImage imagen = renderizadorPDF.renderImageWithDPI(paginaActual, 150);
            Image imagenFX = SwingFXUtils.toFXImage(imagen, null);
            visorImagen.setImage(imagenFX);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mostrarPaginaSiguiente() {
        if (paginaActual < totalPaginas - 1) {
            paginaActual++;
            mostrarPaginaActual();
        }
    }

    public static void mostrarPaginaAnterior() {
        if (paginaActual > 0) {
            paginaActual--;
            mostrarPaginaActual();
        }
    }

    public static void cerrarDocumento() {
        try {
            if (documentoPDF != null) {
                documentoPDF.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int obtenerPaginaActual() {
        return paginaActual + 1;
    }

    public static int obtenerTotalPaginas() {
        return totalPaginas;
    }
}
