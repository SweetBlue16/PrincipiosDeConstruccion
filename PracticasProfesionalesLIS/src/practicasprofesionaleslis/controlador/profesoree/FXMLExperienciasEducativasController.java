package practicasprofesionaleslis.controlador.profesoree;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import practicasprofesionaleslis.modelo.dao.ExperienciaEducativaDAO;
import practicasprofesionaleslis.modelo.pojo.ExperienciaEducativa;
import practicasprofesionaleslis.modelo.pojo.ProfesorEE;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLExperienciasEducativasController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private TableView<ExperienciaEducativa> tableViewExperiencias;
    @FXML
    private TableColumn<ExperienciaEducativa, String> colNombre;
    @FXML
    private TableColumn<ExperienciaEducativa, String> colBloque;
    @FXML
    private TableColumn<ExperienciaEducativa, String> colSeccion;
    @FXML
    private TableColumn<ExperienciaEducativa, Integer> colNrc;
    
    private ProfesorEE profesorEE;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }
    
    private void configurarTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colBloque.setCellValueFactory(new PropertyValueFactory<>("bloque"));
        colSeccion.setCellValueFactory(new PropertyValueFactory<>("seccion"));
        colNrc.setCellValueFactory(new PropertyValueFactory<>("nrc"));
    }
    
    public void inicializarDatos(ProfesorEE profesorEE) {
        this.profesorEE = profesorEE;
        if (profesorEE != null) {
            cargarExperienciasEducativas();
        }
    }
    
    private void cargarExperienciasEducativas() {
        try {
            List<ExperienciaEducativa> experiencias = 
                ExperienciaEducativaDAO.obtenerExperienciasPorProfesorEE(profesorEE.getId());
            tableViewExperiencias.setItems(FXCollections.observableArrayList(experiencias));
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarErrorBD();
        }
    }
    
    private void mostrarErrorBD() {
        VentanasUtils.mostrarAlertaSimple(
            Alert.AlertType.ERROR,
            ConstantesUtils.TITULO_ERROR,
            ConstantesUtils.ALERTA_ERROR_BD
        );
    }

private void clicBtnSeleccionar(ActionEvent event) {
    ExperienciaEducativa experienciaSeleccionada = tableViewExperiencias.getSelectionModel().getSelectedItem();
    
    if (experienciaSeleccionada == null) {
        VentanasUtils.mostrarAlertaSimple(
            Alert.AlertType.WARNING,
            "Selección requerida",
            "Seleccione una experiencia educativa"
        );
        return;
    }

    try {
        // 1. Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
            "/practicasprofesionaleslis/vista/profesoree/FXMLBuscarEstudiante.fxml"));
        
        // 2. Load the root node (the main container from the FXML)
        Parent root = loader.load();
        
        // 3. Get the controller instance
        FXMLBuscarEstudianteController buscarEstudianteController = loader.getController();
        
        // 4. Initialize the controller with the selected experience
        buscarEstudianteController.inicializarDatos(experienciaSeleccionada);
        
        // 5. Create the new stage (window)
        Stage stage = new Stage();
        stage.setTitle("Estudiantes de " + experienciaSeleccionada.getNombre());
        stage.setScene(new Scene(root));
        
        // 6. Set modality if you want it to block the parent window
        stage.initModality(Modality.APPLICATION_MODAL);
        
        // 7. Show the window
        stage.showAndWait(); // or stage.show() if you don't want to wait
        
    } catch (IOException e) {
        e.printStackTrace();
        VentanasUtils.mostrarAlertaSimple(
            Alert.AlertType.ERROR,
            "Error",
            "No se pudo abrir la ventana de estudiantes: " + e.getMessage()
        );
    }
}

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(lblTitulo);
    }

    @FXML
    private void btnSeleccionar(ActionEvent event) {
    }
}