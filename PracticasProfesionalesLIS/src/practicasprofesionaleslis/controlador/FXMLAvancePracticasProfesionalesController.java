package practicasprofesionaleslis.controlador;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import practicasprofesionaleslis.modelo.dao.DocumentoFinalDAO;
import practicasprofesionaleslis.modelo.dao.DocumentoInicialDAO;
import practicasprofesionaleslis.modelo.dao.DocumentoIntermedioDAO;
import practicasprofesionaleslis.modelo.dao.EvaluacionPresentacionDAO;
import practicasprofesionaleslis.modelo.dao.ExpedienteDAO;
import practicasprofesionaleslis.modelo.dao.ReporteDAO;
import practicasprofesionaleslis.modelo.pojo.DocumentoFinal;
import practicasprofesionaleslis.modelo.pojo.DocumentoInicial;
import practicasprofesionaleslis.modelo.pojo.DocumentoIntermedio;
import practicasprofesionaleslis.modelo.pojo.Estudiante;
import practicasprofesionaleslis.modelo.pojo.EvaluacionPresentacion;
import practicasprofesionaleslis.modelo.pojo.Expediente;
import practicasprofesionaleslis.modelo.pojo.Reporte;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

/**
 * Autor: Mauricio Noriega
 * Fecha de creación: 09/06/2025
 * Descripción: Controla la ventana para consultar los
 * avances de prácticas profesionales de un estudiante.
 */
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
        configurarColumnas();
    }
    
    public void inicializarEstudianteYExpediente(Estudiante estudiante) {
        if (estudiante != null) {
            this.estudiante = estudiante;
            try {
                expediente = ExpedienteDAO.obtenerExpedientePorEstudiante(estudiante.getId());
                mostrarDatosGenerales();
                cargarDocumentos();
                cargarEvaluaciones();
            } catch (SQLException e) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                        ConstantesUtils.TITULO_ERROR,
                        ConstantesUtils.ALERTA_ERROR_BD
                );
            }
        }
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(lblSemestre);
    }
    
    private void mostrarDatosGenerales() {
        lblNombreEstudiante.setText(estudiante.toString());
        lblMatricula.setText(estudiante.getMatricula());
        lblSemestre.setText(String.valueOf(estudiante.getSemestre()) + " semestre");
        lblEstadoExpediente.setText("Estado del expediente: " + expediente.getEstado().name());
        lblHorasAcumuladas.setText("Horas acumuladas: " + String.valueOf(expediente.getHorasAcumuladas()));
    }
    
    private void configurarColumnas() {
        colTipoDoctoInicial.setCellValueFactory(new PropertyValueFactory<>("tipoDocumentoInicial"));
        colTipoDoctoIntermedio.setCellValueFactory(new PropertyValueFactory<>("tipoDocumentoIntermedio"));
        colTipoDoctoFinal.setCellValueFactory(new PropertyValueFactory<>("tipoDocumentoFinal"));
        colReporte.setCellValueFactory(new PropertyValueFactory<>("numeroReporte"));
        colCalificacionFinal.setCellValueFactory(new PropertyValueFactory<>("calificacionFinal"));
        colNumeroPresentacion.setCellValueFactory(new PropertyValueFactory<>("numeroEvaluacion"));
    }
    
    private void cargarDocumentos() throws SQLException {
        cargarDocumentosIniciales();
        cargarDocumentosIntermedios();
        cargarDocumentosFinales();
        cargarReportes();
    }
    
    private void cargarDocumentosIniciales() throws SQLException {
        List<DocumentoInicial> documentoIniciales = DocumentoInicialDAO.obtenerDocumentosInicialesPorExpediente(expediente.getId());
        tblDocumentosIniciales.getItems().setAll(documentoIniciales);
        agregarBotonesPDF(tblDocumentosIniciales, colBtnDoctoInicial, (documento) -> documento.getArchivo() != null && documento.getArchivo().length > 0,
                documento -> VentanasUtils.abrirVentanaPDF(
                        new ByteArrayInputStream(documento.getArchivo()),
                        documento.getNombreArchivo()
        ));
    }
    
    private void cargarDocumentosIntermedios() throws SQLException {
        List<DocumentoIntermedio> documentosIntermedios = DocumentoIntermedioDAO.obtenerDocumentosIntermediosPorExpediente(expediente.getId());
        tblDocumentosIntermedios.getItems().setAll(documentosIntermedios);
        agregarBotonesPDF(tblDocumentosIntermedios, colBtnDoctoIntermedio, (documento) -> documento.getArchivo() != null && documento.getArchivo().length > 0,
                documento -> VentanasUtils.abrirVentanaPDF(
                        new ByteArrayInputStream(documento.getArchivo()),
                        documento.getNombreArchivo()
        ));
    }
    
    private void cargarDocumentosFinales() throws SQLException {
        List<DocumentoFinal> documentosFinales = DocumentoFinalDAO.obtenerDocumentosFinalesPorExpediente(expediente.getId());
        tblDocumentosFinales.getItems().setAll(documentosFinales);
        agregarBotonesPDF(tblDocumentosFinales, colBtnDoctoFinal, (documento) -> documento.getArchivo() != null && documento.getArchivo().length > 0,
                documento -> VentanasUtils.abrirVentanaPDF(
                        new ByteArrayInputStream(documento.getArchivo()),
                        documento.getNombreArchivo()
        ));
    }
    
    private void cargarReportes() throws SQLException {
        List<Reporte> reportes = ReporteDAO.obtenerReportesPorExpediente(expediente.getId());
        tblReportes.getItems().setAll(reportes);
        agregarBotonesPDF(tblReportes, colBtnReporte, (reporte) -> reporte.getArchivo() != null && reporte.getArchivo().length > 0,
                reporte -> VentanasUtils.abrirVentanaPDF(
                        new ByteArrayInputStream(reporte.getArchivo()),
                        reporte.getNombreArchivo()
        ));
    }
    
    private void cargarEvaluaciones() throws SQLException {
        List<EvaluacionPresentacion> evaluaciones = EvaluacionPresentacionDAO.obtenerEvaluacionesPorExpediente(expediente.getId());
        tblEvaluacionesPresentaciones.getItems().setAll(evaluaciones);
        agregarBotonesVer(tblEvaluacionesPresentaciones, colBtnDetalle,
                evaluacion -> true,
                evaluacion -> VentanasUtils.abrirVentanaDetallePresentacion(evaluacion)
        );
    }
    
    private <T> void agregarBotonesPDF(TableView<T> tabla, TableColumn<T, Void> columna,
                                       Predicate<T> habilitacion, Consumer<T> accion) {
        columna.setCellFactory(col -> new TableCell<T, Void>() {
            private final Button btn = new Button("PDF");
            {
                btn.setOnAction(e -> accion.accept(getTableView().getItems().get(getIndex())));
                btn.setStyle("-fx-background-color: #28AD56; -fx-text-fill: white; -fx-font-weight: bold;");
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                } else {
                    T elemento = getTableView().getItems().get(getIndex());
                    boolean habilitado = habilitacion.test(elemento);
                    btn.setDisable(!habilitado);
                    setGraphic(btn);
                    setAlignment(Pos.CENTER);
                }
            }
        });
    }
    
    private <T> void agregarBotonesVer(TableView<T> tabla, TableColumn<T, Void> columna,
                                       Predicate<T> habilitacion, Consumer<T> accion) {
        columna.setCellFactory(col -> new TableCell<T, Void>() {
            private final Button btn = new Button("Ver");
            {
                btn.setOnAction(e -> accion.accept(getTableView().getItems().get(getIndex())));
                btn.setStyle("-fx-background-color: #28AD56; -fx-text-fill: white; -fx-font-weight: bold;");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    btn.setDisable(!habilitacion.test(getTableView().getItems().get(getIndex())));
                    setGraphic(btn);
                    setAlignment(Pos.CENTER);
                }
            }
        });
    }
}
