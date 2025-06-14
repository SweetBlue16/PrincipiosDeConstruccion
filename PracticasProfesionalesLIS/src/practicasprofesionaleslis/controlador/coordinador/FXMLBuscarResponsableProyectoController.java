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
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import practicasprofesionaleslis.PracticasProfesionalesLIS;
import practicasprofesionaleslis.modelo.dao.ProyectoDAO;
import practicasprofesionaleslis.modelo.pojo.Proyecto;
import practicasprofesionaleslis.modelo.pojo.ResponsableProyecto;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLBuscarResponsableProyectoController implements Initializable {

    @FXML
    private TextField txtfNombreProyecto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}    

    @FXML
    private void clicBtnBuscar(ActionEvent event) {
        try {
            validarCampoVacio();
            String nombreProyecto = txtfNombreProyecto.getText().trim();
            Proyecto proyecto = ProyectoDAO.obtenerResponsablePorProyecto(nombreProyecto);
            
            if (proyecto != null) {
                ResponsableProyecto responsableProyecto = proyecto.getResponsableProyecto();
                irResponsableYProyecto(responsableProyecto);
            } else {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ADVERTENCIA,
                    ConstantesUtils.ALERTA_BUSQUEDA_FALLIDA
                );
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                ConstantesUtils.TITULO_ADVERTENCIA,
                ConstantesUtils.ALERTA_DATOS_INVALIDOS
            );
        } catch (SQLException e) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                ConstantesUtils.TITULO_ERROR,
                e.getMessage()
            );
        }
    }
    
    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(txtfNombreProyecto);
    }
    
    private void validarCampoVacio() {
        String nombreProyecto = txtfNombreProyecto.getText().trim();
        if (nombreProyecto == null || nombreProyecto.isEmpty() || nombreProyecto.length() > 100) {
            throw new IllegalArgumentException();
        }
    }
    
    private void irResponsableYProyecto(ResponsableProyecto responsableProyecto) {
        try {
            String rutaRecurso = "/practicasprofesionaleslis/vista/coordinador/FXMLModificarDatosDelResponsable.fxml";
            FXMLLoader cargador = new FXMLLoader(PracticasProfesionalesLIS.class.getResource(rutaRecurso));
            Parent vista = cargador.load();
            FXMLModificarDatosDelResponsableController controlador = cargador.getController();
            controlador.inicializarResponsableProyecto(responsableProyecto);
            
            Stage escenarioBase = VentanasUtils.obtenerEscenarioComponente(txtfNombreProyecto);
            Scene escenaModificarResponsable = new Scene(vista);
            escenarioBase.setScene(escenaModificarResponsable);
            escenarioBase.setTitle(ConstantesUtils.TITULO_ACTUALIZAR);
            escenarioBase.initModality(Modality.APPLICATION_MODAL);
            escenarioBase.show();
            escenarioBase.setResizable(false);
            escenarioBase.centerOnScreen();
        } catch (IOException e) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_CARGAR_VENTANA
            );
        }
    }
}
