package practicasprofesionaleslis.controlador.evaluador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import practicasprofesionaleslis.modelo.dao.EstudianteDAO;
import practicasprofesionaleslis.modelo.dao.EvaluacionPresentacionDAO;
import practicasprofesionaleslis.modelo.pojo.Estudiante;
import practicasprofesionaleslis.modelo.pojo.EvaluacionPresentacion;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLSeleccionEstudianteController implements Initializable {

    @FXML
    private TableView<Estudiante> tvEstudiantes;
    @FXML
    private TableColumn colMatricula;
    @FXML
    private TableColumn colNombre;
    @FXML
    private ComboBox<EvaluacionPresentacion> cbNumeroPresentacion;
    @FXML
    private TableColumn colApPaterno;
    @FXML
    private TableColumn colApMaterno;
    
    private ObservableList<EvaluacionPresentacion> evaluaciones;
    private ObservableList<Estudiante> estudiantes;
    
    @Override
    public void initialize (URL url, ResourceBundle rb) {
        cargarPresentacionesPorNumero();
        configurarTabla();
    } 
    
   private void cargarPresentacionesPorNumero(){
        try {
            evaluaciones = FXCollections.observableArrayList();
            List<EvaluacionPresentacion> evaluacionesDAO = EvaluacionPresentacionDAO.obtenerNumeroEvaluaciones(); 
            evaluaciones.addAll(evaluacionesDAO);
            cbNumeroPresentacion.setItems(evaluaciones);
        } catch (SQLException e) {
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ERROR_BD,
                    ConstantesUtils.ALERTA_ERROR_BD
            );
        }
    }
   
    private void configurarTabla(){
        colMatricula.setCellValueFactory(new PropertyValueFactory("matricula"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
    }
    
    private void cargarInformacionTabla(){
        EvaluacionPresentacion seleccionada = cbNumeroPresentacion.getSelectionModel().getSelectedItem();
          
        try { 
            estudiantes = FXCollections.observableArrayList();
            List<Estudiante> estudiantesDAO = EstudianteDAO.obtenerEstudiantesSinPresentacion(seleccionada.getNumeroEvaluacion());
            estudiantes.addAll(estudiantesDAO);
            tvEstudiantes.setItems(estudiantes);
        } catch (SQLException e){
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ERROR_BD,
                    ConstantesUtils.ALERTA_ERROR_BD
            );
        }    
    }
        
    @FXML
    private void clicBtnEvaluacionSeleccionada(ActionEvent event) {
        cargarInformacionTabla();
    }

    @FXML
    private void clicBtnAceptar(ActionEvent event) {
        Estudiante estudiante = tvEstudiantes.getSelectionModel().getSelectedItem();
        if (estudiante != null) {
            irCalificarEstudiante(estudiante);
        } else {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ADVERTENCIA,
                    "Debe seleccionar un estudiante para continuar");
        }
    }
    
    private void irCalificarEstudiante(Estudiante estudiante){
        try {
            Stage escenarioCalificarPresentacion = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/practicasprofesionaleslis/vista/evaluador/FXMLCalificarPresentacion.fxml"));
            Parent vista = loader.load();
            
            FXMLCalificarPresentacionController controller = loader.getController();
            controller.inicializarDatosEstudiante(estudiante);
            EvaluacionPresentacion seleccionada = cbNumeroPresentacion.getSelectionModel().getSelectedItem();
            controller.setNumeroEvaluacion(seleccionada.getNumeroEvaluacion());
            
            Stage escenarioEvaluador = new Stage();
            Scene escena = new Scene(vista);
            
            escenarioCalificarPresentacion.setScene(escena);
            escenarioCalificarPresentacion.setTitle("CALIFICAR PRESENTACIÓN");
            escenarioCalificarPresentacion.initModality(Modality.APPLICATION_MODAL);
            escenarioCalificarPresentacion.showAndWait();
            escenarioCalificarPresentacion.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_BD
            );
        }
    }
    
    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(tvEstudiantes);
    }

}
