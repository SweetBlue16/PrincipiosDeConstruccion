package practicasprofesionaleslis.controlador.profesoree;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import practicasprofesionaleslis.modelo.dao.EstudianteDAO;
import practicasprofesionaleslis.modelo.dao.ExpedienteDAO;
import practicasprofesionaleslis.modelo.pojo.Estudiante;
import practicasprofesionaleslis.modelo.pojo.Expediente;
import practicasprofesionaleslis.modelo.pojo.ExperienciaEducativa;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLBuscarEstudianteController implements Initializable {

    private AnchorPane apBuscarEstudiante;
    @FXML
    private TableView<Estudiante> tvEstudiantes;
    private ObservableList<Estudiante> estudiantes;
    private ExperienciaEducativa experienciaEducativa;
    
    @FXML
    private Label lblEstudiantes;
    @FXML
    private Label lbltitulo;
    @FXML
    private TableColumn<Estudiante, String> colNombre;
    @FXML
    private TableColumn<Estudiante, String> colApellidoPaterno;
    @FXML
    private TableColumn<Estudiante, String> colApellidoMaterno;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }    

    public void inicializarDatos(ExperienciaEducativa experienciaEducativa) {
        this.experienciaEducativa = experienciaEducativa;
        if (experienciaEducativa != null) {
            cargarEstudiantes();
        }
    }

    private void configurarTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidoPaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
        colApellidoMaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));
    }
    
    private void cargarEstudiantes() {
        try {
            estudiantes = FXCollections.observableArrayList();
            List<Estudiante> estudiantesDAO = EstudianteDAO.obtenerEstudiantesPorExperienciaEducativa(experienciaEducativa.getId());
            estudiantes.addAll(estudiantesDAO);
            tvEstudiantes.setItems(estudiantes);
        } catch (SQLException e) {
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_BD
            );
        }
    }
    
    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(lbltitulo);
    }

    @FXML
    private void clicBtnSeleccionar(ActionEvent event) {
        Estudiante estudianteSeleccionado = tvEstudiantes.getSelectionModel().getSelectedItem();
        if (estudianteSeleccionado != null) {
            try {
                // Get the expediente for the selected student
                Expediente expediente = ExpedienteDAO.obtenerExpedienteActivoPorEstudiante(estudianteSeleccionado.getId());

                if (expediente != null) {
                    // Load the DocumentosEntregados view
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "practicasprofesionaleslis/vista/profesoree/FXMLDocumentosEntregados.fxml"));
                            Parent root = loader.load();

                    // Get the controller and initialize data
                    FXMLDocumentosEntregadosController controller = loader.getController();
                    controller.inicializarDatos(experienciaEducativa, estudianteSeleccionado, expediente);

                    // Show the new window
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } else {
                    VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                        "No hay expediente",
                        "El estudiante seleccionado no tiene un expediente activo");
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    "Error",
                    "No se pudo cargar la información del estudiante");
            }
        } else {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                "Selección requerida",
                "Debe seleccionar un estudiante de la tabla");
        }
    }
}