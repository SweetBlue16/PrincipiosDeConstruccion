package practicasprofesionaleslis.controlador.coordinador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import practicasprofesionaleslis.modelo.dao.EstudianteDAO;
import practicasprofesionaleslis.modelo.dao.ExpedienteDAO;
import practicasprofesionaleslis.modelo.pojo.Estudiante;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLBuscarEstudianteController implements Initializable {

    @FXML
    private TextField txtfMatriculaEstudiante;
    @FXML
    private Label lbMensajeError;

    private Estudiante estudianteEncontrado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configuración inicial si es necesaria
    }

    @FXML
    private void validarEstudiantePorTeclado(ActionEvent event) {
        validarEstudiante();
    }

    private void validarEstudiante() {
        String matricula = txtfMatriculaEstudiante.getText().trim();
        if (matricula.isEmpty()) {
            mostrarMensajeError("Ingresa la matrícula del estudiante.");
            return;
        }

        try {
            estudianteEncontrado = EstudianteDAO.obtenerEstudiantePorMatricula(matricula);
            if (estudianteEncontrado == null) {
                mostrarMensajeError("Estudiante no encontrado.");
            } else {
                limpiarMensajeError();
            }
        } catch (SQLException e) {
            mostrarErrorBD();
        }
    }

    private void mostrarMensajeError(String mensaje) {
        lbMensajeError.setText(mensaje);
    }

    private void limpiarMensajeError() {
        lbMensajeError.setText("");
    }

    private void mostrarErrorBD() {
        VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                ConstantesUtils.TITULO_ERROR,
                ConstantesUtils.ALERTA_ERROR_BD);
    }

    private void abrirVentanaDocumentos(Estudiante estudiante) {
        try {
            Stage stageActual = (Stage) txtfMatriculaEstudiante.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/practicasprofesionaleslis/vista/coordinador/FXMLGenerarDocumentosAsignacion.fxml"));
            Parent vista = loader.load();

            FXMLGenerarDocumentosAsignacionController controller = loader.getController();
            controller.inicializarDatosEstudiante(estudiante, true);

            Stage escenarioDocumentos = new Stage();
            escenarioDocumentos.setScene(new Scene(vista));
            escenarioDocumentos.setTitle("GENERAR DOCUMENTOS DE ASIGNACIÓN");

            escenarioDocumentos.initModality(Modality.WINDOW_MODAL);
            escenarioDocumentos.initOwner(stageActual);

            escenarioDocumentos.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Error al cargar la ventana de documentos.");
        }
    }

    private boolean confirmarCreacionExpediente() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Crear Expediente");
        alert.setHeaderText("El estudiante no tiene expediente");
        alert.setContentText("¿Desea crear un nuevo expediente para este estudiante?");

        Optional<ButtonType> resultado = alert.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

    @FXML
    private void clicBtnBuscar(ActionEvent event) {
        validarEstudiante();

        if (estudianteEncontrado != null) {
            manejarEstudianteEncontrado();
        }
    }

    private void manejarEstudianteEncontrado() {
        try {
            if (!EstudianteDAO.tieneExpediente(estudianteEncontrado.getId())) {
                if (!confirmarCreacionExpediente()) {
                    return;
                }
                if (!ExpedienteDAO.registrarExpedienteEstudiante(estudianteEncontrado.getId())) {
                    mostrarError("No se pudo crear el expediente del estudiante.");
                    return;
                }
            }
            abrirVentanaDocumentos(estudianteEncontrado);
            limpiarCampos();
        } catch (SQLException e) {
            mostrarErrorBD();
        }
    }

    private void mostrarError(String mensaje) {
        VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                ConstantesUtils.TITULO_ERROR,
                mensaje);
    }

    private void limpiarCampos() {
        txtfMatriculaEstudiante.clear();
        limpiarMensajeError();
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(txtfMatriculaEstudiante);
    }
}