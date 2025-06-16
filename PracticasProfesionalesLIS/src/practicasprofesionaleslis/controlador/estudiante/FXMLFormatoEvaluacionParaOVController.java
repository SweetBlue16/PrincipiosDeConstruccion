package practicasprofesionaleslis.controlador.estudiante;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import practicasprofesionaleslis.modelo.dao.ExpedienteDAO;
import practicasprofesionaleslis.modelo.dao.OrganizacionVinculadaDAO;
import practicasprofesionaleslis.modelo.dao.ProyectoDAO;
import practicasprofesionaleslis.modelo.dao.ResponsableProyectoDAO;
import practicasprofesionaleslis.modelo.pojo.Estudiante;
import practicasprofesionaleslis.modelo.pojo.Expediente;
import practicasprofesionaleslis.modelo.pojo.OrganizacionVinculada;
import practicasprofesionaleslis.modelo.pojo.Proyecto;
import practicasprofesionaleslis.modelo.pojo.ResponsableProyecto;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.PDFUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLFormatoEvaluacionParaOVController implements Initializable {
   
    @FXML
    private Label lblEncabezado;
    
    private final String RUTA_RELATIVA = "/practicasprofesionaleslis/recursos/pdf/PRAIS-04-Evaluacion-de-la-organizacion.pdf";
    private final String NOMBRE_ARCHIVO = "FormatoEvaluacionOV";
    
    private Estudiante estudiante;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void inicializarEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
    
    @FXML
    private void clicBtnDescargar(ActionEvent event) {
        if (estudiante == null) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    "No se ha podido identificar al estudiante");
            return;
        }
        
        try {
            Expediente expediente = ExpedienteDAO.obtenerExpedientePorEstudiante(estudiante.getId());
            
            if (expediente == null || expediente.getProyecto() == null) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                        ConstantesUtils.TITULO_ERROR,
                        "El estudiante no tiene un proyecto asignado");
                return;
            }
            
            Proyecto proyecto = ProyectoDAO.obtenerProyectoPorId(expediente.getProyecto().getId());
            
            if (proyecto == null || proyecto.getOrganizacionVinculada() == null || 
                proyecto.getResponsableProyecto() == null) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                        ConstantesUtils.TITULO_ERROR,
                        "No se encontró información completa del proyecto");
                return;
            }
            
            // Para generar el PDF y los datos
            boolean exito = PDFUtils.guardarPDFConDatos(
                    RUTA_RELATIVA,  
                    NOMBRE_ARCHIVO, 
                    estudiante, 
                    proyecto, 
                    expediente);
            
            if (exito) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                        "DESCARGA CORRECTA",
                        "Se ha descargado el formato Evaluación de la Organización Vinculada correctamente"
                );
                VentanasUtils.cerrarVentana(lblEncabezado);
            } else {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                        ConstantesUtils.TITULO_ERROR,
                        ConstantesUtils.ALERTA_DESCARGA_ARCHIVO_FALLIDA
                );
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
        VentanasUtils.cerrarVentana(lblEncabezado);
    }
    
}
