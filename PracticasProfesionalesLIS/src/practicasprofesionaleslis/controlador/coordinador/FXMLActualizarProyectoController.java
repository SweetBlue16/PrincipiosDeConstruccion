package practicasprofesionaleslis.controlador.coordinador;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.modelo.dao.ProyectoDAO;
import practicasprofesionaleslis.modelo.pojo.Proyecto;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLActualizarProyectoController implements Initializable {

    @FXML
    private TextField txtfNombreProyecto;
    @FXML
    private TextField txtfNumeroIntegrantes;
    @FXML
    private TextArea txtfDescripcionProy;
    private Proyecto proyectoSeleccionado;
    private boolean integrantesAsignados;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void inicializarDatosProyecto(Proyecto proyecto, boolean integrantesAsignados) {
        this.proyectoSeleccionado = proyecto;
        this.integrantesAsignados = integrantesAsignados;
        cargarDatosProyecto();
        
        // Para no permitir ingresar otro número de integrantes 
        if (integrantesAsignados) {
            txtfNumeroIntegrantes.setDisable(true);
        }
    }
    
    private void cargarDatosProyecto() {
        if (proyectoSeleccionado != null) {
            txtfNombreProyecto.setText(proyectoSeleccionado.getNombre());
            txtfNumeroIntegrantes.setText(String.valueOf(proyectoSeleccionado.getNumIntegrantes()));
            txtfDescripcionProy.setText(proyectoSeleccionado.getDescripcion());
        }
    }
    
    private boolean validarCampos() {
        if (txtfNombreProyecto.getText().trim().isEmpty()) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ADVERTENCIA,
                    "El nombre del proyecto es obligatorio");
            return false;
        }
        
        // Para validar número de integrantes solo si no hay asignados
        if (!integrantesAsignados) {
            try {
                int numIntegrantes = Integer.parseInt(txtfNumeroIntegrantes.getText().trim());
                if (numIntegrantes < 1 || numIntegrantes > 3) {
                    VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                            ConstantesUtils.TITULO_ADVERTENCIA,
                            "El número de integrantes debe estar entre 1 y 3");
                    return false;
                }
            } catch (NumberFormatException e) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                            ConstantesUtils.TITULO_ADVERTENCIA,
                            ConstantesUtils.ALERTA_DATOS_INVALIDOS);
                return false;
            }
        }
        
        return true;
    }

    @FXML
    private void clicBtnAceptar(ActionEvent event) {
        if (validarCampos()) {
            try {
                proyectoSeleccionado.setNombre(txtfNombreProyecto.getText().trim());
                
                // Solo actualizar número de integrantes si no hay asignados
                if (!integrantesAsignados) {
                    proyectoSeleccionado.setNumIntegrantes(Integer.parseInt(txtfNumeroIntegrantes.getText().trim()));
                }
                
                proyectoSeleccionado.setDescripcion(txtfDescripcionProy.getText().trim());
                
                boolean exito = ProyectoDAO.editarProyecto(proyectoSeleccionado);
                
                if (exito) {
                    VentanasUtils.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                            ConstantesUtils.TITULO_EXITO,
                            "Proyecto actualizado exitosamente");
                    VentanasUtils.cerrarVentana(txtfNombreProyecto);
                } else {
                    VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                            ConstantesUtils.TITULO_ERROR,
                            "No se pudo actualizar el proyecto");
                }
            } catch (SQLException e) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                        ConstantesUtils.TITULO_ERROR,
                        ConstantesUtils.ALERTA_ERROR_BD);
            }
        }
    }
    
    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(txtfNombreProyecto);
    }
    
}
