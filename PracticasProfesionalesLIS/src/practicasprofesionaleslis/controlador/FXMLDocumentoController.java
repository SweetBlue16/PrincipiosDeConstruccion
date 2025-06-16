package practicasprofesionaleslis.controlador;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import practicasprofesionaleslis.utilidades.PDFUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLDocumentoController implements Initializable {

    @FXML
    private ImageView imgPDF;
    @FXML
    private Label lblPagina;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        actualizarEtiquetaPagina();
    }
    
    public void cargarDesdeStream(InputStream streamPDF) {
        PDFUtils.cargarPDFDesdeStream(streamPDF, imgPDF);
        actualizarEtiquetaPagina();
    }

    @FXML
    private void clicBtnAnterior(ActionEvent event) {
        PDFUtils.mostrarPaginaAnterior();
        actualizarEtiquetaPagina();
    }

    @FXML
    private void clicBtnSiguiente(ActionEvent event) {
        PDFUtils.mostrarPaginaSiguiente();
        actualizarEtiquetaPagina();
    }

    @FXML
    private void clicBtnCerrar(ActionEvent event) {
        PDFUtils.cerrarDocumento();
        VentanasUtils.cerrarVentana(lblPagina);
    }
    
    private void actualizarEtiquetaPagina() {
        lblPagina.setText("Página " + PDFUtils.obtenerPaginaActual() + " de " + PDFUtils.obtenerTotalPaginas());
    }
}
