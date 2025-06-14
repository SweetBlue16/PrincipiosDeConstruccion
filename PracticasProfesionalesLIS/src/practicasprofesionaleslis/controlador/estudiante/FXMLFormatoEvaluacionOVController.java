package practicasprofesionaleslis.controlador.estudiante;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.PDFUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLFormatoEvaluacionOVController implements Initializable {
    private final String RUTA_RELATIVA = "/practicasprofesionaleslis/recursos/pdf/PRAIS-03-Autoevaluacion-del-alumno.pdf";
    private final String NOMBRE_ARCHIVO = "FormatoAutoevaluacionOV";

    @FXML
    private Label lblEncabezado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}    

    @FXML
    private void clicBtnDescargar(ActionEvent event) {
        boolean exito = PDFUtils.guardarPDFDesdeRecursos(RUTA_RELATIVA, NOMBRE_ARCHIVO);
        if (exito) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                    ConstantesUtils.TITULO_EXITO,
                    ConstantesUtils.ALERTA_DESCARGA_ARCHIVO_EXITOSA
            );
            VentanasUtils.cerrarVentana(lblEncabezado);
        } else {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_DESCARGA_ARCHIVO_FALLIDA
            );
        }
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(lblEncabezado);
    }
}
