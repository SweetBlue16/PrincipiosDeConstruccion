package practicasprofesionaleslis.controlador.profesoree;

import java.io.IOException;
import java.net.URL;
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
import practicasprofesionaleslis.controlador.FXMLAvancePracticasProfesionalesController;
import practicasprofesionaleslis.modelo.pojo.Estudiante;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

/**
 * Autor: Mauricio Noriega
 * Fecha de creación: 09/06/2025
 * Descripción: Controla la ventana para seleccionar el estudiante
 * recuperado y poder consultar su expediente.
 */
public class FXMLEstudiantesInscritosController implements Initializable {
    private Estudiante estudiante;

    @FXML
    private Label lblNombreEstudiante;
    @FXML
    private Label lblCorreoInstitucional;
    @FXML
    private Label lblMatricula;
    @FXML
    private Label lblSemestre;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}
    
    public void inicializarEstudiante(Estudiante estudiante) {
        if (estudiante != null) {
            this.estudiante = estudiante;
            lblNombreEstudiante.setText(estudiante.toString());
            lblCorreoInstitucional.setText(estudiante.getCorreoInstitucional());
            lblMatricula.setText(estudiante.getMatricula());
            lblSemestre.setText("Semestre: " + estudiante.getSemestre());
        }
    }

    @FXML
    private void clicBtnConsultarExpediente(ActionEvent event) {
        try {
            String rutaRecurso = "/practicasprofesionaleslis/vista/FXMLAvancePracticasProfesionales.fxml";
            FXMLLoader cargador = new FXMLLoader(PracticasProfesionalesLIS.class.getResource(rutaRecurso));
            Parent vista = cargador.load();
            FXMLAvancePracticasProfesionalesController controlador = cargador.getController();
            controlador.inicializarEstudianteYExpediente(estudiante);
            
            Stage escenarioBase = new Stage();
            Scene escenaExpediente = new Scene(vista);
            escenarioBase.setScene(escenaExpediente);
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
        VentanasUtils.cerrarVentana(lblSemestre);
    }
    
}
