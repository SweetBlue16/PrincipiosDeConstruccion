package practicasprofesionaleslis.controlador.coordinador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import practicasprofesionaleslis.modelo.dao.ProyectoDAO;
import practicasprofesionaleslis.modelo.pojo.Proyecto;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLBuscadorProyectoController implements Initializable {

    @FXML
    private TextField txtfNombreProyecto;
    @FXML
    private Label lbProyectoNoEncontrado;
    
    private Proyecto proyectoEncontrado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void validarProyecto() {
        String nombreProy = txtfNombreProyecto.getText().trim();
        if (nombreProy.isEmpty()) {
            lbProyectoNoEncontrado.setText("Ingresa el nombre del proyecto.");
            return;
        }

        try {
            proyectoEncontrado = ProyectoDAO.obtenerProyectoPorNombre(nombreProy);
            if (proyectoEncontrado == null) {
                lbProyectoNoEncontrado.setText("Proyecto no encontrado.");
            } else {
                lbProyectoNoEncontrado.setText(""); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                    ConstantesUtils.TITULO_ERROR, 
                    ConstantesUtils.ALERTA_ERROR_BD);
        }
    }
    
    private void irRegistrarDatos(Proyecto proyecto){
        try {
            Stage escenarioCalificarPresentacion = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/practicasprofesionaleslis/vista/coordinador/FXMLActualizarProyecto.fxml"));
            Parent vista = loader.load();
            
            FXMLActualizarProyectoController controller = loader.getController();
            
            boolean integrantesAsignados = false;
            try {
                integrantesAsignados = ProyectoDAO.tieneIntegrantesAsignados(proyecto.getId());
            } catch (SQLException e) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                        ConstantesUtils.TITULO_ERROR,
                        ConstantesUtils.ALERTA_ERROR_BD);
                e.printStackTrace();
                return;
            }
            
            controller.inicializarDatosProyecto(proyecto, integrantesAsignados);
            
            Stage escenarioEvaluador = new Stage();
            Scene escena = new Scene(vista);
            escenarioCalificarPresentacion.setScene(escena);
            escenarioCalificarPresentacion.setTitle("ACTUALIZAR PROYECTO");
            escenarioCalificarPresentacion.initModality(Modality.APPLICATION_MODAL);
            escenarioCalificarPresentacion.showAndWait();
            escenarioCalificarPresentacion.centerOnScreen();
            txtfNombreProyecto.clear();
            lbProyectoNoEncontrado.setText("");
        } catch (IOException e) {
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_BD
            );
        }
    }
    
    @FXML
    private void clicBtnBuscar(ActionEvent event) {
        String nombreProy = txtfNombreProyecto.getText().trim();
        
        if (nombreProy.isEmpty()) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ADVERTENCIA,
                    "Debe ingresar un nombre de proyecto.");
            return;
        }
        
        validarProyecto();

        if (proyectoEncontrado != null) {
            irRegistrarDatos(proyectoEncontrado);
        }
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(txtfNombreProyecto);
    }
    
}
