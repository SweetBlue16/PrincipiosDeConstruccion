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
import practicasprofesionaleslis.modelo.dao.ProyectoDAO;
import practicasprofesionaleslis.modelo.pojo.Estudiante;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLBuscarEstudianteController implements Initializable {

    private Estudiante estudianteEncontrado;

    @FXML
    private Label lblEstudiantes;
    @FXML
    private TextField txtfMatricula;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void clicBtnBuscar(ActionEvent event) {
        validarEstudiante();
        if (estudianteEncontrado != null) {
            manejarEstudianteEncontrado();
        }
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(txtfMatricula);
    }

    private void validarEstudiante() {
        String matricula = txtfMatricula.getText().trim();
        if (matricula.isEmpty()) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ADVERTENCIA, ConstantesUtils.ALERTA_DATOS_INVALIDOS);
            return;
        }

        try {
            estudianteEncontrado = EstudianteDAO.obtenerEstudiantePorMatricula(matricula);
            if (estudianteEncontrado == null) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                        ConstantesUtils.TITULO_ADVERTENCIA, ConstantesUtils.ALERTA_BUSQUEDA_FALLIDA);
            }
        } catch (SQLException e) {
            mostrarErrorBD();
        }
    }

    private void manejarEstudianteEncontrado() {
        try {
            if (!EstudianteDAO.tieneExpediente(estudianteEncontrado.getId())) {
                if (!confirmarCreacionExpediente()) return;

                if (!ExpedienteDAO.registrarExpedienteEstudiante(estudianteEncontrado.getId())) {
                    mostrarError("No se pudo crear el expediente del estudiante.");
                    return;
                }
            }
            abrirVentanaDocumentos(estudianteEncontrado);
        } catch (SQLException e) {
            mostrarErrorBD();
        }
    }

    private void abrirVentanaDocumentos(Estudiante estudiante) {
        try {
            Stage stageActual = VentanasUtils.obtenerEscenarioComponente(lblEstudiantes);

            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/practicasprofesionaleslis/vista/coordinador/FXMLGenerarDocumentosAsignacion.fxml"));
            Parent vista = loader.load();

            FXMLGenerarDocumentosAsignacionController controller = loader.getController();
            boolean tieneProyecto = EstudianteDAO.tieneExpediente(estudiante.getId());
            controller.inicializarDatosEstudiante(estudiante, tieneProyecto);

            Stage escenarioDocumentos = new Stage();
            escenarioDocumentos.setScene(new Scene(vista));
            escenarioDocumentos.setTitle("GENERAR DOCUMENTOS DE ASIGNACIÓN");
            escenarioDocumentos.initModality(Modality.WINDOW_MODAL);
            escenarioDocumentos.initOwner(stageActual);
            escenarioDocumentos.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR, ConstantesUtils.ALERTA_ERROR_CARGAR_VENTANA);
        } catch (SQLException e) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR, ConstantesUtils.ALERTA_ERROR_BD);
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

    private void mostrarErrorBD() {
        VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                ConstantesUtils.TITULO_ERROR,
                ConstantesUtils.ALERTA_ERROR_BD);
    }

    private void mostrarError(String mensaje) {
        VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                ConstantesUtils.TITULO_ERROR,
                mensaje);
    }
}