package practicasprofesionaleslis.controlador.estudiante;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import practicasprofesionaleslis.PracticasProfesionalesLIS;
import practicasprofesionaleslis.modelo.dao.ExpedienteDAO;
import practicasprofesionaleslis.modelo.pojo.Estudiante;
import practicasprofesionaleslis.modelo.pojo.Expediente;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLMiExpedienteController implements Initializable {
    private Estudiante estudiante;
    private Expediente expediente;

    @FXML
    private Label lblEstadoExpediente;
    @FXML
    private Label lblHorasAcumuladas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}
    
    public void inicializarEstudiante(Estudiante estudiante) {
        if (estudiante != null) {
            this.estudiante = estudiante;
            cargarExpediente();
        }
    }

    @FXML
    private void clicBtnSubirDocumentos(ActionEvent event) {
        try {
            String rutaRecurso = "/practicasprofesionaleslis/vista/estudiante/FXMLSubirDocumentos.fxml";
            FXMLLoader cargador = new FXMLLoader(PracticasProfesionalesLIS.class.getResource(rutaRecurso));
            Parent vista = cargador.load();
            FXMLSubirDocumentosController controlador = cargador.getController();
            controlador.inicializarExpediente(expediente);
            
            Stage escenarioBase = new Stage();
            Scene escenaSubirDocumentos = new Scene(vista);
            escenarioBase.setScene(escenaSubirDocumentos);
            escenarioBase.setTitle(ConstantesUtils.TITULO_PERFIL);
            escenarioBase.initModality(Modality.APPLICATION_MODAL);
            escenarioBase.show();
            escenarioBase.setResizable(false);
            escenarioBase.centerOnScreen();
        } catch (IOException e) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_CARGAR_VENTANA
            );
        }
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(lblEstadoExpediente);
    }
    
    private void cargarExpediente() {
        try {
            expediente = ExpedienteDAO.obtenerExpedientePorEstudiante(estudiante.getId());
            if (expediente != null) {
                lblEstadoExpediente.setText("Estado del Expediente: " + expediente.getEstado());
                lblHorasAcumuladas.setText("Horas Acumuladas: " + expediente.getHorasAcumuladas() + " hrs");
            } else {
                lblEstadoExpediente.setText("Sin Expediente");
                lblHorasAcumuladas.setText("Horas Acumuladas: 0 hrs");
            }
        } catch (SQLException e) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_BD
            );
        }
    }
}
