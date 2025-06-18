package practicasprofesionaleslis.controlador.coordinador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import practicasprofesionaleslis.modelo.dao.CoordinadorDAO;
import practicasprofesionaleslis.modelo.dao.ExperienciaEducativaDAO;
import practicasprofesionaleslis.modelo.pojo.Coordinador;
import practicasprofesionaleslis.modelo.pojo.ExperienciaEducativa;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

/**
 * Autor: Yael A. Castillo
 * Fecha de creación: 17/06/2025
 * Descripción: Controla la ventana para recuperar
 * las experiencias educativas.
 */
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
    
    private Coordinador coordinador;

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
    
    public void inicializarDatos(Coordinador coordinador) throws SQLException {
        this.coordinador = coordinador;
        this.coordinador = CoordinadorDAO.obtenerCoordinadorPorCorreo(coordinador.getCorreoInstitucional());
        cargarExperienciasEducativas();

    }
    
    private void cargarExperienciasEducativas() {
        try {
            List<ExperienciaEducativa> experiencias = 
                ExperienciaEducativaDAO.obtenerExperienciasPorCoordinador(coordinador.getId());
            tableViewExperiencias.getItems().clear(); 
            tableViewExperiencias.getItems().addAll(experiencias); 
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

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(lblTitulo);
    }

    @FXML
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/practicasprofesionaleslis/vista/coordinador/FXMLEntregas.fxml"));

            Parent root = loader.load();

            FXMLEntregasController entregasController = loader.getController();
            entregasController.inicializarDatos(coordinador, experienciaSeleccionada);

            Stage stage = new Stage();
            stage.setTitle("Programar entregas para " + experienciaSeleccionada.getNombre());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(
                Alert.AlertType.ERROR,
                "Error",
                "No se pudo abrir la ventana de programación de entregas: " + e.getMessage()
            );
        }
    }
}