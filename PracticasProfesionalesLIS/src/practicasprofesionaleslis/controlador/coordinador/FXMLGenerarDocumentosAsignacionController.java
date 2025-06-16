package practicasprofesionaleslis.controlador.coordinador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import practicasprofesionaleslis.modelo.pojo.Estudiante;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLGenerarDocumentosAsignacionController implements Initializable {

    @FXML
    private Label lbMensaje;
    @FXML
    private CheckBox chkHorarioAlumno;
    @FXML
    private CheckBox chkDocOficioAsignacion;
    @FXML
    private CheckBox chkPlanTrabajo;
    @FXML
    private CheckBox chkCartaAsignacion;

    private Estudiante estudiante;
    private boolean tieneProyecto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization code if needed
    }

    public void inicializarDatosEstudiante(Estudiante estudiante, boolean tieneProyecto) {
        this.estudiante = estudiante;
        this.tieneProyecto = tieneProyecto;

        if (!tieneProyecto) {
            mostrarMensajeError("El estudiante no tiene proyecto asignado.");
            deshabilitarCheckboxes();
        } else {
            lbMensaje.setText("");
        }
    }

    private void deshabilitarCheckboxes() {
        chkHorarioAlumno.setDisable(true);
        chkDocOficioAsignacion.setDisable(true);
        chkPlanTrabajo.setDisable(true);
        chkCartaAsignacion.setDisable(true);
    }

    private void mostrarMensajeError(String mensaje) {
        lbMensaje.setText(mensaje);
    }

    private void mostrarMensajeExito(String mensaje) {
        lbMensaje.setText(mensaje);
    }

    @FXML
    private void clicBtnGenerar(ActionEvent event) {
        if (!tieneProyecto) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ADVERTENCIA,
                    "El estudiante no tiene proyecto asignado.");
            return;
        }

        if (!alMenosUnDocumentoSeleccionado()) {
            mostrarMensajeError("Seleccione al menos un documento.");
            return;
        }

        generarDocumentos();
    }

    private boolean alMenosUnDocumentoSeleccionado() {
        return chkCartaAsignacion.isSelected() ||
                chkDocOficioAsignacion.isSelected() ||
                chkHorarioAlumno.isSelected() ||
                chkPlanTrabajo.isSelected();
    }

    private void generarDocumentos() {
        try {
            // TODO: Implement actual document generation logic
            // using this.estudiante for needed data

            mostrarMensajeExito("Documentos generados exitosamente.");

            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                    "Documentos generados",
                    "Los documentos seleccionados se han generado correctamente.");
        } catch (Exception e) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    "Error al generar documentos: " + e.getMessage());
        }
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(lbMensaje);
    }
}
