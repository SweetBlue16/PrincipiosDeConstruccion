package practicasprofesionaleslis.controlador.profesoree;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import practicasprofesionaleslis.modelo.dao.EntregaDocumentoFinalDAO;
import practicasprofesionaleslis.modelo.dao.EntregaDocumentoInicialDAO;
import practicasprofesionaleslis.modelo.dao.EntregaDocumentoIntermedioDAO;
import practicasprofesionaleslis.modelo.dao.EntregaReporteDAO;

import practicasprofesionaleslis.modelo.pojo.Estudiante;
import practicasprofesionaleslis.modelo.pojo.Expediente;
import practicasprofesionaleslis.modelo.pojo.ExperienciaEducativa;
import practicasprofesionaleslis.modelo.pojo.Entrega;
import practicasprofesionaleslis.modelo.pojo.EntregaDocumentoFinal;
import practicasprofesionaleslis.modelo.pojo.EntregaDocumentoInicial;
import practicasprofesionaleslis.modelo.pojo.EntregaDocumentoIntermedio;
import practicasprofesionaleslis.modelo.pojo.EntregaReporte;
import practicasprofesionaleslis.utilidades.VentanasUtils;

/**
 * Autor: Yael A. Castillo
 * Fecha de creación: 09/06/2025
 * Descripción: Controla la ventana para consultar
 * los documentos entregados por parte del estudiante.
 */
public class FXMLDocumentosEntregadosController implements Initializable {

    private TableView<Entrega> tbldocumentosEntregados;
    
    private ObservableList<Entrega> entregas;
    private ExperienciaEducativa experienciaEducativa;
    private Estudiante estudiante;
    private Expediente expediente;
    @FXML
    private TableView<EntregaDocumentoInicial> tblDocumentosIniciales;
    @FXML
    private TableColumn<EntregaDocumentoInicial, String> colTipoDoctoInicial;
    @FXML
    private TableColumn<EntregaDocumentoInicial, LocalDate> colIniciaInicial;
    @FXML
    private TableColumn<EntregaDocumentoInicial, LocalDate> colTerminaInicial;
    @FXML
    private TableColumn<EntregaDocumentoInicial, Integer> colPuntajeInicial;
    
    @FXML
    private TableView<EntregaDocumentoIntermedio> tblDocumentosIntermedios;
    @FXML
    private TableColumn<EntregaDocumentoIntermedio, String> colTipoDoctoIntermedio;
    @FXML
    private TableColumn<EntregaDocumentoIntermedio, LocalDate> colIniciaIntermedio;
    @FXML
    private TableColumn<EntregaDocumentoIntermedio, LocalDate> colTerminaIntermedio;
    @FXML
    private TableColumn<EntregaDocumentoIntermedio, Integer> colPuntajeIntermedio;
    
    @FXML
    private TableView<EntregaDocumentoFinal> tblDocumentosFinales;
    @FXML
    private TableColumn<EntregaDocumentoFinal, String> colTipoDoctoFinal;
    @FXML
    private TableColumn<EntregaDocumentoFinal, LocalDate> colIniciaFinal;
    @FXML
    private TableColumn<EntregaDocumentoFinal, LocalDate> colTerminaFinal;
    @FXML
    private TableColumn<EntregaDocumentoFinal, Integer> colPuntajeFinal;
    
    @FXML
    private TableView<EntregaReporte> tblReportes;
    @FXML
    private TableColumn<EntregaReporte, Integer> colReporte;
    @FXML
    private TableColumn<EntregaReporte, LocalDate> colIniciaReporte;
    @FXML
    private TableColumn<EntregaReporte, LocalDate> colTerminaReporte;
    @FXML
    private TableColumn<EntregaReporte, Integer> colPuntajeReporte;
    
    private ObservableList<EntregaDocumentoInicial> entregasIniciales;
    private ObservableList<EntregaDocumentoIntermedio> entregasIntermedias;
    private ObservableList<EntregaDocumentoFinal> entregasFinales;
    private ObservableList<EntregaReporte> reportes;
    @FXML
    private Label lblTitulo;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTablas();
    }
    

    
    private void configurarTablas() {
        colTipoDoctoInicial.setCellValueFactory(new PropertyValueFactory<>("tipoDocumentoInicial"));
        colIniciaInicial.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        colTerminaInicial.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
        colPuntajeInicial.setCellValueFactory(new PropertyValueFactory<>("puntaje"));

        colTipoDoctoIntermedio.setCellValueFactory(new PropertyValueFactory<>("tipoDocumentoIntermedio"));
        colIniciaIntermedio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        colTerminaIntermedio.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
        colPuntajeIntermedio.setCellValueFactory(new PropertyValueFactory<>("puntaje"));

        colTipoDoctoFinal.setCellValueFactory(new PropertyValueFactory<>("tipoDocumentoFinal"));
        colIniciaFinal.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        colTerminaFinal.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
        colPuntajeFinal.setCellValueFactory(new PropertyValueFactory<>("puntaje"));

        colReporte.setCellValueFactory(new PropertyValueFactory<>("numeroReporte"));
        colIniciaReporte.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        colTerminaReporte.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
        colPuntajeReporte.setCellValueFactory(new PropertyValueFactory<>("puntaje"));

        entregasIniciales = FXCollections.observableArrayList();
        entregasIntermedias = FXCollections.observableArrayList();
        entregasFinales = FXCollections.observableArrayList();
        reportes = FXCollections.observableArrayList();

        tblDocumentosIniciales.setItems(entregasIniciales);
        tblDocumentosIntermedios.setItems(entregasIntermedias);
        tblDocumentosFinales.setItems(entregasFinales);
        tblReportes.setItems(reportes);
    }
    
    public void inicializarDatos(ExperienciaEducativa experienciaEducativa, Estudiante estudiante, Expediente expediente) {
        this.experienciaEducativa = experienciaEducativa;
        this.estudiante = estudiante;
        this.expediente = expediente;
        
        cargarEntregas();
    }
    
    private void cargarEntregas() {
        try {
            List<EntregaDocumentoInicial> entregasIni = EntregaDocumentoInicialDAO
                .obtenerEntregasPorExperienciaEducativa(experienciaEducativa.getId());
            entregasIniciales.addAll(entregasIni);

            List<EntregaDocumentoIntermedio> entregasInter = EntregaDocumentoIntermedioDAO
                .obtenerEntregasPorExperienciaEducativa(experienciaEducativa.getId());
            entregasIntermedias.addAll(entregasInter);

            List<EntregaDocumentoFinal> entregasFin = EntregaDocumentoFinalDAO
                .obtenerEntregasPorExperienciaEducativa(experienciaEducativa.getId());
            entregasFinales.addAll(entregasFin);

            List<EntregaReporte> entregasRep = EntregaReporteDAO
                .obtenerEntregasPorExperienciaEducativa(experienciaEducativa.getId());
            reportes.addAll(entregasRep);

        } catch (SQLException e) {
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                "Error al cargar entregas",
                "No se pudieron cargar las entregas: " + e.getMessage());
        }
    }
    


    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(lblTitulo);
    }

    @FXML
    private void clicBtnSeleccionar(ActionEvent event) throws SQLException {
        EntregaDocumentoInicial entregaInicial = tblDocumentosIniciales.getSelectionModel().getSelectedItem();
        EntregaDocumentoIntermedio entregaIntermedia = tblDocumentosIntermedios.getSelectionModel().getSelectedItem();
        EntregaDocumentoFinal entregaFinal = tblDocumentosFinales.getSelectionModel().getSelectedItem();
        EntregaReporte entregaReporte = tblReportes.getSelectionModel().getSelectedItem();

        Entrega entregaSeleccionada = null;
        String tipoDocumento = null;

        if (entregaInicial != null) {
            entregaSeleccionada = entregaInicial;
            tipoDocumento = "INICIAL";
        } else if (entregaIntermedia != null) {
            entregaSeleccionada = entregaIntermedia;
            tipoDocumento = "INTERMEDIO";
        } else if (entregaFinal != null) {
            entregaSeleccionada = entregaFinal;
            tipoDocumento = "FINAL";
        } else if (entregaReporte != null) {
            entregaSeleccionada = entregaReporte;
            tipoDocumento = "REPORTE";
        }

        if (entregaSeleccionada != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/practicasprofesionaleslis/vista/profesoree/FXMLEvaluarDocumento.fxml"));
                Parent vista = loader.load();

                            // Create the stage first
                Stage stage = new Stage();
                stage.setScene(new Scene(vista));
                stage.setResizable(false);

                FXMLEvaluarDocumentoController controller = loader.getController();
                controller.inicializarDatos(
                    experienciaEducativa, 
                    estudiante, 
                    expediente, 
                    entregaInicial,
                    entregaIntermedia,
                    entregaFinal,
                    entregaReporte,
                    stage

                );

                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    "Error",
                    "No se pudo abrir la ventana de evaluación: " + e.getMessage());
            }
        } else {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                "Selección requerida",
                "Por favor seleccione un documento de las tablas.");
        }
    }
}