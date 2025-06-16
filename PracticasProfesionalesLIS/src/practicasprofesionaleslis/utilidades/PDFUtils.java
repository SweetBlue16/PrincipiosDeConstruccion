package practicasprofesionaleslis.utilidades;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.*;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import practicasprofesionaleslis.modelo.pojo.Estudiante;
import practicasprofesionaleslis.modelo.pojo.Expediente;
import practicasprofesionaleslis.modelo.pojo.Proyecto;
import practicasprofesionaleslis.modelo.pojo.ResponsableProyecto;

public class PDFUtils {
    private static PDDocument documentoPDF;
    private static PDFRenderer renderizadorPDF;
    private static int paginaActual = 0;
    private static int totalPaginas = 0;
    private static ImageView visorImagen;

    public static void cargarPDFDesdeRecursos(String rutaRelativa, ImageView visor) {
        InputStream entrada = PDFUtils.class.getResourceAsStream(rutaRelativa);
        if (entrada == null) {
            return;
        }
        cargarPDFDesdeStream(entrada, visor);
    }

    public static void cargarPDFDesdeStream(InputStream entrada, ImageView visor) {
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
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_CARGAR_DOCUMENTO
            );
        }
    }
    
    public static boolean guardarPDFDesdeRecursos(String rutaRelativa, String nombreArchivo) {
        try (InputStream entrada = PDFUtils.class.getResourceAsStream(rutaRelativa)) {
            if (entrada == null) {
                return false;
            }
            
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar archivo PDF");
            fileChooser.setInitialFileName(nombreArchivo + ".pdf");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));
            
            File archivoDestino = fileChooser.showSaveDialog(null);
            if (archivoDestino == null) {
                return false;
            }
            
            try (FileOutputStream salida = new FileOutputStream(archivoDestino)) {
                byte[] buffer = new byte[1024];
                int bytesLeidos;
                while ((bytesLeidos = entrada.read(buffer)) != -1) {
                    salida.write(buffer, 0, bytesLeidos);
                }
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void mostrarPaginaActual() {
        if (documentoPDF == null || renderizadorPDF == null || visorImagen == null) return;
        try {
            BufferedImage imagen = renderizadorPDF.renderImageWithDPI(paginaActual, 200);
            Image imagenFX = SwingFXUtils.toFXImage(imagen, null);
            visorImagen.setImage(imagenFX);
        } catch (IOException e) {
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_CARGAR_DOCUMENTO
            );
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
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_CARGAR_DOCUMENTO
            );
        }
    }

    public static int obtenerPaginaActual() {
        return paginaActual + 1;
    }

    public static int obtenerTotalPaginas() {
        return totalPaginas;
    }
    
    public static boolean guardarPDFConDatos(String rutaRelativa, String nombreArchivo, 
            Estudiante estudiante, Proyecto proyecto, Expediente expediente) {

        try (InputStream entrada = PDFUtils.class.getResourceAsStream(rutaRelativa)) {
            if (entrada == null) {
                return false;
            }

            PDDocument documento = PDDocument.load(entrada);
            PDPage pagina = documento.getPage(0);
            
            // Para plasmar datos en el PDF
            try (PDPageContentStream contenido = new PDPageContentStream(documento, pagina, PDPageContentStream.AppendMode.APPEND, true, true)) {
                
                contenido.setFont(PDType1Font.HELVETICA, 10);

                escribirTexto(contenido, 230, 657, 
                        estudiante.getNombre() + " " + 
                        estudiante.getApellidoPaterno() + " " + 
                        estudiante.getApellidoMaterno());
                escribirTexto(contenido, 230, 643, estudiante.getMatricula());
                if (proyecto.getOrganizacionVinculada() != null) {
                    escribirTexto(contenido, 230, 630, proyecto.getOrganizacionVinculada().getRazonSocial());
                }
                if (proyecto.getResponsableProyecto() != null) {
                    ResponsableProyecto responsable = proyecto.getResponsableProyecto();
                    String nombreResponsable = responsable.getNombre() + " " + 
                            responsable.getApellidoPaterno() + 
                            (responsable.getApellidoMaterno() != null ? " " + responsable.getApellidoMaterno() : "");
                    escribirTexto(contenido, 230, 603, nombreResponsable);
                }
                escribirTexto(contenido, 230, 590, proyecto.getNombre());
                escribirTexto(contenido, 230, 577, 
                        expediente.getHorasAcumuladas() > 0 ? 
                        String.valueOf(expediente.getHorasAcumuladas()) : "0");
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar archivo PDF");
            fileChooser.setInitialFileName(nombreArchivo + ".pdf");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));

            File archivoDestino = fileChooser.showSaveDialog(null);
            if (archivoDestino == null) {
                documento.close();
                return false;
            }
            
            documento.save(archivoDestino);
            documento.close();

            return true;
            
        } catch (IOException e) {
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_BD);
            return false;
        }
    }

    private static void escribirTexto(PDPageContentStream contenido, float x, float y, String texto) 
            throws IOException {
        if (texto != null && !texto.isEmpty()) {
            contenido.beginText();
            contenido.newLineAtOffset(x, y);
            contenido.showText(texto);
            contenido.endText();
        }
    }
    
}
