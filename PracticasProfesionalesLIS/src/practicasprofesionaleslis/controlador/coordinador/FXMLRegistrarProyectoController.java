package practicasprofesionaleslis.controlador.coordinador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import practicasprofesionaleslis.modelo.dao.ProyectoDAO;
import practicasprofesionaleslis.modelo.pojo.OrganizacionVinculada;
import practicasprofesionaleslis.modelo.pojo.Proyecto;
import practicasprofesionaleslis.modelo.pojo.ResponsableProyecto;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

/**
 * Autor: Abraham Cano
 * Fecha de creación: 09/06/2025
 * Descripción: Controla la ventana para registrar
 * un nuevo proyecto.
 */
public class FXMLRegistrarProyectoController implements Initializable {

    @FXML
    private TextArea tfDescripcion;
    @FXML
    private TextField tfNombreProyecto;
    @FXML
    private TextField tfNumeroIntegrantes;
    private OrganizacionVinculada organizacion;
    private ResponsableProyecto responsable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setOrganizacionYResponsable(OrganizacionVinculada organizacion, ResponsableProyecto responsable) {
        this.organizacion = organizacion;
        this.responsable = responsable;
    }
    
    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        String nombreProyecto = tfNombreProyecto.getText().trim();
        String descripcion = tfDescripcion.getText().trim();
        String numIntegrantesTexto = tfNumeroIntegrantes.getText().trim();

        if (nombreProyecto.isEmpty() || descripcion.isEmpty() || numIntegrantesTexto.isEmpty()) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ADVERTENCIA,
                    "Por favor, completa todos los campos antes de guardar.");
            return;
        }

        int numeroIntegrantes;
        try {
            numeroIntegrantes = Integer.parseInt(numIntegrantesTexto);
            if (numeroIntegrantes <= 0  || numeroIntegrantes > 3) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                        ConstantesUtils.TITULO_ADVERTENCIA,
                        "Dato inválido en número de integrantes, ingrese números del 1 al 3.");
                return;
            }
        } catch (NumberFormatException e) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ADVERTENCIA,
                    "El número de integrantes debe ser un valor numérico entero.");
            return;
        }

        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(nombreProyecto);
        proyecto.setDescripcion(descripcion);
        proyecto.setNumIntegrantes(numeroIntegrantes);
        proyecto.setOrganizacionVinculada(organizacion);
        proyecto.setResponsableProyecto(responsable);

        try {
            boolean registrado = ProyectoDAO.registrarProyecto(proyecto);
            if (registrado) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                        ConstantesUtils.TITULO_EXITO,
                        "Se ha registrado correctamente el proyecto en el sistema.");
                VentanasUtils.cerrarVentana(tfNombreProyecto);
            } 
        } catch (Exception e) {
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_BD);
        }
    }
    
    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(tfDescripcion);
    }

    
}
