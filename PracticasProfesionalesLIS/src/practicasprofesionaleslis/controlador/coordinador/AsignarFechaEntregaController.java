package practicasprofesionaleslis.controlador.coordinador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import practicasprofesionaleslis.modelo.dao.EntregaDocumentoFinalDAO;
import practicasprofesionaleslis.modelo.dao.EntregaDocumentoInicialDAO;
import practicasprofesionaleslis.modelo.dao.EntregaDocumentoIntermedioDAO;
import practicasprofesionaleslis.modelo.dao.EntregaReporteDAO;
import practicasprofesionaleslis.modelo.pojo.Coordinador;
import practicasprofesionaleslis.modelo.pojo.EntregaDocumentoFinal;
import practicasprofesionaleslis.modelo.pojo.EntregaDocumentoInicial;
import practicasprofesionaleslis.modelo.pojo.EntregaDocumentoIntermedio;
import practicasprofesionaleslis.modelo.pojo.EntregaReporte;
import practicasprofesionaleslis.modelo.pojo.ExperienciaEducativa;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class AsignarFechaEntregaController implements Initializable {

    @FXML 
    private Label lblNombreEntrega;
    @FXML
    private DatePicker dpInicia;
    @FXML
    private DatePicker dpTermina;
    
    private Coordinador coordinador;
    private ExperienciaEducativa experienciaEducativa;
    private EntregaDocumentoInicial entregaInicial;
    private EntregaDocumentoIntermedio entregaIntermedia;
    private EntregaDocumentoFinal entregaFinal;
    private EntregaReporte entregaReporte;
    private String tipoDocumento;
    
    private Stage stage;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @FXML
    private Label lblInicia;
    @FXML
    private Label lblValor;
    @FXML
    private Label lblTermina;
    @FXML
    private Label lblNombreEntregable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dpInicia.getEditor().setDisable(true);
        dpInicia.getEditor().setOpacity(1);
        dpTermina.getEditor().setDisable(true);
        dpTermina.getEditor().setOpacity(1);
    }
    
    public void inicializarDatos(Coordinador coordinador, 
                               ExperienciaEducativa experienciaEducativa,
                               EntregaDocumentoInicial entregaInicial,
                               EntregaDocumentoIntermedio entregaIntermedia,
                               EntregaDocumentoFinal entregaFinal,
                               EntregaReporte entregaReporte,
                               Stage stage) {
        this.coordinador = coordinador;
        this.experienciaEducativa = experienciaEducativa;
        this.stage = stage;

        if (entregaInicial != null) {
            this.entregaInicial = entregaInicial;
            this.tipoDocumento = "DOCUMENTO INICIAL: " + entregaInicial.getTipoDocumentoInicial();
            mostrarDetallesEntrega(entregaInicial);
            
        } else if (entregaIntermedia != null) {
            this.entregaIntermedia = entregaIntermedia;
            this.tipoDocumento = "DOCUMENTO INTERMEDIO: " + entregaIntermedia.getTipoDocumentoIntermedio();
            mostrarDetallesEntrega(entregaIntermedia);
            
        } else if (entregaFinal != null) {
            this.entregaFinal = entregaFinal;
            this.tipoDocumento = "DOCUMENTO FINAL: " + entregaFinal.getTipoDocumentoFinal();
            mostrarDetallesEntrega(entregaFinal);
            
        } else if (entregaReporte != null) {
            this.entregaReporte = entregaReporte;
            this.tipoDocumento = "REPORTE #" + entregaReporte.getNumeroReporte();
            mostrarDetallesEntrega(entregaReporte);
            
        } else {
            throw new IllegalArgumentException("At least one type of Entrega must be provided");
        }

        lblNombreEntrega.setText(tipoDocumento);
    }

    private void mostrarDetallesEntrega(EntregaDocumentoInicial entrega) {
        dpInicia.setValue(entrega.getFechaInicio());
        dpTermina.setValue(entrega.getFechaFin());
        lblValor.setText("Valor: " + entrega.getPuntaje());
    }

    private void mostrarDetallesEntrega(EntregaDocumentoIntermedio entrega) {
        dpInicia.setValue(entrega.getFechaInicio());
        dpTermina.setValue(entrega.getFechaFin());
        lblValor.setText("Valor: " + entrega.getPuntaje());
    }

    private void mostrarDetallesEntrega(EntregaDocumentoFinal entrega) {
        dpInicia.setValue(entrega.getFechaInicio());
        dpTermina.setValue(entrega.getFechaFin());
        lblValor.setText("Valor: " + entrega.getPuntaje());
    }

    private void mostrarDetallesEntrega(EntregaReporte entrega) {
        dpInicia.setValue(entrega.getFechaInicio());
        dpTermina.setValue(entrega.getFechaFin());
        lblValor.setText("Valor: " + entrega.getPuntaje());
    }

    private void btnGuardar(ActionEvent event) {
        try {
            if (dpInicia.getValue() == null || dpTermina.getValue() == null) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                    "Error", "Debe seleccionar ambas fechas");
                return;
            }
            
            LocalDate fechaInicio = dpInicia.getValue();
            LocalDate fechaFin = dpTermina.getValue();
            
            if (fechaFin.isBefore(fechaInicio)) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                    "Error", "La fecha de término no puede ser anterior a la fecha de inicio");
                return;
            }
            
            boolean resultado = false;
            
            if (entregaInicial != null) {
                resultado = EntregaDocumentoInicialDAO.actualizarFechasEntregaInicial(
                    entregaInicial.getId(), fechaInicio, fechaFin);
                
            } else if (entregaIntermedia != null) {
                resultado = EntregaDocumentoIntermedioDAO.actualizarFechasEntregaIntermedio(
                    entregaIntermedia.getId(), fechaInicio, fechaFin);
                
            } else if (entregaFinal != null) {
                resultado = EntregaDocumentoFinalDAO.actualizarFechasEntregaFinal(
                    entregaFinal.getId(), fechaInicio, fechaFin);
                
            } else if (entregaReporte != null) {
                resultado = EntregaReporteDAO.actualizarFechasEntregaReporte(
                    entregaReporte.getId(), fechaInicio, fechaFin);
                
            } 
            
            if (resultado) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
                    "Éxito", "Fecha guardada exitosamente");
                stage.close();
            } 
            
        } catch (SQLException e) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                "Error", "Error al actualizar las fechas: " + e.getMessage());
        }
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        stage.close();
    }

    @FXML
    private void btnAceptar(ActionEvent event) {
        btnGuardar(event);
    }
}