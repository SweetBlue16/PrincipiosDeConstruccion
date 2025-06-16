package practicasprofesionaleslis.controlador.profesoree;

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
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import practicasprofesionaleslis.PracticasProfesionalesLIS;
import practicasprofesionaleslis.modelo.dao.EstudianteDAO;
import practicasprofesionaleslis.modelo.pojo.Estudiante;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.ValidacionUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLBusquedaEstudianteController implements Initializable {

    @FXML
    private TextField txtfMatricula;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}    

    @FXML
    private void clicBtnBuscar(ActionEvent event) {
        String matricula = txtfMatricula.getText().trim();
        if (!matricula.isEmpty() && ValidacionUtils.validarIDEstudiante(matricula)) {
            try {
                Estudiante estudiante = EstudianteDAO.obtenerEstudiantePorMatricula(matricula);
                irSeleccionEstudiante(estudiante);
            } catch (SQLException e) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                        ConstantesUtils.TITULO_ERROR,
                        e.getMessage()
                );
            }
        } else {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ADVERTENCIA,
                    ConstantesUtils.ALERTA_DATOS_INVALIDOS
            );
        }
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(txtfMatricula);
    }
    
    private void irSeleccionEstudiante(Estudiante estudiante) {
        try {
            String rutaRecurso = "/practicasprofesionaleslis/vista/profesoree/FXMLEstudiantesInscritos.fxml";
            FXMLLoader cargador = new FXMLLoader(PracticasProfesionalesLIS.class.getResource(rutaRecurso));
            Parent vista = cargador.load();
            FXMLEstudiantesInscritosController controlador = cargador.getController();
            controlador.inicializarEstudiante(estudiante);
            
            Stage escenarioBase = VentanasUtils.obtenerEscenarioComponente(txtfMatricula);
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
    
}
