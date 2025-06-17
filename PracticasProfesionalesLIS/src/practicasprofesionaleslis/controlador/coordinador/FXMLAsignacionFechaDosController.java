/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package practicasprofesionaleslis.controlador.coordinador;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import practicasprofesionaleslis.modelo.dao.EntregaReporteDAO;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

/**
 * FXML Controller class
 *
 * @author acrca
 */
public class FXMLAsignacionFechaDosController implements Initializable {

    @FXML
    private DatePicker dpFechaFin;
    private int numeroReporte;
    private LocalDate fechaInicioReporte;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void inicializarDatos(int numeroReporte) {
        this.numeroReporte = numeroReporte;
        try {
            this.fechaInicioReporte = EntregaReporteDAO.obtenerFechaInicioPorNumeroReporte(numeroReporte);
            if (fechaInicioReporte != null) {
                dpFechaFin.setValue(fechaInicioReporte.plusDays(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(
                Alert.AlertType.ERROR,
                ConstantesUtils.TITULO_ERROR,
                ConstantesUtils.ALERTA_ERROR_BD
            );
        }
    }

    @FXML
    private void clicBtnAceptar(ActionEvent event) {
         if (dpFechaFin.getValue() == null) {
            VentanasUtils.mostrarAlertaSimple(
                Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ADVERTENCIA,
                    "Debe seleccionar una fecha de fin"
            );
            return;
        }
        
        if(!fechaInicioReporte.isBefore(dpFechaFin.getValue())){ 
            try {
                boolean exito = EntregaReporteDAO.actualizarFechaFinPorNumeroReporte(
                    numeroReporte, 
                    dpFechaFin.getValue()
                );

                if (exito) {
                    VentanasUtils.mostrarAlertaSimple(
                        Alert.AlertType.INFORMATION,
                            ConstantesUtils.TITULO_EXITO,
                            "Fecha guardada correctamente"
                    );
                    VentanasUtils.cerrarVentana(dpFechaFin);
                } else {
                    VentanasUtils.mostrarAlertaSimple(
                        Alert.AlertType.WARNING,
                            ConstantesUtils.TITULO_ADVERTENCIA,
                            "No se encontraron reportes con el número especificado"
                    );
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                VentanasUtils.mostrarAlertaSimple(
                    Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_BD
                );
            }
        }else{
            VentanasUtils.mostrarAlertaSimple(
            Alert.AlertType.WARNING,
                ConstantesUtils.TITULO_ADVERTENCIA,
                "La fecha de fin no puede ser antes que la fecha de inicio"
            );        
        }   
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(dpFechaFin);
    }
    
}
