package practicasprofesionaleslis.controlador.coordinador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import practicasprofesionaleslis.modelo.dao.OrganizacionVinculadaDAO;
import practicasprofesionaleslis.modelo.dao.ResponsableProyectoDAO;
import practicasprofesionaleslis.modelo.pojo.OrganizacionVinculada;
import practicasprofesionaleslis.modelo.pojo.ResponsableProyecto;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLBuscarOrganizacionYResponsableController implements Initializable {

    @FXML
    private ComboBox<ResponsableProyecto> cbResponsableProyecto;
    @FXML
    private TextField tfNombreOV;
    @FXML
    private Label lbOVNoEncontrada;
    private OrganizacionVinculada organizacionSeleccionada;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validarOV();
    }    
    
    @FXML
    private void validarOV() {
        String nombreOV = tfNombreOV.getText().trim();
        if (nombreOV.isEmpty()) {
            lbOVNoEncontrada.setText("Ingresa el nombre de la organización.");
            cbResponsableProyecto.getItems().clear();
            organizacionSeleccionada = null;
            return;
        }

        try {
            OrganizacionVinculada ovEncontrada = OrganizacionVinculadaDAO.obtenerOrganizacionPorNombre(nombreOV);
            if (ovEncontrada == null) {
                lbOVNoEncontrada.setText("Organización no encontrada.");
                organizacionSeleccionada = null;
                cbResponsableProyecto.getItems().clear();
            } else {
                lbOVNoEncontrada.setText(""); 
                organizacionSeleccionada = ovEncontrada;
                
                List<ResponsableProyecto> responsables = ResponsableProyectoDAO.obtenerResponsablesPorIdOrganizacion(organizacionSeleccionada.getId());
                cbResponsableProyecto.getItems().clear();
                cbResponsableProyecto.getItems().addAll(responsables);
            }
        } catch (SQLException e) {
            lbOVNoEncontrada.setText("Error al buscar la organización.");
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                    ConstantesUtils.TITULO_ERROR, 
                    ConstantesUtils.ALERTA_ERROR_BD);
        }
    }
    
    private void irRegistrarDatos(OrganizacionVinculada organizacion, ResponsableProyecto responsable){
        try {
            Stage escenarioCalificarPresentacion = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/practicasprofesionaleslis/vista/coordinador/FXMLRegistrarProyecto.fxml"));
            Parent vista = loader.load();
            
            FXMLRegistrarProyectoController controller = loader.getController();
            controller.setOrganizacionYResponsable(organizacion, responsable);
            
            Stage escenarioEvaluador = new Stage();
            Scene escena = new Scene(vista);
            escenarioCalificarPresentacion.setScene(escena);
            escenarioCalificarPresentacion.setTitle("REGISTRAR PROYECTO");
            escenarioCalificarPresentacion.initModality(Modality.APPLICATION_MODAL);
            escenarioCalificarPresentacion.showAndWait();
            escenarioCalificarPresentacion.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_BD
            );
        }
    }

    
    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(tfNombreOV);
    }

    @FXML
    private void clicBtnAceptar(ActionEvent event) {
        ResponsableProyecto responsableSeleccionado = cbResponsableProyecto.getValue();
        
        if (organizacionSeleccionada == null || responsableSeleccionado == null) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ADVERTENCIA,
                    "Debes seleccionar una organización válida y un responsable.");
            return;
        }

        irRegistrarDatos(organizacionSeleccionada, responsableSeleccionado);
    }
    
}
