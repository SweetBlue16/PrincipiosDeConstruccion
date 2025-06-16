package practicasprofesionaleslis.controlador.coordinador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import practicasprofesionaleslis.modelo.dao.EstudianteDAO;
import practicasprofesionaleslis.modelo.dao.ProyectoDAO;
import practicasprofesionaleslis.modelo.pojo.Estudiante;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLBuscarEstudianteSinProyectoController implements Initializable {

    @FXML
    private TableView<Estudiante> tvEstudiantesSinProyecto;
    @FXML
    private TableColumn colMatricula;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApPaterno;
    @FXML
    private TableColumn colApMaterno;
    
    private ObservableList<Estudiante> estudiantes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        try {
            if (!validarPrecondiciones()) {
                VentanasUtils.cerrarVentana(tvEstudiantesSinProyecto);
                return;
            }
            cargarInformacionTabla();
        } catch (SQLException e) {
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_BD
            );        
        }
    }    
    
    private boolean validarPrecondiciones() throws SQLException {
        if (!ProyectoDAO.verificarProyectosDisponibles()) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ADVERTENCIA,
                    "No hay proyectos con cupo disponible en este momento.");
            return false;
        }
        return true;
    }
    
    private void configurarTabla(){
        colMatricula.setCellValueFactory(new PropertyValueFactory("matricula"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
    }
    
    private void cargarInformacionTabla(){
          
        try { 
            estudiantes = FXCollections.observableArrayList();
            List<Estudiante> estudiantesDAO = EstudianteDAO.obtenerEstudiantesSinProyecto();
            
            
            if (estudiantesDAO.isEmpty()) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ADVERTENCIA,
                    "No hay estudiantes sin proyecto asignado en este momento.");
                VentanasUtils.cerrarVentana(tvEstudiantesSinProyecto);
                return;
            }
            estudiantes.addAll(estudiantesDAO);
            tvEstudiantesSinProyecto.setItems(estudiantes);
        } catch (SQLException e){
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_BD
            );
        }    
    }

    private void irAsignacionProyecto(Estudiante estudiante){
        try {
            Stage escenarioCalificarPresentacion = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/practicasprofesionaleslis/vista/coordinador/FXMLAsignacionProyecto.fxml"));
            Parent vista = loader.load();
            
            FXMLAsignacionProyectoController controller = loader.getController();
            controller.inicializarDatosEstudiante(estudiante);
            
            Scene escena = new Scene(vista);
            escenarioCalificarPresentacion.setScene(escena);
            escenarioCalificarPresentacion.setTitle("ASIGNACIÓN DE PROYECTO");
            escenarioCalificarPresentacion.initModality(Modality.APPLICATION_MODAL);
            escenarioCalificarPresentacion.showAndWait();
            escenarioCalificarPresentacion.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_BD
            );
        }
    }

    @FXML
    private void clicBtnAceptar(ActionEvent event) {
        Estudiante estudianteSeleccionado = tvEstudiantesSinProyecto.getSelectionModel().getSelectedItem();

        if (estudianteSeleccionado == null) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ADVERTENCIA,
                    "Por favor, selecciona un estudiante de la tabla.");
            return;
        }    
        
        irAsignacionProyecto(estudianteSeleccionado);
    }
    
    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(tvEstudiantesSinProyecto);
    }
    
}
