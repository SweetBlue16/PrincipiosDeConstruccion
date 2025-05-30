package practicasprofesionaleslis.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import practicasprofesionaleslis.modelo.pojo.Evaluador;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLPrincipalEvaluadorController implements Initializable {
    private Evaluador evaluador;

    @FXML
    private Label lblCorreoInstitucional;
    @FXML
    private Label lblNombreEvaluador;
    @FXML
    private ImageView imgFotoPerfil;

    @Override
    public void initialize(URL url, ResourceBundle rb) {} 
    
    public void inicializarDatosEvaluador(Evaluador evaluador) {
        this.evaluador = evaluador;
        if (evaluador != null) {
            lblNombreEvaluador.setText(evaluador.toString());
            lblCorreoInstitucional.setText(evaluador.getCorreoInstitucional());
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
    }
}
