package practicasprofesionaleslis.controlador.evaluador;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import practicasprofesionaleslis.PracticasProfesionalesLIS;
import practicasprofesionaleslis.interfaz.IObservador;
import practicasprofesionaleslis.modelo.pojo.Evaluador;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLPrincipalEvaluadorController implements Initializable, IObservador {
    
    private Evaluador evaluador;
    @FXML
    private Label lblCorreoInstitucional;
    @FXML
    private Label lblNombreEvaluador;
    @FXML
    private ImageView imgFotoPerfil;

    @Override
    public void initialize(URL url, ResourceBundle rb) {} 
    
    @Override
    public void operacionExitosa() {
        inicializarDatosEvaluador(evaluador);
    }
    
    public void inicializarDatosEvaluador(Evaluador evaluador) {
        this.evaluador = evaluador;
        if (evaluador != null) {
            lblNombreEvaluador.setText(evaluador.toString());
            lblCorreoInstitucional.setText(evaluador.getCorreoInstitucional());
            mostrarFotoPerfilEvaluador(evaluador);
        }
    }
    
    private void mostrarFotoPerfilEvaluador(Evaluador evaluador) {
        Image imagen = null;
        try {
            byte[] foto = evaluador.getFotoPerfil();
            if (foto != null) {
                ByteArrayInputStream input = new ByteArrayInputStream(foto);
                imagen = new Image(input);
            } else {
                String ruta = "/practicasprofesionaleslis/recursos/fotoPerfilDefault.png";
                imagen = new Image(PracticasProfesionalesLIS.class.getResourceAsStream(ruta));
            }
            imgFotoPerfil.setImage(imagen);
        } catch (NullPointerException e) {
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_CARGAR_IMAGEN
            );
        }
    }

    @FXML
    private void clicBtnCerrarSesion(ActionEvent event) {
        String titulo = ConstantesUtils.TITULO_CIERRE_SESION;
        String contenido = ConstantesUtils.ALERTA_CERRAR_SESION;
        if (VentanasUtils.mostrarAlertaConfirmacion(titulo, contenido)) {
            VentanasUtils.irInicioSesion(lblNombreEvaluador);
        }
    }

    @FXML
    private void clicBtnEvaluarPresentacionEstudiante(ActionEvent event) {
        try {
            Stage escenarioEvaluador = new Stage();
            Parent vista = FXMLLoader.load(getClass().getResource("/practicasprofesionaleslis/vista/evaluador/FXMLSeleccionEstudiante.fxml"));
            Scene escena = new Scene(vista);
            
            escenarioEvaluador.setScene(escena);
            escenarioEvaluador.setTitle("SELECCIÓN DE ESTUDIANTE");
            escenarioEvaluador.initModality(Modality.APPLICATION_MODAL);
            escenarioEvaluador.showAndWait();
            escenarioEvaluador.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_CARGAR_VENTANA
            );;
        }
    }

    @FXML
    private void clicBtnMiPerfil(ActionEvent event) {
        VentanasUtils.irMiPerfil(lblNombreEvaluador, evaluador, this);
    }
    
}
