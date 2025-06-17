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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import practicasprofesionaleslis.modelo.dao.EntregaReporteDAO;
import practicasprofesionaleslis.modelo.pojo.EntregaReporte;
import practicasprofesionaleslis.modelo.pojo.Reporte;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLAsignacionFechaController implements Initializable {

    @FXML
    private TableView<EntregaReporte> tblReportes;
    @FXML
    private Button btnClicAsignarFechaUno;
    @FXML
    private Button btnClicAsignarFechaDos;
    @FXML
    private Button btnClicAsignarFechaTres;
    @FXML
    private TableColumn<EntregaReporte, Integer> colNombre;
    @FXML
    private TableColumn<EntregaReporte, String> colFechaInicio;
    @FXML
    private TableColumn<EntregaReporte, String> colFechaTerminacion;
    @FXML
    private TableColumn<EntregaReporte, Integer> colValor;
    private ObservableList<EntregaReporte> entregas;
    @FXML
    private Button btnClicAsignarFechaCuatro;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
        configurarBotones();
    }    
    
    private void configurarTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("numeroReporte"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
        colFechaTerminacion.setCellValueFactory(new PropertyValueFactory("fechaFin"));
        colValor.setCellValueFactory(new PropertyValueFactory("puntaje"));
    }
    
    private void cargarInformacionTabla(){  
        try {
            List<EntregaReporte> listaEntregas = EntregaReporteDAO.obtenerTodasLasEntregasReporte();
            entregas = FXCollections.observableArrayList(listaEntregas);
            tblReportes.setItems(entregas);
        } catch (SQLException ex) {
            ex.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(
                Alert.AlertType.ERROR,
                ConstantesUtils.TITULO_ERROR,
                ConstantesUtils.ALERTA_ERROR_BD
            );
        }
    }
    
    private void configurarBotones() {
        btnClicAsignarFechaUno.setOnAction(e -> abrirVentanaModificacion(1));
        btnClicAsignarFechaDos.setOnAction(e -> abrirVentanaModificacion(2));
        btnClicAsignarFechaTres.setOnAction(e -> abrirVentanaModificacion(3));
        btnClicAsignarFechaCuatro.setOnAction(e -> abrirVentanaModificacion(4));
    }
    
    private void abrirVentanaModificacion(int numeroReporte) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/practicasprofesionaleslis/vista/coordinador/FXMLAsignacionFechaDos.fxml"));
            Parent root = loader.load();
            
            FXMLAsignacionFechaDosController controller = loader.getController();
            controller.inicializarDatos(numeroReporte);
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("ASIGNACIÓN DE FECHA");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
            cargarInformacionTabla();
        } catch (IOException ex) {
            ex.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(
                Alert.AlertType.ERROR,
                ConstantesUtils.TITULO_ERROR,
                ConstantesUtils.ALERTA_ERROR_CARGAR_VENTANA
            );
        }
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(tblReportes);
    }
    
}
