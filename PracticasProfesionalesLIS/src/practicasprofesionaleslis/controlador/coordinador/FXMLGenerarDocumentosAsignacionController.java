package practicasprofesionaleslis.controlador.coordinador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import practicasprofesionaleslis.modelo.pojo.Estudiante;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.PDFUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

/**
 * Autor: Yael A. Castillo
 * Fecha de creación: 09/06/2025
 * Descripción: Controla la ventana para generar
 * los documentos de asignación de prácticas.
 */
public class FXMLGenerarDocumentosAsignacionController implements Initializable {

    @FXML
    private CheckBox chkCartaAsignacion;
    @FXML
    private CheckBox chkOficioAsignacion;

    private Estudiante estudiante;
    private boolean tieneProyecto;
    
    @FXML
    private Label lbProyectoNoEncontrado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chkCartaAsignacion.setSelected(true);
        chkOficioAsignacion.setSelected(true);
    }

    public void inicializarDatosEstudiante(Estudiante estudiante, boolean tieneProyecto) {
        this.estudiante = estudiante;
        this.tieneProyecto = tieneProyecto;

        if (!tieneProyecto) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ADVERTENCIA,
                    "El estudiante no tiene proyecto asignado.");
            deshabilitarCheckboxes();
        }
    }

    private void deshabilitarCheckboxes() {
        chkOficioAsignacion.setDisable(true);
        chkCartaAsignacion.setDisable(true);
    }

    private boolean alMenosUnDocumentoSeleccionado() {
        return chkCartaAsignacion.isSelected()
                || chkOficioAsignacion.isSelected();
    }

    @FXML
    private void clicBtnDescargar(ActionEvent event) {
        if (!tieneProyecto) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ADVERTENCIA,
                    "El estudiante no tiene proyecto asignado.");
            return;
        }

        if (!alMenosUnDocumentoSeleccionado()) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ADVERTENCIA,
                    "Seleccione al menos un documento.");
            return;
        }

        generarDocumentos();
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(chkCartaAsignacion);
    }

    private void generarDocumentos() {
        try {
            if (!alMenosUnDocumentoSeleccionado()) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ADVERTENCIA,
                    "Seleccione al menos un documento.");
                return;
            }

            boolean exito = true;
            String matricula = estudiante.getMatricula();

            if (chkCartaAsignacion.isSelected()) {
                boolean descargado = PDFUtils.guardarPDFDesdeRecursos(
                        "/practicasprofesionaleslis/recursos/pdf/PRAIS-02-Aceptacion-1.pdf",
                        "Carta_Asignacion_" + matricula
                );
                if (!descargado) {
                    exito = false;
                }
            }

            if (chkOficioAsignacion.isSelected()) {
                boolean descargado = PDFUtils.guardarPDFDesdeRecursos(
                        "/practicasprofesionaleslis/recursos/pdf/F1-Solicitud-Practicas-3.pdf",
                        "Oficio_Asignacion_" + matricula
                );
                if (!descargado) {
                    exito = false;
                }
            }

            if (exito) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                    "Documentos generados",
                    "Los documentos seleccionados se han generado correctamente.");
                VentanasUtils.cerrarVentana(chkOficioAsignacion);
            }
        } catch (Exception e) {
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_DESCARGA_ARCHIVO_FALLIDA);
        }
    }
}
