package practicasprofesionaleslis.controlador.profesoree;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
//import practicasprofesionaleslis.modelo.dao.EntregaDAO;
import practicasprofesionaleslis.modelo.dao.EntregaDocumentoFinalDAO;
import practicasprofesionaleslis.modelo.dao.EntregaDocumentoInicialDAO;
import practicasprofesionaleslis.modelo.dao.EntregaDocumentoIntermedioDAO;
import practicasprofesionaleslis.modelo.dao.EntregaReporteDAO;


import practicasprofesionaleslis.modelo.dao.ExpedienteDAO;
import practicasprofesionaleslis.modelo.pojo.Estudiante;
import practicasprofesionaleslis.modelo.pojo.Expediente;
import practicasprofesionaleslis.modelo.pojo.ExperienciaEducativa;
import practicasprofesionaleslis.modelo.pojo.Entrega;
import practicasprofesionaleslis.modelo.pojo.EntregaDocumentoFinal;
import practicasprofesionaleslis.modelo.pojo.EntregaDocumentoInicial;
import practicasprofesionaleslis.modelo.pojo.EntregaDocumentoIntermedio;
import practicasprofesionaleslis.modelo.pojo.EntregaReporte;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLDocumentosEntregadosController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private TableView<Entrega> tbldocumentosEntregados;
    @FXML
    private TableColumn<Entrega, String> colNombre;
    @FXML
    private TableColumn<Entrega, String> colInicia;
    @FXML
    private TableColumn<Entrega, String> colTermina;
    @FXML
    private TableColumn<Entrega, String> colPuntaje;
    
    private ObservableList<Entrega> entregas;
    private ExperienciaEducativa experienciaEducativa;
    private Estudiante estudiante;
    private Expediente expediente;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }
    
    public void inicializarDatos(ExperienciaEducativa experienciaEducativa, Estudiante estudiante, Expediente expediente) {
        this.experienciaEducativa = experienciaEducativa;
        this.estudiante = estudiante;
        this.expediente = expediente;
        
        cargarEntregas();
    }
    
    private void configurarTabla() {
       // colNombre.setCellValueFactory(cellData -> cellData.getValue().getNombreProperty());
    //    colInicia.setCellValueFactory(cellData -> cellData.getValue().getFechaInicioProperty().asString());
      //  colTermina.setCellValueFactory(cellData -> cellData.getValue().getFechaFinProperty().asString());
    //    colPuntaje.setCellValueFactory(cellData -> cellData.getValue().getPuntajeProperty().asString());
        
        entregas = FXCollections.observableArrayList();
        tbldocumentosEntregados.setItems(entregas);
    }
    
    private void cargarEntregas() {
        try {
            List<Entrega> todasLasEntregas = new ArrayList<>();
            
            // Load all types of entregas
            todasLasEntregas.addAll(EntregaDocumentoFinalDAO.obtenerEntregasPorExperienciaEducativa(experienciaEducativa.getId()));
            todasLasEntregas.addAll(EntregaDocumentoInicialDAO.obtenerEntregasPorExperienciaEducativa(experienciaEducativa.getId()));
            todasLasEntregas.addAll(EntregaDocumentoIntermedioDAO.obtenerEntregasPorExperienciaEducativa(experienciaEducativa.getId()));
            todasLasEntregas.addAll(EntregaReporteDAO.obtenerEntregasPorExperienciaEducativa(experienciaEducativa.getId()));
            
            entregas.addAll(todasLasEntregas);
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
        VentanasUtils.cerrarVentana(lblTitulo);
    }
}