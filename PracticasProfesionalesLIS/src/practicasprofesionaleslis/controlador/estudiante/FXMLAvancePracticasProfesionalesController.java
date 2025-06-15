package practicasprofesionaleslis.controlador.estudiante;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import practicasprofesionaleslis.modelo.dao.ExpedienteDAO;
import practicasprofesionaleslis.modelo.pojo.DocumentoFinal;
import practicasprofesionaleslis.modelo.pojo.DocumentoInicial;
import practicasprofesionaleslis.modelo.pojo.DocumentoIntermedio;
import practicasprofesionaleslis.modelo.pojo.Estudiante;
import practicasprofesionaleslis.modelo.pojo.EvaluacionPresentacion;
import practicasprofesionaleslis.modelo.pojo.Expediente;
import practicasprofesionaleslis.modelo.pojo.Reporte;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLAvancePracticasProfesionalesController implements Initializable {
    private Estudiante estudiante;
    private Expediente expediente;

    @FXML
    private Label lblNombreEstudiante;
    @FXML
    private Label lblSemestre;
    @FXML
    private Label lblMatricula;
    @FXML
    private Label lblEstadoExpediente;
    @FXML
    private Label lblHorasAcumuladas;
    
    @FXML
    private TableView<DocumentoInicial> tblDocumentosIniciales;
    @FXML
    private TableColumn<DocumentoInicial, String> colTipoDoctoInicial;
    @FXML
    private TableColumn<DocumentoInicial, Void> colBtnDoctoInicial;
    
    @FXML
    private TableView<DocumentoIntermedio> tblDocumentosIntermedios;
    @FXML
    private TableColumn<DocumentoIntermedio, String> colTipoDoctoIntermedio;
    @FXML
    private TableColumn<DocumentoIntermedio, Void> colBtnDoctoIntermedio;
    
    @FXML
    private TableView<DocumentoFinal> tblDocumentosFinales;
    @FXML
    private TableColumn<DocumentoFinal, String> colTipoDoctoFinal;
    @FXML
    private TableColumn<DocumentoFinal, Void> colBtnDoctoFinal;
    
    @FXML
    private TableView<Reporte> tblReportes;
    @FXML
    private TableColumn<Reporte, Integer> colReporte;
    @FXML
    private TableColumn<Reporte, Void> colBtnReporte;
    
    @FXML
    private TableView<EvaluacionPresentacion> tblEvaluacionesPresentaciones;
    @FXML
    private TableColumn<EvaluacionPresentacion, Double> colCalificacionFinal;
    @FXML
    private TableColumn<EvaluacionPresentacion, Integer> colNumeroPresentacion;
    @FXML
    private TableColumn<EvaluacionPresentacion, Void> colBtnDetalle;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void inicializarEstudianteYExpediente(Estudiante estudiante) {
        if (estudiante != null) {
            this.estudiante = estudiante;
            try {
                expediente = ExpedienteDAO.obtenerExpedientePorEstudiante(estudiante.getId());
            } catch (SQLException e) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                        ConstantesUtils.TITULO_ERROR,
                        e.getMessage()
                );
            }
        }
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(lblSemestre);
    }
    
}
