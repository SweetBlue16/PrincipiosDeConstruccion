package practicasprofesionaleslis.controlador.coordinador;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import practicasprofesionaleslis.modelo.dao.ExpedienteDAO;
import practicasprofesionaleslis.modelo.dao.ProyectoDAO;
import practicasprofesionaleslis.modelo.pojo.Estudiante;
import practicasprofesionaleslis.modelo.pojo.Proyecto;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

/**
 * Autor: Abraham Cano
 * Fecha de creación: 09/06/2025
 * Descripción: Controla la ventana para
 * asignar un proyecto disponible a un estudiante.
 */
public class FXMLAsignacionProyectoController implements Initializable {

    @FXML
    private Label lbNombreEstudiante;
    @FXML
    private Label lbMatriculaEst;
    @FXML
    private Label lbCorreoInst;
    @FXML
    private Label lbSemestre;
    @FXML
    private TableView<Proyecto> tvProyectosElegidos;
    @FXML
    private TableColumn colNombreProy;
    @FXML
    private TableColumn colNumIntegrantes;
    @FXML
    private TableColumn<Proyecto, String> colNombreOV;
    private Estudiante estudiante;
    ObservableList<Proyecto> proyectosDisponibles;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }    
    
    public void inicializarDatosEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
        cargarDatosEstudiante();
    }    
        
    private void cargarDatosEstudiante(){    
        if (estudiante != null) {
            lbNombreEstudiante.setText(estudiante.getNombre() + " " + estudiante.getApellidoPaterno() + " " + estudiante.getApellidoMaterno());
            lbMatriculaEst.setText(estudiante.getMatricula());
            lbCorreoInst.setText(estudiante.getCorreoInstitucional());
            lbSemestre.setText(String.valueOf(estudiante.getSemestre()));
            
            try {
                int idExpediente = ExpedienteDAO.obtenerIdExpedientePorIdEstudiante(estudiante.getId());
            } catch (SQLException e) {
                e.printStackTrace();
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_BD
                );        
            }
        }
    }
    
    private void configurarTabla(){
        colNombreProy.setCellValueFactory(new PropertyValueFactory("nombre"));
        colNumIntegrantes.setCellValueFactory(new PropertyValueFactory("numIntegrantes"));
        colNombreOV.setCellValueFactory(cellData -> 
            new SimpleStringProperty(
                cellData.getValue().getOrganizacionVinculada() != null ?
                cellData.getValue().getOrganizacionVinculada().getRazonSocial() : ""
            )
        );
    }
    
    private void cargarInformacionTabla(){
        try { 
            proyectosDisponibles = FXCollections.observableArrayList();
            List<Proyecto> proyectosDAO = ProyectoDAO.obtenerProyectosDisponibles();
            
            proyectosDisponibles.addAll(proyectosDAO);
            tvProyectosElegidos.setItems(proyectosDisponibles);
        } catch (SQLException e){
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_BD
            );
        }    
    }


    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        Proyecto proyectoSeleccionado = tvProyectosElegidos.getSelectionModel().getSelectedItem();
        if (proyectoSeleccionado == null) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING, 
                ConstantesUtils.TITULO_ADVERTENCIA, 
                "Debes seleccionar un proyecto para asignar.");
            return;
        }

        try {
            // Para obtener el id del expediente del estudiante al que se insertará el nuevo proyecto
            int idExpediente = ExpedienteDAO.obtenerIdExpedientePorIdEstudiante(estudiante.getId());
            if (idExpediente == -1) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    "No se encontró expediente para el estudiante.");
                return;
            }

            boolean resultado = ExpedienteDAO.asignarProyectoAExpediente(idExpediente, proyectoSeleccionado.getId());
            if (resultado) {
                tvProyectosElegidos.getItems().clear();
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
                    "GUARDADO CORRECTAMENTE", 
                    "Proyecto asignado y expediente del estudiante creado correctamente.");
                VentanasUtils.cerrarVentana(tvProyectosElegidos);
            } else {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                    ConstantesUtils.TITULO_ERROR, 
                    "No se pudo asignar el proyecto.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                ConstantesUtils.TITULO_ERROR, 
                ConstantesUtils.ALERTA_ERROR_BD);
        }
    }
    
    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(tvProyectosElegidos);
    }
    
}
