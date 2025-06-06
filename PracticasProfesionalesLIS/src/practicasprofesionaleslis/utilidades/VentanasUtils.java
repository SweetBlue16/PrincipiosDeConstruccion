package practicasprofesionaleslis.utilidades;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.stage.Stage;
import practicasprofesionaleslis.PracticasProfesionalesLIS;

public class VentanasUtils {
    public static void mostrarAlertaSimple(Alert.AlertType tipo, String titulo, String contenido) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
    
    public static boolean mostrarAlertaConfirmacion(String titulo, String contenido) {
        Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        alertaConfirmacion.setTitle(titulo);
        alertaConfirmacion.setHeaderText(null);
        alertaConfirmacion.setContentText(contenido);
        return alertaConfirmacion.showAndWait().get() == ButtonType.OK;
    }
    
    public static Stage obtenerEscenarioComponente(Control componente) {
        return (Stage) componente.getScene().getWindow();
    }
    
    public static void cerrarVentana(Control componente) {
        ((Stage) componente.getScene().getWindow()).close();
    }
    
    public static void irInicioSesion(Control componente) {
        try {
            Stage escenarioBase = (Stage) componente.getScene().getWindow();
            Parent vista = FXMLLoader.load(PracticasProfesionalesLIS.class.getResource("/practicasprofesionaleslis/vista/FXMLInicioSesion.fxml"));
            Scene escenaPrincipal = new Scene(vista);
            
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle(ConstantesUtils.TITULO_INICIO_SESION);
            escenarioBase.show();
            escenarioBase.setResizable(false);
            escenarioBase.centerOnScreen();
        } catch (IOException e) {
            mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_CARGAR_VENTANA
            );
        }
    }
}
